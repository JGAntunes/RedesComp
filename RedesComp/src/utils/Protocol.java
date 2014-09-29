/**
 * 
 */
package utils;

/**
 * @author Joao Antunes
 *
 */
public class Protocol {
	
	public final static String LIST = new String("LST");
	
	public final static String LIST_RESPONSE = new String("AWL");
	
	public final static String CHECK_FILE = new String("UPR");
	
	public final static String CHECK_FILE_RESPONSE = new String("AWR");
	
	public final static String UP_USER_FILE = new String("UPC");
	
	public final static String UP_USER_RESPONSE = new String("AWC");
	
	public final static String UP_CS_FILE = new String("UPS");
	
	public final static String UP_CS_RESPONSE = new String("AWS");
	
	public final static String DOWN_FILE = new String("REQ");
	
	public final static String DOWN_RESPONSE = new String("REP");
	
	public final static String ERROR = new String("ERR");
	
	private Protocol(){}
	
}
