/**
 * 
 */
package servers;

import java.io.IOException;
import java.net.SocketException;

import socketwrappers.ServerTCP;
import utils.Protocol;

/**
 * @author Joao Antunes
 *
 */
public class SS {
	private StorageServer _server;
	private String _name;
	private int _port;
	private static final int DEFAULT_PORT = 59000;
	
	public SS(String name, int port) throws IOException, SocketException{
		_name = name;
		_port = port;
	}
	
	private class StorageServer implements Runnable{
		
		private ServerTCP _server;
			
		@Override
		public void run() {
			try {
				_server = new ServerTCP(_port);
				_server.waitConnection();
				//while(cenas);
				//do coisas;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static int initParser(String[] string) throws NumberFormatException, IOException{
		if(string.length == 0){
			return DEFAULT_PORT;
		}
		else if(string.length == 2){
			if(string[0].equals("-p")){
				return Integer.parseInt(string[1]);
			}
		}
		else{
			throw new IOException("[SS]Invalid init input: " + string);
		}
		return 0;
	}
	
	private static void protocolParser(String string) throws IOException{
		String[] tokens = string.split(" ");
		if(tokens.length == 0){
			throw new IOException("[SS]Invalid protocol usage, empty command received");
		}
		else if(tokens[0].equals(Protocol.DOWN_FILE)){
			//send file to user
		}
		else if(tokens[0].equals(Protocol.UP_CS_FILE)){
			//receive file from cs
		}
		else{
			throw new IOException("[SS]Invalid protocol usage: " + string);
		}
	}
}
