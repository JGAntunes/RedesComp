/**
 * 
 */
package commands;

import java.io.IOException;

import socketwrappers.ClientTCP;
import socketwrappers.MessageTCP;
import utils.Errors;
import utils.FileHandler;
import utils.Protocol;

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
			if(_arguments.length > 1){
				throw new IllegalArgumentException();
			}
			_fileName = _arguments[0];
			_user.sendToServer(Protocol.CHECK_FILE + " " + _fileName + "\n");
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
					
					_user.sendToServer(Protocol.CHECK_FILE + " " + _fileName + "\n");
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
