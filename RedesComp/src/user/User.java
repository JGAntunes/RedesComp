/**
 * 
 */
package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;

import commands.Command;
import commands.List;
import commands.Retrieve;
import commands.Upload;
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
	private static String _CSName;
	private static int _CSPort;
	private static String _SSName;
	private static int _SSPort;
	private static BufferedReader _input;
	private static String _fileName;
	

	public static void main(String[] args){
		initParser(args);
		System.out.println(_CSName);
		System.out.println(_CSPort);
		try {
			_input = new BufferedReader(new InputStreamReader(System.in));
			while(emitter(_input.readLine())){}
		} catch (IllegalArgumentException e) {
			System.err.println(Errors.INVALID_COMMAND);
			System.exit(-1);
		} catch (UnknownHostException e) {
			System.err.println(Errors.UNKNOWN_HOST);
			System.exit(-1);
		} catch (SocketException e) {
			System.err.println(Errors.SOCKET_PROBLEM);
			System.exit(-1);
		}catch (IOException e) {
			System.err.println(Errors.IO_INPUT);
			System.exit(-1);
		}
	}
	
	/**
	 * This is the parser we use to call out the user if the command used is valid.
	 */
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
	
	/**
	 * This is the function we use to call out the commands.
	 */
	public static boolean emitter(String input) throws UnknownHostException, IOException{
		Command com = null;
		if(input.equals(Protocol.LIST_COMMAND)){
			com = new List(new ClientUDP(), _CSName, _CSPort, null);
		}
		else if(input.startsWith(Protocol.RETRIEVE_COMMAND + " ")){
			if(_SSName != null){
				String[] arguments = input.split(Protocol.RETRIEVE_COMMAND + " ");
				if(arguments.length == 2){
					com = new Retrieve(new ClientTCP(_SSName, _SSPort), _SSName, _SSPort, arguments[1].split(" "));
				}
				else{
					System.out.println(Errors.INVALID_COMMAND);
				}
			}
			else{
				System.out.println("Please use the list command first.");
			}
		}
		else if(input.startsWith(Protocol.UPLOAD_COMMAND + " ")){
			String[] arguments = input.split(Protocol.UPLOAD_COMMAND + " ");
			if(arguments.length == 2){
				com = new Upload(new ClientTCP(_CSName, _CSPort), _CSName, _CSPort, arguments[1].split(" "));
			}
			else{
				System.out.println(Errors.INVALID_COMMAND);
			}
		}
		else if(input.equals(Protocol.EXIT_COMMAND)){
			return false;
		}
		else{
			System.out.println(Errors.INVALID_COMMAND);
			return true;
		}
		if(com != null){
			com.run();
		}
		
		return true;
	}
	
	/**
	 * Function used to set the port and name of the storage server that we have to acess to retrieve or upload any file.
	 */
	public static void setSS(String name, int port){
		_SSName = name;
		_SSPort = port;
	}
}
