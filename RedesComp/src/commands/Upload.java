/**
 * 
 */
package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import socketwrappers.ClientTCP;
import socketwrappers.MessageTCP;
import utils.Errors;
import utils.FileHandler;
import utils.LocalPaths;
import utils.Protocol;
import utils.StreamProcessors;

/**
 * @author Joao Antunes
 *
 */
public class Upload extends Command{
	
	private ClientTCP _user;
	private String _CSName;
	private int _CSPort;
	private MessageTCP _bufferTCP;
	private String _fileName;
	
	public Upload(ClientTCP user, String CSName, int CSPort, String[] arguments){
		_code = Protocol.UPLOAD_COMMAND;
		_arguments = arguments;
		_user = user;
		_CSName = CSName;
		_CSPort = CSPort;
	}
	
	/* (non-Javadoc)
	 * @see commands.Command#run()
	 */
	@Override
	public void run() {
		try{
			if(_arguments.length > 2){
				throw new IllegalArgumentException();
			}
			_fileName = _arguments[0];
			File file = new File(_fileName);
			
			System.out.println(_fileName);
			
			if(!file.exists()){
				throw new FileNotFoundException();
			}
			_user.sendToServer(Protocol.CHECK_FILE + " " + file.getName() + '\n');
			
			System.out.println(file.getName());
			
			System.out.println(">> Sent upload");
			_bufferTCP = _user.receiveFromServer(Protocol.CHECK_FILE_RESPONSE_ARGS);
			_arguments = _bufferTCP.getStrParams();
			if(_arguments[0].equals(Protocol.CHECK_FILE_RESPONSE)){
				if(_arguments[1].equals(Protocol.IN_USE)){
					System.out.println("File name already in use, please try another one.");
				}
				else if(_arguments[1].equals(Protocol.AVAILABLE)){
					
					System.out.println("File name available");
					
					byte[] output = FileHandler.upFile(_fileName);
					
					byte[] resultOut = StreamProcessors.concatByte(new String(Protocol.UP_USER_FILE + " " + (output.length - 1) + " ").getBytes(), output);
					_user.sendToServer(resultOut);
					
					System.out.println("File sent");
					
					_bufferTCP = _user.receiveFromServer(Protocol.UP_USER_RESPONSE_ARGS);
					_arguments = _bufferTCP.getStrParams();
					System.out.println(_user.isConnected());
					System.out.println(_arguments[0]);
					if(_arguments[0].equals(Protocol.UP_USER_RESPONSE)){
						if(_arguments.length == 3){
							if(_arguments[1].startsWith(Protocol.NOT_OK)){
								System.out.println("Problems uploading file. Please try again.");
							}
							else if(_arguments[1].startsWith(Protocol.OK)){
								System.out.println("File uploaded!");
							}
						}
						else{
							System.err.println(Errors.INVALID_PROTOCOL);
							System.exit(-1);
						}
					}
				}
			}
			else if(_arguments[0].startsWith(Protocol.ERROR)){
				System.out.println(Errors.INVALID_PROTOCOL);
				System.exit(-1);
			}
			else{
				System.out.println(Errors.INVALID_COMMAND);
			}
		} catch (IllegalArgumentException e){
			System.err.println(Errors.INVALID_COMMAND);
			System.exit(-1);
		} catch (IOException e){
			System.err.println(Errors.IO_INPUT);
			System.exit(-1);
		} finally{
			try {
				_user.close();
			} catch (IOException e) {
				System.err.println(Errors.IO_INPUT);
				System.exit(-1);
			}
		}
	}
}
