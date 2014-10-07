package socketwrappers;
import java.io.IOException;
import java.net.*;

/**
 * @author Joao Antunes
 *
 */
public class ServerUDP{
		
	private DatagramSocket _socket;
	private byte[] _inputBuffer;
	private byte[] _outputBuffer;
	private int _port;
	
	public ServerUDP(int port) throws SocketException{
		_inputBuffer = new byte[5120];
		_outputBuffer = new byte[5120];
		_port = port;
		_socket = new DatagramSocket(_port);
	}
	
	public DatagramSocket getSocket() {
		return _socket;
	}

	public void setSocket(DatagramSocket socket) {
		_socket.close();
		_socket = socket;
	}

	public int getPort() {
		return _port;
	}

	public void setPort(int port) throws IOException {
		_port = port;
		reconnect();
	}
	
	public void reconnect() throws IOException{
		_socket.close();
		_socket.bind(new InetSocketAddress(InetAddress.getLocalHost(), _port));
	}
	
	public void close() throws IOException{
		_socket.close();
	}

	public MessageUDP receiveMessage() throws IOException{
		DatagramPacket receivePacket = new DatagramPacket(_inputBuffer, _inputBuffer.length);
		_socket.receive(receivePacket);
		MessageUDP message = new MessageUDP(_socket.getInetAddress(), receivePacket.getPort(), new String( receivePacket.getData()));
		return message;
	}
	
	public void sendMessage(MessageUDP message) throws IOException{
		_outputBuffer = message.getMessage().getBytes();
		DatagramPacket sendPacket = new DatagramPacket(_outputBuffer, _outputBuffer.length, message.getIPAddress(), message.getPort());
		_socket.send(sendPacket);
	}
}
