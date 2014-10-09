package socketwrappers;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import utils.StreamProcessors;

/**
 * 
 */

/**
 * @author Joao Antunes
 *
 */
public class ServerTCP{

	private BufferedReader _inputString;
	private BufferedInputStream _inputByte;
	private DataOutputStream _output;
	private Socket _clientSocket;
	private ServerSocket _serverSocket;
	private int _port;
	
	/**
	 * The TCP server constructor, we receive as an argument the port of the machine were we allocate the server.
	 * We also creater a server socket that we bound to the specified port.
	 */
	public ServerTCP(int port) throws IOException{
		_port = port;
		_serverSocket = new ServerSocket(getPort());
	}
	
	/**
	 * Function that we use to get a TCP server port.
	 */
	public int getPort() {
		return _port;
	}
	
	/**
	 * Function that we can use to set a new port to the server.
	 */
	public void setPort(int port) throws IOException {
		_port = port;
		reconnect();
	}
	
	/**
	 * Returns the TCP server socket.
	 */
	public ServerSocket getServerSocket() {
		return _serverSocket;
	}
	
	/**
	 * The TCP server sets another socket, we close the current socket and the associate with the one we get as an argument.
	 */
	public void setServerSocket(ServerSocket serverSocket) throws IOException {
		_serverSocket.close();
		_serverSocket = serverSocket;
	}
	
	/**
	 * Returns the TCP server client socket.
	 */
	public Socket getClientSocket() {
		return _clientSocket;
	}
	
	/**
	 * We set a new socket to the clientSocket.
	 */
	public void setClientSocket(Socket clientSocket) {
		_clientSocket = clientSocket;
	}
	
	/**
	 * The current socket is closed and we bind the ServerSocket to the socket adress we get from the new
	 * InetSocketAdress. (The InetSocketAdress creates a socket address from an IP address and a port number) 
	 */
	public void reconnect() throws IOException{
		_serverSocket.close();
		_serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), _port));
	}
	
	/**
	 * This fucntion listens for a connection to be made to the serverSocket and if one is made it accepts it.
	 */
	public void waitConnection() throws IOException{
		_clientSocket = _serverSocket.accept();
	}
	
	/**
	 * We get an output stream to the socket, then we write the message that is passed as an argument to the function, then 
	 * close the stream when everything has been written.
	 */
	public void sendMessage(String message) throws IOException{
		_output = new DataOutputStream(_clientSocket.getOutputStream());
		_output.writeBytes(message);
		_output.close();
	}
	
	/**
	 * First we get an input stream for the socket from where we want to retrieve the message, we read the line of text and
	 * close the stream when finished, returning the message in the end.
	 */
	public String receiveStringMessage() throws IOException{
		_inputString = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
		String out = _inputString.readLine();
		_inputString.close();
		return out;
	}
	
	
/*	public MessageTCP receiveByteMessage(int expectedArgs) throws IOException{
		return new MessageTCP(StreamProcessors.getByteArray(_clientSocket.getInputStream()), expectedArgs);
	}*/

}
