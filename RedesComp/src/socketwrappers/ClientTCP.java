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
	
	/**
	 * The TCP client constructor that receives as arguments the name of the host machine and its port. Then we create a socket using this information that
	 * is later used to create an output stream.
	 */
	public ClientTCP(String host, int port) throws UnknownHostException, IOException{
		_host = host;
		_port = port;
		_socket = new Socket(host, port);
		_output = new DataOutputStream(_socket.getOutputStream());
	}
	
	/**
	 * Return the port of the TCP client.
	 */
	public int getPort() {
		return _port;
	}

	/**
	 * Function to set another port to the TCP client.
	 */
	public void setPort(int port) throws IOException {
		_port = port;
		reconnect();
	}
	
	/**
	 * Returns the host machine name.
	 */
	public String getHost() {
		return _host;
	}
	
	/**
	 * Function to set another hots to the TCP client.
	 */
	public void setHost(String host) throws IOException {
		_host = host;
		reconnect();
	}

	/**
	 * Returns the TCP client socket.
	 */
	public Socket getSocket() {
		return _socket;
	}

	/**
	 * Function to set a new socket to the TCP client.
	 */
	public void setServerSocket(Socket socket) {
		_socket.close();
		_socket = socket;
	}
	
	/**
	 * A new socket was set and we need to close the previous one and connect the new one.
	 */
	public void reconnect() throws IOException{
		_socket.close()
		_socket.connect(new InetSocketAddress(_host, _port));
	}
	
	/**
	 * To close the TCP client we need to close the current socket.
	 */
	public void close() throws IOException{
		_socket.close();
	}
	
	/**
	 * The function sends a message, received as an argument(as a string), to the server by creating an output stream to the socket.
	 */
	public void sendToServer(String message) throws IOException{
		_output = new DataOutputStream(_socket.getOutputStream());
		_output.writeBytes(message + '\n');
	}
	
	/**
	 * The function sends a message, received as an argument(as an array of bytes), to the server by creating an output stream to the socket.
	 */
	public void sendToServer(byte[] message, int length) throws IOException{
		_output = new DataOutputStream(_socket.getOutputStream());
		_output.write(message, 0, length);
	}
	
	/**
	 * Function to receive a message from the server, an input stream to the socket is created and used to retrive the message.
	 */
	public String receiveFromServer() throws IOException{
		_input = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		return _input.readLine();
	}
}