package socketwrappers;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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

	private BufferedReader _inputString;
	private BufferedInputStream _inputByte;
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

	public void setPort(int port) throws IOException {
		_port = port;
		reconnect();
	}

	public ServerSocket getServerSocket() {
		return _serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) throws IOException {
		_serverSocket.close();
		_serverSocket = serverSocket;
	}

	public Socket getClientSocket() {
		return _clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		_clientSocket = clientSocket;
	}
	
	public void reconnect() throws IOException{
		_serverSocket.close();
		_serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), _port));
	}
	
	public void waitConnection() throws IOException{
		_clientSocket = _serverSocket.accept();
	}
	
	public void sendMessage(String message) throws IOException{
		_output = new DataOutputStream(_clientSocket.getOutputStream());
		_output.writeBytes(message);
		_output.close();
	}
	
	public String receiveStringMessage() throws IOException{
		_inputString = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
		String out = _inputString.readLine();
		_inputString.close();
		return out;
	}
	
	public byte[] receiveByteMessage() throws IOException{
		byte[] resultBuff = new byte[0];
		byte[] buff = new byte[133169152];
		int k = -1;
		_inputByte = new BufferedInputStream(_clientSocket.getInputStream());

		while ((k = _inputByte.read(buff, 0, buff.length)) > -1) {
			byte[] tempBuff = new byte[resultBuff.length + k];
			System.arraycopy(resultBuff, 0, tempBuff, 0, resultBuff.length);
			System.arraycopy(buff, 0, tempBuff, resultBuff.length, k);
			resultBuff = tempBuff;
		}
		_inputByte.close();
		return resultBuff;
	}

}
