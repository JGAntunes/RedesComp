package socketwrappers;
import java.io.IOException;
import java.net.*;

/**
 * @author Joao Antunes
 *
 */
public class ServerUDP implements Runnable{
		
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
		_socket = socket;
	}

	public int getPort() {
		return _port;
	}

	public void setPort(int port) {
		_port = port;
	}

	public MessageUDP receiveMessage() throws IOException{
		DatagramPacket receivePacket = new DatagramPacket(_inputBuffer, _inputBuffer.length);
		_socket.receive(receivePacket);
		MessageUDP message = new MessageUDP(receivePacket.getPort(), new String( receivePacket.getData()), _socket.getInetAddress());
		return message;
	}
	
	public void sendMessage(MessageUDP message) throws IOException{
		_outputBuffer = message.getMessage().getBytes();
		DatagramPacket sendPacket = new DatagramPacket(_outputBuffer, _outputBuffer.length, message.getIPAddress(), message.getPort());
		_socket.send(sendPacket);
	}

	@Override
	public void run() {
		
	}
}
