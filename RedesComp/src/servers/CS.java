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

import socketwrappers.*;
import utils.Protocol;

/**
 * @author Joao Antunes
 *
 */
public class CS {
	
	// The list of files that we can find on the Storage Servers.
	 
	private static ListServer _listServer;
	private static UploadServer _uploadServer;
	
	// The name of the machine that allocates the central server.
	
	private static String _name;
	
	// The machine's ip.
	
	private static String _ip;
	
	// The port of the CS.
	
	private static int _port;
	public static final int DEFAULT_PORT = 58003;
	private static final String RESOURCES_PATH = "./resources/FILES_LIST";
	private static final String SERVERS_PATH = "./resources/SERVERS_LIST";
	
	private static class ListServer implements Runnable{
		
		private static ServerUDP _server;
			
		
		/**
		 * We get the UDP server running by creating one, we pass as an argument the port of the machine were UDP server
		 * will run.
		 */
		@Override
		public void run() {
			try {
				_server = new ServerUDP(_port);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private static class UploadServer implements Runnable{
		
		private static ServerTCP _server;
		
		/**
		 * We get the TCP server running by creating one, we pass as an argument the port of the machine were TCP server
		 * will run. After creating one we just have to await a connection.
		 */
		@Override
		public void run() {
			try {
				_server = new ServerTCP(_port);
				_server.waitConnection();
				//while(cenas);
				//do coisas;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * This is a parser we use to check if the command that is beeing used to call out the CS is valid.
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
	 * The parser that we use to get the command we need to call.
	 */
	private static void protocolParser(String string, String ip, int port) throws IOException, FileNotFoundException{
		String[] tokens = string.split(" ");
		String output;
		if(tokens.length == 0){
			throw new IOException("[CS]Invalid protocol usage, empty command received");
		}
		else if(tokens[0].equals(Protocol.LIST)){
			/*try{
				String[] files = readFromFile(RESOURCES_PATH);
				output = new String("AWL " + _ip + " " + );
			} catch (NullPointerException e) {
				if(e.getMessage().startsWith("[EmptyFile]")){
					output = 
				}
			}
			for(String file : files){
				
			}*/
		}
		else if(tokens[0].equals(Protocol.CHECK_FILE)){
			//check if file exists
		}
		else if(tokens[0].equals(Protocol.UP_CS_FILE)){
			//receive file from user
		}
		else if(tokens[0].equals(Protocol.UP_CS_RESPONSE)){
			//receive response from ss upload
		}
		else{
			throw new IOException("[CS]Invalid protocol usage: " + string);
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
			_listServer.run();
			_uploadServer.run();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
