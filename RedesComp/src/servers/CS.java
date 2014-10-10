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
import java.util.Random;

import commands.Command;
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
				Random rand = new Random();
				while(true){
					MessageUDP message;
					try {
						message = _listSocket.receiveMessage();
						if(message.getMessage().equals(Protocol.LIST + '\n')){
							String echoToScreen = Protocol.LIST + " " + message.getIPAddress().getHostAddress() + " " + message.getPort();
							System.out.println(echoToScreen);
							String response = new String();
							String[] files = readFromFile("SS_List");
							if(files.length == 0){
								_listSocket.sendMessage(new MessageUDP(message.getIPAddress().getHostAddress(), message.getPort(), Protocol.EOF + "\n"));
							}
							else{
								String[] servers = readFromFile("CS_List");
								if(servers.length == 0){
									_listSocket.sendMessage(new MessageUDP(message.getIPAddress().getHostAddress(), message.getPort(), Protocol.ERROR + "\n"));
								}
								else{
								    int randomNum = rand.nextInt(servers.length);
								    String server = servers[randomNum];
								    response = Protocol.LIST_RESPONSE + " " + server + " " + files.length + " ";
								    int i = 0;
								    for(String f : files){
								    	response = response.concat(f);
								    	i++;
								    	if(files.length != i){
								    		response = response.concat(" ");
								    	}
								    }
									_listSocket.sendMessage(new MessageUDP(message.getIPAddress().getHostAddress(), message.getPort(), response + "\n"));
								}
							}
						}
						else{
							_listSocket.sendMessage(new MessageUDP(message.getIPAddress().getHostAddress(), message.getPort(), Protocol.ERROR + "\n"));
						}
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
						Thread co = new Thread(new Upload(_serverSocket, _clientSocket));
						co.start();
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
	private static void protocolParser(String[] string){

	}
	
	/**
	 * We use this function to read lines from a file and save them to an array.
	 */
	public static String[] readFromFile(String path) throws IOException, FileNotFoundException, NullPointerException{
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
		return (String[]) lines.toArray(new String[lines.size()]);
	}
	
	public static void main(String[] args){
		try {
			_port = initParser(args);
			Thread list = new Thread(new ListServer());
			Thread upload = new Thread(new UploadServer());
			list.start();
			upload.start();
			list.join();
			upload.join();
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
