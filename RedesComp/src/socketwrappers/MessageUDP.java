package socketwrappers;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
	
	public MessageUDP(String IPAddress, int port, String message) throws UnknownHostException{
		_port = port;
		_message = message;
		_IPAddress = InetAddress.getByName(IPAddress);
	}
	
	public MessageUDP(InetAddress IPAddress, int port, String message){
		_port = port;
		_message = message;
		_IPAddress =IPAddress;
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
