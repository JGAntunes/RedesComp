package socketwrappers;
import java.io.IOException;
import java.net.*;

/**
 * @author Grupo 3
 *
 */
public class ServerUDP{
		
	private DatagramSocket _socket;
	private byte[] _inputBuffer;
	private byte[] _outputBuffer;
	private int _port;
	
	/**
	 * UDP server constructor that receives a port as an argument. Two buffers are created, an input and an output, and a socket is created wich is binded to the sprecified
	 * port.
	 */
	public ServerUDP(int port) throws SocketException{
		_inputBuffer = new byte[5120];
		_outputBuffer = new byte[5120];
		_port = port;
		_socket = new DatagramSocket(_port);
	}
	
	/**
	 * Returns the UDP server socket.
	 */
	public DatagramSocket getSocket() {
		return _socket;
	}
	
	/**
	 * Sets a new socket to the UDP server.
	 */
	public void setSocket(DatagramSocket socket) {
		_socket.close();
		_socket = socket;
	}
	
	/**
	 * Returns the UDP server port.
	 */
	public int getPort() {
		return _port;
	}
	
	/**
	 * Sets a new port to the UDP server.
	 */
	public void setPort(int port) throws IOException {
		_port = port;
		reconnect();
	}
	
	/**
	 * A new socket was set and we need to close the previous one and connect the new one.
	 */
	public void reconnect() throws IOException{
		_socket.close();
		_socket = new DatagramSocket();
	}
	
	/**
	 * To close the UDP server we close its socket.
	 */
	public void close() throws IOException{
		_socket.close();
	}

	/**
	 * ????????
	 */
	public MessageUDP receiveMessage() throws IOException{
		DatagramPacket receivePacket = new DatagramPacket(_inputBuffer, _inputBuffer.length);
		_socket.receive(receivePacket);
		MessageUDP message = new MessageUDP(_socket.getInetAddress(), receivePacket.getPort(), new String( receivePacket.getData()));
		return message;
	}
	
	/**
	 * A datagram packet is created to send a message trought the socket.
	 */
	public void sendMessage(MessageUDP message) throws IOException{
		_outputBuffer = message.getMessage().getBytes();
		DatagramPacket sendPacket = new DatagramPacket(_outputBuffer, _outputBuffer.length, message.getIPAddress(), message.getPort());
		_socket.send(sendPacket);
	}
}
