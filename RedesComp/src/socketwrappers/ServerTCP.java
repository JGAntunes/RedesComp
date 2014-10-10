package socketwrappers;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

import utils.StreamProcessors;

/**
 * 
 */

/**
 * @author Grupo 3
 *
 */
public class ServerTCP{

	private DataOutputStream _output;
	private BufferedInputStream _input;
	private Socket _clientSocket;
	private ServerSocket _serverSocket;
	private int _port;
	
	/**
	 * The TCP server constructor, we receive as an argument the port of the machine were we allocate the server.
	 * We also creater a server socket that we bound to the specified port.
	 */
	public ServerTCP(int port) throws IOException{
		_port = port;
		_serverSocket = new ServerSocket(_port);
		System.out.println("Running on TCP: " + _port);
		_input = null;
		_clientSocket = null;
		_output = null;
	}
	
	public void closeClient() throws IOException{
		_clientSocket.close();
	}
	
	public void close() throws IOException{
		_serverSocket.close();
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
	public void setClientSocket(Socket clientSocket) throws IOException {
		_clientSocket.close();
		_clientSocket = clientSocket;
	}
	
	/**
	 * The current socket is closed and we bind the ServerSocket to the socket adress we get from the new
	 * InetSocketAdress. (The InetSocketAdress creates a socket address from an IP address and a port number) 
	 */
	public void reconnect() throws IOException{
		_serverSocket.close();
		_serverSocket = new  ServerSocket(_port);
	}
	
	/**
	 * This function listens for a connection to be made to the serverSocket and if one is made it accepts it.
	 */
	public void waitConnection() throws IOException{
		_clientSocket = _serverSocket.accept();
		_output = new DataOutputStream(_clientSocket.getOutputStream());
		_input = new BufferedInputStream(_clientSocket.getInputStream());
	}
	
	public void send(String message) throws IOException, NullPointerException{
		PrintWriter out = new PrintWriter(_output, true);
		out.println(message);
	}
	
	public void send(byte[] message) throws IOException, NullPointerException{
		_output.write(message, 0, message.length);
	}
	
	public MessageTCP receive(int expectedArgs, boolean data) throws IOException, NullPointerException{
		return StreamProcessors.getTCPInput(_input, expectedArgs, data);
	}

	public String receive() throws IOException, NullPointerException{
		Scanner sc = new Scanner(_input);
		return sc.nextLine();
	}
	
	public String receive(boolean b) throws IOException, NullPointerException{
		Scanner sc = new Scanner(_input);
		return sc.next();
	}

}
