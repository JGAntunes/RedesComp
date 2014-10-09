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
		
		args[0] = sc.next(); // tratar exepções
		
		//args[0] = sc.next();

		System.out.println(args[0]);
		if(argNum == 2){
			args[1] = sc.next(); // tratar exepções
			
			System.out.println(args[1]);
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
			
			/*if((char) resultBuff[resultBuff.length - 1] == '\n'){
				System.out.println("Isto é um \n que foi lido.");
				//message not ending with \n
			}*/
		}
		System.out.println("I've exited the while!");
		return new MessageTCP(args, resultBuff);
	}
	
	public static byte[] readFile(BufferedInputStream input, int fileSize) throws IOException{
		
		System.out.println("FILE SIZE:" + fileSize);
		int byteNum = -1;
		int initBuff = 1024;
		int byteTotal = 0;
		byte[] buff;
		//byte[] resultBuff = new byte[0];
		/*if(fileSize+1 < initBuff){
			initBuff = fileSize;
		}*/
		buff = new byte[initBuff];
		
		byte[] resultBuff = new byte[0];
		
		System.out.println(resultBuff.length);
		while ((fileSize > byteTotal) && ((byteNum = input.read(buff, 0, buff.length)) > -1)) {
			System.out.println("I'm reading in the while!");
			resultBuff = concatByte(resultBuff, resultBuff.length, buff, byteNum);
			byteTotal += byteNum;
			System.out.println("Total: " + byteTotal);
			System.out.println("File: " + fileSize);
			System.out.println("Buff: " + byteNum);
			
		}
		return resultBuff;
	}
	
	/**
	 * This function concatenates two arrays of bytes. An array called resultBuff is created and all the data from the first array is copied and then then we do the same
	 * for the second array, returning the array in the end with the data from the two arrays concatenated.
	 */
	public static byte[] concatByte(byte[] init, int initLen, byte[] end, int endLen) throws IOException{
		byte[] resultBuff = new byte[initLen + endLen];
		System.arraycopy(init, 0, resultBuff, 0, initLen);
		System.arraycopy(end, 0, resultBuff, initLen, endLen);
		//System.out.println("Just concated: " + new String(resultBuff, "UTF-8"));
		return resultBuff;
	}
}
