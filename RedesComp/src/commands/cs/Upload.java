/**
 * 
 */
package commands.cs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import servers.CS;
import socketwrappers.ClientTCP;
import socketwrappers.MessageTCP;
import socketwrappers.ServerTCP;
import utils.Errors;
import utils.Protocol;
import utils.StreamProcessors;
import commands.Command;

/**
 * @author Joao Antunes
 *
 */
public class Upload extends Command{

	private ServerTCP _server;
	private ClientTCP _client;
	
	public Upload (ServerTCP server, ClientTCP client) throws NullPointerException, IOException{
		_server = server;
		_client = client;
	}
	
	/* (non-Javadoc)
	 * @see commands.Command#run()
	 */
	@Override
	public void run() {
		try {
			String args[] = _server.receive().split(" ");
			if(args.length == 2){
				if(args[0].equals(Protocol.CHECK_FILE)){
					String filename = args[0];
					String[] files = CS.readFromFile("SS_List");
					for(String f : files){
						File file =  new File(f);
						if(file.exists()){
							_server.send(Protocol.CHECK_FILE_RESPONSE + " " + Protocol.IN_USE);
							System.exit(0);
						}
					}
					_server.send(Protocol.CHECK_FILE_RESPONSE + " " + Protocol.AVAILABLE);
					MessageTCP response = _server.receive(2, true);
					args = response.getStrParams();
					if((args.length == 2) && (args[0].equals(Protocol.CHECK_FILE))){
						if(Integer.parseInt(args[1]) == response.getData().length){
							byte[] sendMessage = new String(Protocol.UP_CS_FILE + " " + filename + " " + response.getData().length + " ").getBytes();
							byte[] result = StreamProcessors.concatByte(sendMessage, sendMessage.length, response.getData(), response.getData().length);
							String[] servers = CS.readFromFile("CS_List");
							String responseCommand = Protocol.UP_USER_RESPONSE;
							for(String s : servers){
								String[] info = s.split(" ");
								_client = new ClientTCP(info[0], Integer.parseInt(info[1]));
								_client.sendToServer(StreamProcessors.concatByte(result, result.length, ("\n").getBytes(), 1));
								String[] resp = _client.receiveFromServer().split(" ");
								if(resp.length == 2 && resp[0].equals(Protocol.UP_CS_RESPONSE)){
									if(!resp[1].equals(Protocol.OK)){
										if(resp[1].equals(Protocol.NOT_OK)){
											_server.send(responseCommand + " " + Protocol.NOT_OK);
										}
										else{
											_server.send(Protocol.ERROR);
										}
										System.out.println(Errors.IO_PROBLEM);
										System.exit(-1);
									}
								}
							}
							try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("SS_List", true)))) {
							    out.println(filename);
							}catch (IOException e) {
							    System.out.println(Errors.IO_PROBLEM);
							    System.exit(-1);
							}
							_server.send(responseCommand + " " + Protocol.OK);
						}
						else{
							_server.send(Protocol.ERROR);
						}
					}
					else{
						_server.send(Protocol.ERROR);
					}
				}
				else{
					_server.send(Protocol.ERROR);
				}
			}
			else{
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
