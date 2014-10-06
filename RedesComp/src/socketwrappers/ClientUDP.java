/**
 * 
 */
package socketwrappers;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author Joao Antunes
 *
 */
public class ClientUDP {
	private DatagramSocket _socket;
	private byte[] _inputBuffer;
	private byte[] _outputBuffer;
	private String _host;
	private int _port;
	
	public ClientUDP(String host, int port) throws UnknownHostException, IOException{
		_host = host;
		_port = port;
		_inputBuffer = new byte[5120];
		_outputBuffer = new byte[5120];
		_socket = new DatagramSocket(port, InetAddress.getByName(_host));
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

	public DatagramSocket getSocket() {
		return _socket;
	}

	public void setServerSocket(DatagramSocket socket) {
		_socket = socket;
	}
	
	public void reconnect() throws IOException{
		_socket.connect(new InetSocketAddress(_host, _port));
	}
	
	public MessageUDP receiveFromServer() throws IOException{
		DatagramPacket receivePacket = new DatagramPacket(_inputBuffer, _inputBuffer.length);
		_socket.receive(receivePacket);
		MessageUDP message = new MessageUDP(receivePacket.getPort(), new String( receivePacket.getData()), _socket.getInetAddress());
		return message;
	}
	
	public void sendToServer(MessageUDP message) throws IOException{
		_outputBuffer = message.getMessage().getBytes();
		DatagramPacket sendPacket = new DatagramPacket(_outputBuffer, _outputBuffer.length, message.getIPAddress(), message.getPort());
		_socket.send(sendPacket);
	}
}