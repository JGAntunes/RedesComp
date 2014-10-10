/**
 * 
 */
package servers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import commands.Command;
import commands.cs.Retrieve;
import commands.cs.Upload;
import socketwrappers.*;
import utils.Errors;
import utils.Protocol;

/**
 * @author Grupo 3
 *
 */
public class CS {

	 
	private static ServerUDP _listSocket;
	private static ClientTCP _clientSocket;
	private static ServerTCP _serverSocket;
	private static String _name;
	private static String _ip;
	private static int _port;
	public static final int DEFAULT_PORT = 58003;
	
	private static class ListServer implements Runnable{
			
		
		ListServer(){}
		
		/**
		 * We get the UDP server running by creating one, we pass as an argument the port of the machine were UDP server will run.
		 */
		@Override
		public void run() {
			try {
				_listSocket = new ServerUDP(_port);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private static class UploadServer implements Runnable{
		
		UploadServer(){}
		
		/**
		 * We get the TCP server running by creating one, we pass as an argument the port of the machine were TCP server will run. After creating one we 
		 * just have to await a connection.
		 */
		@Override
		public void run() {
			try {
				_serverSocket = new ServerTCP(_port);
				while(true){
					try {
						_serverSocket.waitConnection();
					} catch (IOException e) {
						System.err.println(Errors.WAITING_PROBLEM);
						System.exit(-1);
					}
					try {
						protocolParser(_serverSocket.preReceive(3));
					} catch (NullPointerException e) {
						System.err.println(Errors.NO_CLIENT_SOCKET);
						System.exit(-1);
					} catch (SocketException e) {
						System.err.println(Errors.SOCKET_PROBLEM);
						System.exit(-1);
					} catch (IOException e) {
						System.err.println(Errors.IO_PROBLEM);
						System.exit(-1);
					}
				}
			} catch(Exception e){
				System.err.println(Errors.UNKNOWN_ERROR);
				System.exit(-1);
			}
		}
	}
	
	
	/**
	 * This is the parser we use to call out the CS if the command used is valid.
	 */
	public static int initParser(String[] string) throws NumberFormatException, IOException{
		if(string.length == 0){
			return DEFAULT_PORT;
		}
		else if(string.length == 2){
			if(string[0].equals("-p")){
				return Integer.parseInt(string[1]);
			}
		}
		else{
			throw new IOException("[CS]Invalid init input: " + string);
		}
		return 0;
	}
	
	
	/**
	 * The parser that we use to call out a command, if it is a valid one.
	 */
	private static void protocolParser(String string){
		try {
			Command co;
			if(string.isEmpty()){
				System.err.println(Errors.INVALID_PROTOCOL);
				_serverSocket.send(Protocol.ERROR);
			}
			else if(string.equals(Protocol.DOWN_FILE)){
				co = new Retrieve(_serverSocket);
				co.run();
			}
			else if(string.equals(Protocol.UP_CS_FILE)){
				co = new Upload(_clientSocket, _serverSocket, 2, true);
				co.run();
			}
			else{
				System.err.println(Errors.INVALID_PROTOCOL);
				_serverSocket.send(Protocol.ERROR);
				
			}
		} catch (NullPointerException e) {
			System.err.println(Errors.NO_CLIENT_SOCKET);
			System.exit(-1);
		} catch (IOException e) {
			System.err.println(Errors.IO_PROBLEM);
			System.exit(-1);
		}
	}
	
	/**
	 * We use this function to read lines from a file and save them to an array.
	 */
	private static String[] readFromFile(String path) throws IOException, FileNotFoundException, NullPointerException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		ArrayList <String> lines = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null) {
		    lines.add(line);
		}
		reader.close();
		if(lines.size() == 0){
			throw new NullPointerException("[EmptyFile]");
		}
		return (String[]) lines.toArray();
	}
	
	public static void main(String[] args){
		try {
			_port = initParser(args);
			(new UploadServer()).run();
			(new ListServer()).run();
		} catch (NumberFormatException e) {
			System.err.println(Errors.INVALID_INITIALIZERS);
			System.exit(-1);
			
		} catch (SocketException e) {
			System.err.println(Errors.SOCKET_PROBLEM);
			System.exit(-1);
			
		} catch (IOException e) {
			System.err.println(Errors.IO_PROBLEM);
			System.exit(-1);
			
		} catch (Exception e) {
			System.err.println(Errors.UNKNOWN_ERROR);
			System.exit(-1);
		}
	}
	
}
