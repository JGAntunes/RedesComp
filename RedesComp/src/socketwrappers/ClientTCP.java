/**
 * 
 */
package socketwrappers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Joao Antunes
 *
 */
public class ClientTCP {
	private Socket _socket;
	private DataOutputStream _output;
	private BufferedReader _input;
	private String _host;
	private int _port;
	
	public ClientTCP(String host, int port) throws UnknownHostException, IOException{
		_host = host;
		_port = port;
		_socket = new Socket(host, port);
		_output = new DataOutputStream(_socket.getOutputStream());
	}
	
	public int getPort() {
		return _port;
	}

	public void setPort(int port) throws IOException {
		_port = port;
		reconnect();
	}
	
	public String getHost() {
		return _host;
	}

	public void setHost(String host) throws IOException {
		_host = host;
		reconnect();
	}

	public Socket getSocket() {
		return _socket;
	}

	public void setServerSocket(Socket socket) {
		_socket = socket;
	}
	
	public void reconnect() throws IOException{
		_socket.connect(new InetSocketAddress(_host, _port));
	}
	
	public void sendToServer(String message) throws IOException{
		_output = new DataOutputStream(_socket.getOutputStream());
		_output.writeBytes(message + '\n');
	}
	
	public String receiveFromServer() throws IOException{
		_input = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		return _input.readLine();
	}
}