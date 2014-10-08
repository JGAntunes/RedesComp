/**
 * 
 */
package utils;

/**
 * @author Joao Antunes
 *
 */
public class Protocol {
	
	public final static String LIST_COMMAND = new String("list");
	
	public final static String RETRIEVE_COMMAND = new String("retrieve");
	
	public final static String UPLOAD_COMMAND = new String("upload");
	
	public final static String EXIT_COMMAND = new String("exit");
	
	public final static String LIST = new String("LST");
	
	public final static String LIST_RESPONSE = new String("AWL");
	
	public final static String CHECK_FILE = new String("UPR");
	
	public final static int CHECK_FILE_ARGS = 2;
	
	public final static String CHECK_FILE_RESPONSE = new String("AWR");
	
	public final static int CHECK_FILE_RESPONSE_ARGS = 2;
	
	public final static String IN_USE = new String("dup");
	
	public final static String AVAILABLE = new String("new");
	
	public final static String UP_USER_FILE = new String("UPC");
	
	public final static int UP_USER_FILE_ARGS = 2;
	
	public final static String UP_USER_RESPONSE = new String("AWC");
	
	public final static int UP_USER_RESPONSE_ARGS = 2;
	
	public final static String OK = new String("ok");
	
	public final static String NOT_OK = new String("nok");
	
	public final static String UP_CS_FILE = new String("UPS");
	
	public final static int UP_CS_FILE_ARGS = 3;
	
	public final static String UP_CS_RESPONSE = new String("AWS");
	
	public final static int UP_CS_RESPONSE_ARGS = 2;
	
	public final static String DOWN_FILE = new String("REQ");
	
	public final static int DOWN_FILE_ARGS = 2;
	
	public final static String DOWN_RESPONSE = new String("REP");
	
	public final static int DOWN_RESPONSE_ARGS = 3;
	
	public final static String ERROR = new String("ERR");
	
	public final static String EOF = new String("\u001a");
	
	private Protocol(){}
	
}
