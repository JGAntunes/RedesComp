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
 * @author Grupo 3
 *
 */
public class Retrieve extends Command{
	private ClientTCP _user;
	private String _SSName;
	private int _SSPort;
	private MessageTCP _bufferTCP;
	private String _fileName;
	
	/**
	 * The retrieve command constructor, receives as arguments a TCP client user, the port and name of the storage server we 
	 * to acess to retrieve the file, and an array of arguments.
	 */
	public Retrieve(String SSName, int SSPort, String[] arguments){
		_code = Protocol.RETRIEVE_COMMAND;
		_arguments = arguments;
		_SSName = SSName;
		_SSPort = SSPort;
	}
	
	/* (non-Javadoc)
	 * @see commands.Command#run()
	 */
	/**
	 * To use this command first we check if the number of arguments exceeds 1, if its does we assume that the arguments given
	 * int the command are wrong.
	 * If everything is ok we proceed to get the name of the file we need to retrieve from the SS, after that we request the file 
	 * and await for the SS to respond. As we receive a response we check if the number of arguments is 3, otherwise the protocol
	 * wasn't followed. First we check the first argument to verify if the file we want is on the SS, if not we end the communication
	 * and print out an error message, otherwise we create a file to were we sent all the data that the SS sent us.
	 * 
	 */
	@Override
	public void run() {
		try{
			_user = new ClientTCP(_SSName, _SSPort);
			if(_arguments.length > 1){
				throw new IllegalArgumentException();
			}
			_fileName = _arguments[0];
			_user.sendToServer(new String(Protocol.DOWN_FILE + " " + _fileName + '\n').getBytes());
			System.out.println(">> Sent retrieve");
			_bufferTCP = _user.receiveFromServer(Protocol.DOWN_RESPONSE_ARGS, true);
			_arguments = _bufferTCP.getStrParams();
			if(_arguments[0].equals(Protocol.DOWN_RESPONSE)){
				if(_arguments.length == 3){
					if(_arguments[1].startsWith(Protocol.NOT_OK)){
						System.out.println("Requested file isn't available. Please try again.");
					}
					else if(_arguments[1].startsWith(Protocol.OK)){
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
				System.out.println(_arguments[0]);
				System.out.println(Errors.INVALID_COMMAND);
			}
		} catch (IllegalArgumentException e){
			e.printStackTrace();
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
