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
import socketwrappers.MessageUDP;
import utils.*;

/**
 * @author Joao Antunes
 *
 */
public class User {
	private static ClientTCP _clientTCP;
	private static ClientUDP _clientUDP;
	private static String _CSName;
	private static int _CSPort;
	private static String _SSName;
	private static int _SSPort;
	private static BufferedReader _input;
	

	public static void main(String[] args){
		initParser(args);
		System.out.println(_CSName);
		System.out.println(_CSPort);
		try {
			_clientTCP = new ClientTCP(_CSName, _CSPort);
			_clientUDP = new ClientUDP();
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
	
	public static boolean commandParser(String input){
		if(input.equals("list")){
			System.out.println("- Sent list");
			try {
				_clientUDP.sendToServer(new MessageUDP(_CSName, _CSPort, Protocol.LIST + "\n"));
				MessageUDP msg = _clientUDP.receiveFromServer();
				System.out.println(msg.getMessage());
				showOutput(msg.getMessage().split(" "));
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(input.startsWith("retrieve")){
			
		}
		else if(input.startsWith("upload")){
			
		}
		else if(input.equals("exit")){
			return false;
		}
		else{
			System.out.println("Invalid command");
		}
		return true;
	}
	
	public static void showOutput(String[] data){
		if(data.length == 0){
			System.out.println("No message received from the server");
		}
		else if(data[0].equals(Protocol.LIST_RESPONSE)){
			if(data.length > 4){
				_SSName = data[1];
				_SSPort = Integer.parseInt(data[2]);
				int numFiles = Integer.parseInt(data[3]);
				System.out.println("Available files for download:");
				for(int i = 1; i <= numFiles; i++){
					System.out.println(i + " " + data[3+i]);
				}
			}
		}
		else if(data[0].equals(Protocol.CHECK_FILE_RESPONSE)){
			
		}
		else if(data[0].equals(Protocol.UP_USER_RESPONSE)){
			
		}
		else if(data[0].equals(Protocol.DOWN_RESPONSE)){
			
		}
		else if(data[0].equals(Protocol.ERROR)){
			
		}
		else if(data[0].equals(Protocol.EOF)){
			
		}
	}
}
