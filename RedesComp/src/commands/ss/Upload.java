/**
 * 
 */
package commands.ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

import commands.Command;
import socketwrappers.MessageTCP;
import socketwrappers.ServerTCP;
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
	
	private ServerTCP _server;
	
	private MessageTCP _message;
	
	public Upload (ServerTCP server, MessageTCP message) throws NullPointerException, IOException{
		_server = server;
		_message = message;
	}
	
	@Override
	public void run() {
		
		try {
			try{
				String status;
				String commandResponse = Protocol.UP_CS_RESPONSE;
				String[] tokens = _message.getStrParams();
				byte[] data = _message.getData();
				String filename = tokens[1];
				int filesize = Integer.parseInt(tokens[2]);
				File file = new File(LocalPaths.RESOURCES + filename);
				if(file.exists()){
					status = "nok";
					System.out.println(Errors.FILE_EXISTS);
				}
				else{
					FileHandler.createFile(LocalPaths.STORED + filename, filesize, data);
					if(file.exists()){
						if(file.length() == filesize){
							status = "ok";
						}
						else{
							status= "nok";
							System.err.println(Errors.SAVING_FILE);
						}
					}
					else{
						status= "nok";
						System.err.println(Errors.INEXISTING_FILE);
					}
				}
				String toEcho = commandResponse + " " + status;
				System.out.println(toEcho);
				_server.send(toEcho);
				System.exit(0);
			} catch(NumberFormatException e){
				_server.send(Protocol.ERROR);
				System.err.println(Errors.INVALID_PROTOCOL);
				
			} catch(IllegalArgumentException e){
				_server.send(Protocol.ERROR);
				System.err.println(Errors.INVALID_PROTOCOL);
				
			} catch(SocketException e){
				_server.send(Protocol.ERROR);
				System.err.println(Errors.SOCKET_PROBLEM);
				
			} catch(IOException e){
				_server.send(Protocol.ERROR);
				System.err.println(Errors.IO_PROBLEM);
			}
		} catch(IOException e){
			System.err.println(Errors.SOCKET_PROBLEM);
			System.exit(-1);
		}
		System.exit(0);
	}
}
