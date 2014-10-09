/**
 * 
 */
package utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import socketwrappers.MessageTCP;

/**
 * @author Joao Antunes
 *
 */
public class StreamProcessors {
	
	/**
	 * In this function we get an input stream to a socket, from where we need to read information. Then while there is something to read from the strem we will
	 * read the bytes and tranfer them trough the arrays till all information is retrieved from the stream and we return the result buffer.
	 */
	
	public static MessageTCP getTCPInput(BufferedInputStream input, int argNum, boolean size) throws IOException{
		Scanner sc = new Scanner(input);
		byte[] resultBuff = null;
		String[] args = new String[argNum];
		
		args[0] = sc.next(Pattern.compile("^([A-Z]{3})")); // tratar exepções

		System.out.println(args[0]);
		if(argNum == 2){
			args[1] = sc.nextLine(); // tratar exepções
		}
		
		if(argNum == 3){
			args[1] = sc.next(); // tratar exepções
			System.out.println(args[1]);
		}
		
		if(size){
			int fileSize = 0;	
			System.out.println("Checking size");
			
			args[args.length-1] = sc.next(Pattern.compile("([0-9]+)"));
			fileSize = Integer.parseInt(args[args.length-1]);
			
			resultBuff = readFile(input, fileSize);
			
			if((char) resultBuff[resultBuff.length - 1] != '\n'){
				//message not ending with \n
			}
		}
		System.out.println("I've exited the while!");
		sc.close();
		return new MessageTCP(args, resultBuff);
	}
	
	public static byte[] readFile(BufferedInputStream input, int fileSize) throws IOException{
		
		System.out.println("FILE SIZE:" + fileSize);
		int byteNum = -1;
		int byteTotal = 0;
		byte[] buff = new byte[128];
		byte[] resultBuff = new byte[0];
		while ((byteNum = input.read(buff, 0, buff.length)) > -1) {
			System.out.println("I'm reading in the while!");
			byte[] tempBuff = new byte[resultBuff.length + byteNum];
			System.arraycopy(resultBuff, 0, tempBuff, 0, resultBuff.length);
			System.arraycopy(buff, 0, tempBuff, resultBuff.length, byteNum);
			resultBuff = tempBuff;
			System.out.println(byteTotal);
			
			byteTotal += byteNum;
			if(byteTotal >= fileSize){
				break;
			}
			
		}
		return resultBuff;
	}
	
	/**
	 * This function concatenates two arrays of bytes. An array called resultBuff is created and all the data from the first array is copied and then then we do the same
	 * for the second array, returning the array in the end with the data from the two arrays concatenated.
	 */
	public static byte[] concatByte(byte[] init, byte[] end) throws IOException{
		byte[] resultBuff = new byte[init.length + end.length];
		System.arraycopy(init, 0, resultBuff, 0, init.length);
		System.arraycopy(end, 0, resultBuff, init.length, end.length);
		System.out.println(new String(resultBuff, "UTF-8"));
		return resultBuff;
	}
}
