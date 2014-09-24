/**
 * 
 */
package servers;

import java.io.IOException;
import java.net.SocketException;

import socketwrappers.*;

/**
 * @author Joao Antunes
 *
 */
public class CS {
	private ListServer _listServer;
	private UploadServer _uploadServer;
	private String _name;
	private int _port;
	
	public CS(String name, int port) throws IOException, SocketException{
		_name = name;
		_port = port;
	}
	
	private class ListServer implements Runnable{
		
		private ServerUDP _server;
			
		@Override
		public void run() {
			_server = new ServerUDP(_port);
			
		}
	}
	
	private class UploadServer implements Runnable{
		
		private ServerTCP _server;
			
		@Override
		public void run() {
			_server = new ServerTCP(_port);
			_server.waitConnection();
		}
	}
}
