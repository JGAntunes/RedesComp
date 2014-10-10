/**
 * 
 */
package commands.ss;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

import commands.Command;
import socketwrappers.ClientTCP;
import socketwrappers.MessageTCP;
import socketwrappers.ServerTCP;
import utils.FileHandler;
import utils.Protocol;
import utils.StreamProcessors;

/**
 * @author Joao Antunes
 *
 */
public class Retrieve  extends Command{
	
	private ServerTCP _server;
	
	public Retrieve (ServerTCP server){
		_server = server;
	}
	
	@Override
	public void run() {
		try{
			String[] input = new String[0];
			try {
				input = _server.receive().split(" ");
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(input.length<2){
				if(input[0].equals(Protocol.DOWN_FILE)){
					String fileName = input[1];
					String echoToScreen = fileName + " " + _server.getClientSocket().getInetAddress().getHostAddress() + " " + _server.getClientSocket().getPort();
					System.out.println(echoToScreen);
					String responseCommand = Protocol.DOWN_RESPONSE;
					try {
						byte[] file = FileHandler.upFile(System.getProperty("user.dir") + "/storagefiles/" + fileName); //check for missing file
						byte[] init = (responseCommand + "ok " + (file.length-1) + " ").getBytes();
						_server.send(StreamProcessors.concatByte(init, init.length, file, file.length));
					} catch (FileNotFoundException e) {
						//missing file
						_server.send((responseCommand + "nok\n").getBytes());
					} catch (SocketException e){
						//socket business
					} finally{
						_server.closeClient();
					}
				}
				else{
					_server.send(Protocol.ERROR);
					_server.closeClient();
				}
			}
			else{
				_server.send(Protocol.ERROR);
				_server.closeClient();
			}
		} catch (IOException e){
			//blow up business
		}
		
		System.exit(1);
	}
}
