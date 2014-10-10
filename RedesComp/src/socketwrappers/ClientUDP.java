/**
 * 
 */
package socketwrappers;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author Grupo 3
 *
 */
public class ClientUDP {
	private DatagramSocket _socket;
	private byte[] _inputBuffer;
	private byte[] _outputBuffer;
	private String _host;
	private int _port;
	
	/**
	 * The UDP client constructor that receives as arguments the host name and the port. An input and an output buffers are created and we also create a datagram 
	 * socket bounded to the specified adress.
	 */
	public ClientUDP(String host, int port) throws UnknownHostException, SocketException{
		_host = host;
		_port = port;
		_inputBuffer = new byte[5120];
		_outputBuffer = new byte[5120];
		_socket = new DatagramSocket(port, InetAddress.getByName(host));
		_socket.setSoTimeout(180000);
	}
	
	/**
	 * Another UDP client constructor that doesnt receive any argument, by definition we assume that the host will be "localhost", a socket is created and it binds to
	 * any available port on the local host machine. The two buffers are also created.
	 */
	public ClientUDP() throws SocketException{
		_socket = new DatagramSocket();
		_socket.setSoTimeout(120000);
		_host = "localhost";
		_port = _socket.getPort();
		_inputBuffer = new byte[5120];
		_outputBuffer = new byte[5120];
	}
	
	/**
	 * Returns the port of the machine were the UDP client runs.
	 */
	public int getPort() {
		return _port;
	}
	
	/**
	 * Sets a new port to the UDP client.
	 */
	public void setPort(int port) throws IOException {
		_port = port;
		reconnect();
	}
	
	/**
	 * Returns the host of the UPD cient.
	 */
	public String getHost() {
		return _host;
	}
	
	/**
	 * Sets a new host to the UDP client.
	 */
	public void setHost(String host) throws IOException {
		_host = host;
		reconnect();
	}
	
	/**
	 * Returns the UDP client datagram socket.
	 */
	public DatagramSocket getSocket() {
		return _socket;
	}
	
	/**
	 * Sets a new datagram socket to the UDP client.
	 */
	public void setServerSocket(DatagramSocket socket) {
		_socket = socket;
	}
	
	/**
	 * A new socket was set and we need to close the previous one and connect the new one.
	 */
	public void reconnect() throws IOException{
		_socket.connect(new InetSocketAddress(_host, _port));
	}
	
	/**
	 * To close the UDP client we close its socket.
	 */
	public void close() throws IOException{
		_socket.close();
	}
	
	/**
	 * ??????????????????????????????
	 */
	public MessageUDP receiveFromServer() throws IOException{
		DatagramPacket receivePacket = new DatagramPacket(_inputBuffer, _inputBuffer.length);
		_socket.receive(receivePacket);
		MessageUDP message = new MessageUDP(_socket.getInetAddress(), receivePacket.getPort(), new String( receivePacket.getData()));
		return message;
	}
	
	/**
	 * A datagram packet is created to send a message trought the socket.
	 */
	public void sendToServer(MessageUDP message) throws IOException{
		_outputBuffer = message.getMessage().getBytes();
		DatagramPacket sendPacket = new DatagramPacket(_outputBuffer, _outputBuffer.length, message.getIPAddress(), message.getPort());
		_socket.send(sendPacket);
	}
}
