/**
 * 
 */
package commands;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.nio.file.FileAlreadyExistsException;

import socketwrappers.ClientTCP;
import socketwrappers.MessageTCP;
import utils.Errors;
import utils.FileHandler;
import utils.LocalPaths;
import utils.Protocol;

/**
 * @author Joao Antunes
 *
 */
public class Retrieve extends Command{
	private ClientTCP _user;
	private String _SSName;
	private int _SSPort;
	private MessageTCP _bufferTCP;
	private String _fileName;
	
	public Retrieve(ClientTCP user, String SSName, int SSPort, String[] arguments){
		_code = Protocol.RETRIEVE_COMMAND;
		_arguments = arguments;
		_user = user;
		_SSName = SSName;
		_SSPort = SSPort;
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
			_user.sendToServer(Protocol.DOWN_FILE + " " + _fileName + "\n");
			System.out.println(">> Sent retrieve");
			_bufferTCP = _user.receiveFromServer(Protocol.DOWN_RESPONSE_ARGS);
			_arguments = _bufferTCP.getStrParams();
			if(_arguments[0].equals(Protocol.DOWN_RESPONSE)){
				System.out.println(_arguments.length);
				if(_arguments.length == 3){
					if(_arguments[1].equals(Protocol.NOT_OK)){
						System.out.println("Requested file isn't available. Please try again.");
					}
					else if(_arguments[1].equals(Protocol.OK)){
						System.out.println("File received!");
						try{
							FileHandler.createFile(LocalPaths.DOWNLOADS + _fileName, Integer.parseInt(_arguments[2]), _bufferTCP.getData());
						} catch(NumberFormatException e){
							e.printStackTrace();
							System.err.println(Errors.INVALID_PROTOCOL);
							System.exit(-1);
						} catch(IllegalArgumentException e){
							e.printStackTrace();
							System.err.println(Errors.INVALID_PROTOCOL);
							System.exit(-1);
						} catch(FileAlreadyExistsException e){
							System.out.println("File already exists in your downloads directory.");
						} catch(IOException e){
							e.printStackTrace();
							System.err.println(Errors.SAVING_FILE);
							System.exit(-1);
						}
					}
				}
				else{
					System.err.println(Errors.INVALID_PROTOCOL);
					System.exit(-1);
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
			e.printStackTrace();
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
