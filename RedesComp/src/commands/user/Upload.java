/**
 * 
 */
package commands.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import commands.Command;

import socketwrappers.ClientTCP;
import socketwrappers.MessageTCP;
import utils.Errors;
import utils.FileHandler;
import utils.LocalPaths;
import utils.Protocol;
import utils.StreamProcessors;

/**
 * @author Grupo 3
 *
 */
public class Upload extends Command{
	
	private ClientTCP _user;
	private String _CSName;
	private int _CSPort;
	private MessageTCP _bufferTCP;
	private String _fileName;
	
	public Upload(String CSName, int CSPort, String[] arguments){
		_code = Protocol.UPLOAD_COMMAND;
		_arguments = arguments;
		_CSName = CSName;
		_CSPort = CSPort;
	}
	
	/* (non-Javadoc)
	 * @see commands.Command#run()
	 */
	@Override
	public void run() {
		try{
			_user = new ClientTCP(_CSName, _CSPort);
			if(_arguments.length > 2){
				throw new IllegalArgumentException();
			}
			_fileName = _arguments[0];
			File file = new File(_fileName);
			
			System.out.println(_fileName);
			
			if(!file.exists()){
				throw new FileNotFoundException();
			}
			_user.sendToServer(new String(Protocol.CHECK_FILE + " " + file.getName() + '\n').getBytes());
			
			System.out.println(file.getName());
			
			System.out.println(">> Sent upload");
			
			//_bufferTCP = _user.receiveFromServer(Protocol.CHECK_FILE_RESPONSE_ARGS, false);
			_arguments = _user.receiveFromServer().split(" ");
			if(_arguments[0].equals(Protocol.CHECK_FILE_RESPONSE)){
				if(_arguments[1].equals(Protocol.IN_USE)){
					System.out.println("File name already in use, please try another one.");
				}
				else if(_arguments[1].equals(Protocol.AVAILABLE)){
					
					System.out.println("File name available");
					byte[] output = FileHandler.upFile(_fileName);
					byte[] command = new String(Protocol.UP_USER_FILE + " " + (output.length-1) + " ").getBytes();
					byte[] resultOut = StreamProcessors.concatByte(command, command.length, output, output.length);
					_user.sendToServer(resultOut);
					
					System.out.println("File sent");
					
					_bufferTCP = _user.receiveFromServer(Protocol.UP_USER_RESPONSE_ARGS, false);
					_arguments = _bufferTCP.getStrParams();
					if(_arguments[0].equals(Protocol.UP_USER_RESPONSE)){
						if(_arguments.length == 2){
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
			e.printStackTrace();
			System.err.println(Errors.INVALID_COMMAND);
			System.exit(-1);
		} catch (IOException e){
			e.printStackTrace();
			System.err.println(Errors.IO_PROBLEM);
			System.exit(-1);
		} finally{
			try {
				_user.close();
			} catch (IOException e) {
				System.err.println(Errors.IO_PROBLEM);
				System.exit(-1);
			}
		}
	}
}
