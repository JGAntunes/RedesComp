/**
 * 
 */
package utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import socketwrappers.MessageTCP;

/**
 * @author Grupo 3
 *
 */
public class StreamProcessors {
	
	/**
	 * In this function we get an input stream to a socket, from where we need to read information. Then while there is something to read from the strem we will
	 * read the bytes and tranfer them trough the arrays till all information is retrieved from the stream and we return the result buffer.
	 */
	
	public static MessageTCP getTCPInput(BufferedInputStream input, int argNum, boolean size) throws IOException{
		byte[] inputBuff = readFile(input);
		byte[] resultBuff = null;
		System.out.println(new String(inputBuff, "UTF-8"));
		Scanner sc = new Scanner(new ByteArrayInputStream(inputBuff));
		String[] args = new String[argNum];
		
		try{		
			args[0] = sc.next();
	
			if(argNum == 2){
				args[1] = sc.next();
			}
			
			if(argNum == 3){
				args[1] = sc.next();
			}
			
			if(size){
				int fileSize = 0;
				int offset = 0;
				
				if(argNum == 1){
					args[0] = sc.next();
				}
				else{
					args[args.length-1] = sc.next(Pattern.compile("([0-9]+)"));
					for(String s: args){
						offset += s.length() + 1;
					}
				}
				
				resultBuff = new byte[inputBuff.length - offset];
				System.arraycopy(inputBuff, offset, resultBuff, 0, resultBuff.length);
				
				if((char) resultBuff[resultBuff.length - 1] != '\n'){
					System.err.println(Errors.INVALID_PROTOCOL);
					System.exit(-1);
				}
				
				fileSize = Integer.parseInt(args[args.length-1]);
	
			}
		} catch (InputMismatchException e){
			System.err.println(Errors.INVALID_PROTOCOL);
			System.exit(-1);
		} catch (NoSuchElementException e){
			System.err.println(Errors.INVALID_PROTOCOL);
			System.exit(-1);
		}
		return new MessageTCP(args, resultBuff);
	}
	
	public static byte[] readFile(BufferedInputStream input) throws IOException{
		
		
		int byteNum = -1;
		byte[] buff = new byte[128];
		
		byte[] resultBuff = new byte[0];
		
		while ((byteNum = input.read(buff, 0, buff.length)) != -1) {
			byte[] tbuff = new byte[resultBuff.length + byteNum];
			System.arraycopy(resultBuff, 0, tbuff, 0, resultBuff.length);
			System.arraycopy(buff, 0, tbuff, resultBuff.length, byteNum);
			resultBuff = tbuff;
			if(byteNum != buff.length){
				break;
			}
		}
		System.out.println("Total: " + resultBuff.length);
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
		return resultBuff;
	}
}
