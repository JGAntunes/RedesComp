package socketwrappers;
import java.net.InetAddress;

/**
 * 
 */

/**
 * @author Joao Antunes
 *
 */
public class MessageUDP {
	private int _port;
	private String _message;
	private InetAddress _IPAddress;
	
	public MessageUDP(int port, String message, InetAddress IPAddress){
		_port = port;
		_message = message;
		_IPAddress = IPAddress;
	}
	
	public String getMessage(){
		return _message;
	}
	
	public int getPort(){
		return _port;
	}
	
	public void setPort(int port){
		_port = port;
	}
	
	public void setMessage(String message){
		_message = message;
	}

	public InetAddress getIPAddress() {
		return _IPAddress;
	}

	public void setIPAddress(InetAddress IPAddress) {
		_IPAddress = IPAddress;
	}
}
