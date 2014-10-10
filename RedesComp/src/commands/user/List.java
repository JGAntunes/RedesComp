/**
 * 
 */
package commands.user;

import java.io.IOException;

import commands.Command;

import socketwrappers.ClientUDP;
import socketwrappers.MessageUDP;
import user.User;
import utils.Errors;
import utils.Protocol;

/**
 * @author Grupo 3
 *
 */
public class List extends Command{

	private ClientUDP _user;
	private String _CSName;
	private int _CSPort;
	private MessageUDP _bufferUDP;
	
	/**
	 * List command constructor that receives as arguments an UDP client user, the central server name , the central server port
	 * and an array of arguments.
	 * 
	 */
	public List(String CSName, int CSPort, String[] arguments){
		_code = Protocol.LIST_COMMAND;
		_arguments = arguments;
		_CSName = CSName;
		_CSPort = CSPort;
	}
	
	/**
	 * The use this command the client who is using an UDP protocol sends out a message to the central server with the command 
	 * string and awaits a response with the port and the ip adress of the SS that we have to acess to retrieve any file, we also 
	 * receive the number of files and the list. If the lenght of the arguments is bigger than 4 we assume right away that the
	 * protocol wasn't followed.
	 */
	@Override
	public void run() {
		try{
			_user = new ClientUDP();
			_user.sendToServer(new MessageUDP(_CSName, _CSPort, Protocol.LIST + "\n"));
			System.out.println(">> Sent list");
			_bufferUDP = _user.receiveFromServer();
			_arguments = _bufferUDP.getMessage().split(" ");
			if(_arguments[0].equals(Protocol.LIST_RESPONSE)){
				if(_arguments.length > 4){
					User.setSS(_arguments[1], Integer.parseInt(_arguments[2]));
					int numFiles = Integer.parseInt(_arguments[3]);
					System.out.println("Available files for download:");
					for(int i = 1; i <= numFiles; i++){
						System.out.println(i + " " + _arguments[3+i]);
					}
				}
			}
			else if(_arguments[0].startsWith(Protocol.ERROR)){
				System.out.println(Errors.INVALID_PROTOCOL);
				System.exit(-1);
			}
			else if(_arguments[0].startsWith(Protocol.EOF)){
				System.out.println(Errors.SERVER_BUSY);
			}
			else{
				System.out.println(Errors.INVALID_COMMAND);
			}
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
