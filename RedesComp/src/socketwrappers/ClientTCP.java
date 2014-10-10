/**
 * 
 */
package socketwrappers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import utils.FileHandler;
import utils.Protocol;
import utils.StreamProcessors;

/**
 * @author Grupo 3
 *
 */
public class ClientTCP {
	private Socket _socket;
	private DataOutputStream _output;
	private BufferedInputStream _input;
	private String _host;
	private int _port;
	
	public ClientTCP(String host, int port) throws UnknownHostException, IOException{
		_host = host;
		_port = port;
		_socket = new Socket(host, port);
		if(_socket.isConnected()){
			System.out.println("Successfully connected to: " + _host + " " + _port);
		}
		else{
			System.out.println("Failded to connect to server.");
		}
		_output = new DataOutputStream(_socket.getOutputStream());
		_input = new BufferedInputStream(_socket.getInputStream());
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
	
	public boolean isConnected() {
		return _socket.isConnected();
	}

	public void setServerSocket(Socket socket) {
		_socket = socket;
	}
	
	public void reconnect() throws IOException{
		_socket.close();
		_socket = new Socket(_host, _port);
	}
	
	public void close() throws IOException{
		_socket.close();
	}
	
	public void sendToServer(String message) throws IOException{
		PrintWriter out = new PrintWriter(_output, true);
		out.println(message);
	}
	
	public void sendToServer(byte[] message) throws IOException{
		System.out.println(new String(message, "UTF-8"));
		_output.write(message, 0, message.length);
	}
	
	public MessageTCP receiveFromServer(int expectedArgs, boolean data) throws IOException{
		return StreamProcessors.getTCPInput(_input, expectedArgs, data);
	}

	public String receiveFromServer() throws IOException{
		Scanner sc = new Scanner(_input);
		return sc.nextLine();
	}
}