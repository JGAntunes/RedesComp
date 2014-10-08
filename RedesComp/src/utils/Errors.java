/**
 * 
 */
package utils;

/**
 * @author Joao Antunes
 *
 */
/**
 * All the possible errors and the string we present to the user in case one of them happens.
 */
public class Errors {
	public final static String INVALID_COMMAND = new String(">> Invalid command used, please refer to the available commands.");
	
	public final static String INVALID_PROTOCOL = new String(">> Invalid protocol used, please refer to the used protocol.");
	
	public final static String SERVER_BUSY = new String(">> Server currently busy and unable to respond, please try again later.");
	
	public final static String UNKNOWN_HOST = new String(">> Unknown host.");
	
	public final static String SOCKET_PROBLEM = new String(">> Problems with the socket connection. Socket could not be opened.");
	
	public final static String IO_INPUT = new String(">> IO problems with the socket connection, or establishing data buffers.");
	
	public final static String FILE_PATH = new String(">> Problems saving file in the given path.");
	
	public final static String SAVING_FILE = new String(">> Could not save file.");
}
