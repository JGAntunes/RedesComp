/**
 * 
 */
package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import servers.CS;
import socketwrappers.ClientTCP;
import socketwrappers.ClientUDP;

/**
 * @author Joao Antunes
 *
 */
public class User {
	private static ClientTCP _clientTCP;
	private static ClientUDP _clientUDP;
	private static String _CSName;
	private static int _CSPort;
	private static BufferedReader _input;
	

	public static void main(String[] args){
		initParser(args);
		try {
			_clientTCP = new ClientTCP(_CSName, _CSPort);
			_clientUDP = new ClientUDP(_CSName, _CSPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_input = new BufferedReader(new InputStreamReader(System.in));
		try {
			while(commandParser(_input.readLine())){}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	public static void initParser(String[] args) throws IllegalArgumentException{
		_CSPort = CS.DEFAULT_PORT;
		_CSName = "localhost";
		if(args.length != 0){
			if(args.length == 2){
				if(args[0].equals("-n")){
					_CSName = args[1];
				}
				else if(args[0].equals("-p")){
					_CSPort = Integer.parseInt(args[1]);
				}
				else{
					throw new IllegalArgumentException("Wrong input args");
				}
			}
			else if(args.length == 4){
				if(args[0].equals("-n") && args[2].equals("-p")){
					_CSName = args[1];
					_CSPort = Integer.parseInt(args[3]);
				}
				else{
					throw new IllegalArgumentException("Wrong input args");
				}
			}
			else{
				throw new IllegalArgumentException("Wrong input args");
			}
		}
	}
	
	public static boolean commandParser(String input) throws IllegalArgumentException{
		if(input.equals("list")){
			
		}
		else if(input.startsWith("retrieve")){
			
		}
		else if(input.startsWith("upload")){
			
		}
		else if(input.equals("exit")){
			
		}
		else{
			
		}
	}
}
