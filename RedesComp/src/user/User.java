/**
 * 
 */
package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
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
	private static String _fileName;
	

	public static void main(String[] args){
		initParser(args);
		System.out.println(_CSName);
		System.out.println(_CSPort);
		try {
			_clientTCP = new ClientTCP(_CSName, _CSPort);
			_clientUDP = new ClientUDP();
		} catch (SocketException e) {
			System.err.println(Errors.SOCKET_PROBLEM);
			System.exit(-1);
		} catch (UnknownHostException e) {
			System.err.println(Errors.UNKNOWN_HOST);
			System.exit(-1);
		} catch (IOException e) {
			System.err.println(Errors.IO_SOCKET_PROBLEM);
			System.exit(-1);
		}
		_input = new BufferedReader(new InputStreamReader(System.in));
		try {
			while(emitter(_input.readLine())){}
		} catch (IllegalArgumentException e) {
			System.err.println(Errors.INVALID_COMMAND);
			System.exit(-1);
		} catch (UnknownHostException e) {
			System.err.println(Errors.UNKNOWN_HOST);
			System.exit(-1);
		} catch (IOException e) {
			System.err.println(Errors.IO_INPUT);
			System.exit(-1);
		}
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
	
	public static boolean emitter(String input) throws UnknownHostException, IOException{
		String bufferTCP;
		MessageUDP bufferUDP;
		if(input.equals(Protocol.LIST_COMMAND)){
			_clientUDP.sendToServer(new MessageUDP(_CSName, _CSPort, Protocol.LIST + "\n"));
			System.out.println(">> Sent list");
			bufferUDP = _clientUDP.receiveFromServer();
			processOutput(bufferUDP.getMessage().split(" "));
			
		}
		else if(input.startsWith(Protocol.RETRIEVE_COMMAND + " ")){
			_fileName = input.split("retrieve ")[1];
			_clientTCP.sendToServer(Protocol.DOWN_FILE + " " + _fileName + "\n");
			System.out.println(">> Sent retrieve");
			bufferTCP = _clientTCP.receiveFromServer();
			System.out.println(bufferTCP);
			if(processOutput(bufferTCP.split(" "))){
			}
		}
		else if(input.startsWith(Protocol.UPLOAD_COMMAND + " ")){
			_fileName = input.split("upload ")[1];
			_clientTCP.sendToServer(Protocol.CHECK_FILE + " " + _fileName + "\n");
			System.out.println(">> Sent upload");
			bufferTCP = _clientTCP.receiveFromServer();
			if(processOutput(bufferTCP.split(" "))){
				System.out.println(">> Uploading");
				_clientTCP.sendToServer(Protocol.UP_USER_FILE + "...");
				System.out.println(">> Upload finished");
				bufferTCP = _clientTCP.receiveFromServer();
			}
		}
		else if(input.equals(Protocol.EXIT_COMMAND)){
			return false;
		}
		else{
			System.out.println("Invalid command");
		}
		return true;
	}
	
	public static boolean processOutput(String[] data){
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
				return true;
			}
			return false;
		}
		else if(data[0].equals(Protocol.CHECK_FILE_RESPONSE)){
			if(data[1].equals(Protocol.IN_USE)){
				System.out.println("File name already in use, please try another one.");
				return false;
			}
			else if(data[1].equals(Protocol.AVAILABLE)){
				System.out.println("File available");
				return true;
			}
		}
		else if(data[0].equals(Protocol.UP_USER_RESPONSE)){
			if(data[1].equals(Protocol.NOT_OK)){
				System.out.println("Problems uploading file. Please try again.");
				return false;
			}
			else if(data[1].equals(Protocol.OK)){
				System.out.println("File uploaded successfully.");
				return true;
			}
		}
		else if(data[0].equals(Protocol.DOWN_RESPONSE)){
			if(data[1].equals(Protocol.NOT_OK)){
				System.out.println("Requested file isn't available. Please try again.");
				return false;
			}
			else if(data[1].equals(Protocol.OK)){
				return true;
			}
		}
		else if(data[0].equals(Protocol.ERROR)){
			
		}
		else if(data[0].equals(Protocol.EOF)){
			
		}
		return true;
	}
}
