/**
 * 
 */
package user;

import socketwrappers.ClientTCP;
import socketwrappers.ClientUDP;

/**
 * @author Joao Antunes
 *
 */
public class User {
	ClientTCP _clientTCP;
	ClientUDP _clientUDP;
	String _CSName;
	int _CSPort;
}
