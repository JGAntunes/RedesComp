package socketwrappers;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * 
 */

/**
 * @author Joao Antunes
 *
 */
public class ServerTCP{

	
	private BufferedReader _input;
	private DataOutputStream _output;
	private Socket _clientSocket;
	private ServerSocket _serverSocket;
	private int _port;
	
	public ServerTCP(int port) throws IOException{
		_port = port;
		_serverSocket = new ServerSocket(getPort());
	}
	
	public int getPort() {
		return _port;
	}

	public void setPort(int port) {
		_port = port;
	}

	public ServerSocket getServerSocket() {
		return _serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		_serverSocket = serverSocket;
	}

	public Socket getClientSocket() {
		return _clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		_clientSocket = clientSocket;
	}
	
	
	public void waitConnection() throws IOException{
		_clientSocket = _serverSocket.accept();
	}
	
	public void sendMessage(String message) throws IOException{
		_output = new DataOutputStream(_clientSocket.getOutputStream());
		_output.writeBytes(message);
	}
	
	public String receiveMessage() throws IOException{
		_input = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
		return _input.readLine();
	}

}
