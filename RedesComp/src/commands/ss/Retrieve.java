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
import utils.LocalPaths;
import utils.Protocol;
import utils.StreamProcessors;

/**
 * @author Joao Antunes
 *
 */
public class Retrieve  extends Command{
	
	private ServerTCP _server;
	private String[] _input;
	
	public Retrieve (ServerTCP server, MessageTCP message){
		_input = message.getStrParams();
		_server = server;
	}
	
	@Override
	public void run() {
		try{
			if(_input.length == 1){
				String fileName = _input[0];
				String echoToScreen = fileName + " " + _server.getClientSocket().getInetAddress().getHostAddress() + " " + _server.getClientSocket().getPort();
				System.out.println(echoToScreen);
				String responseCommand = Protocol.DOWN_RESPONSE;
				try {
					byte[] file = FileHandler.upFile(LocalPaths.STORED + fileName); //check for missing file
					byte[] init = (responseCommand + " ok " + (file.length-1) + " ").getBytes();
					_server.send(StreamProcessors.concatByte(init, init.length, file, file.length));
				} catch (FileNotFoundException e) {
					_server.send((responseCommand + " nok\n").getBytes());
				} catch (SocketException e){
					_server.send(Protocol.ERROR);
					_server.closeClient();
				} finally{
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
	}
}
