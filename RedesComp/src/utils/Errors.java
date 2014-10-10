/**
 * 
 */
package utils;

/**
 * @author Grupo 3
 *
 */
/**
 * All the possible errors and the string we present to the user in case one of them happens.
 */
public class Errors {
	
	public final static String NO_CLIENT_SOCKET = new String(">> No client socket specified. Must wait on connection first.");
	
	public final static String WAITING_PROBLEM = new String(">> Problem while waiting for client connection");
	
	public final static String INVALID_INITIALIZERS = new String(">> Invalid initializing.");
	
	public final static String INVALID_PORT_NUMBER = new String(">> Invalid port number.");
	
	public final static String INVALID_COMMAND = new String(">> Invalid command used, please refer to the available commands.");
	
	public final static String INVALID_PROTOCOL = new String(">> Invalid protocol received.");
	
	public final static String SERVER_BUSY = new String(">> Server currently busy and unable to respond, please try again later.");
	
	public final static String UNKNOWN_HOST = new String(">> Unknown host.");
	
	public final static String SOCKET_PROBLEM = new String(">> Problems with the socket connection. Socket could not be opened.");
	
	public final static String IO_PROBLEM = new String(">> IO problems with the socket connection.");
	
	public final static String FILE_PATH = new String(">> Problems saving file in the given path.");
	
	public final static String SAVING_FILE = new String(">> Could not save file.");
}
