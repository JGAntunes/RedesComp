/**
 * 
 */
package servers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

import commands.Command;
import commands.ss.Retrieve;
import commands.ss.Upload;
import socketwrappers.MessageTCP;
import socketwrappers.ServerTCP;
import utils.Errors;
import utils.FileHandler;
import utils.Protocol;
import utils.StreamProcessors;

/**
 * @author Grupo 3
 *
 */
public class SS {
	private static ServerTCP _server;
	private static int _port;
	private static final int DEFAULT_PORT = 59000;
	
	
	private static class Server implements Runnable{
		
		public Server(){}
		
		/**
		 * We get the TCP server running by creating one, we pass as an argument the port of the machine were TCP server will run. After creating one we 
		 * just have to await a connection.
		 */
		@Override
		public void run() {
			try {
				System.out.println("Running");
				byte[] inputBuff = StreamProcessors.readFile(_server.getInput());
				Scanner sc = new Scanner(new ByteArrayInputStream(inputBuff));
				String string = sc.next();
				Thread co;
				if(string.isEmpty()){
					System.err.println(Errors.INVALID_PROTOCOL);
					_server.send(Protocol.ERROR);
				}
				else if(string.equals(Protocol.DOWN_FILE)){
					String[] arg = {sc.next()};
					MessageTCP message  =  new MessageTCP(arg, null);
					co = new Thread( new Retrieve(_server, message));
					co.start();
				}
				else if(string.equals(Protocol.UP_CS_FILE)){
					String[] args = {sc.next(), sc.next()};
					int offset = 0;
					for(String s: args){
						offset += s.length() + 1;
					}
					byte[] resultBuff = new byte[inputBuff.length - offset];
					System.arraycopy(inputBuff, offset, resultBuff, 0, resultBuff.length);
					MessageTCP message  =  new MessageTCP(args, resultBuff);
					co = new Thread( new Upload(_server,message));
					co.start();
				}
				else{
					System.err.println(Errors.INVALID_PROTOCOL);
					_server.send(Protocol.ERROR);
					
				}		
			} catch (NullPointerException e) {
				System.err.println(Errors.NO_CLIENT_SOCKET);
				System.exit(-1);
			} catch (IOException e) {
				System.err.println(Errors.IO_PROBLEM);
				System.exit(-1);
			}
		}
	}
	
	/**
	 * Storage server constructor, receives as arguments the name of the machine and the port were the server will run.
	 */
	private SS(){}
	
	
	/**
	 * This is the parser we use to call out the SS if the command used is valid.
	 */
	private static int initParser(String[] string){
		if(string.length == 0){
			return DEFAULT_PORT;
		}
		else if(string.length == 2){
			if(string[0].equals("-p")){
				try{
					return Integer.parseInt(string[1]);
				} catch(NumberFormatException e){
					System.err.println(Errors.INVALID_PORT_NUMBER);
					System.exit(-1);
				}
			}
		}
		else{
			System.err.println(Errors.INVALID_INITIALIZERS);
			System.exit(-1);
		}
		return 0;
	}
	
	/**
	 * The parser that we use to call out a command, if it is a valid one.
	 */
	private static void protocolParser(String string){

	}
	
	public static void main(String[] args){
		_port = initParser(args);
		try {
			try {
				_server = new ServerTCP(_port);
				
			} catch (SocketException e) {
				System.err.println(Errors.SOCKET_PROBLEM);
				System.exit(-1);
				
			} catch (IOException e) {
				System.err.println(Errors.IO_PROBLEM);
				System.exit(-1);
			}
			while(true){
				try {
					_server.waitConnection();
				} catch (IOException e) {
					System.err.println(Errors.WAITING_PROBLEM);
					System.exit(-1);
				}
				try {
					Thread t = new Thread(new Server());
					t.start();
				} catch (NullPointerException e) {
					System.err.println(Errors.NO_CLIENT_SOCKET);
					System.exit(-1);
				}
			}
		} catch(Exception e){
			System.err.println(Errors.UNKNOWN_ERROR);
			System.exit(-1);
		}
	}
}
