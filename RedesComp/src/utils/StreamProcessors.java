/**
 * 
 */
package utils;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author Joao Antunes
 *
 */
public class StreamProcessors {
	
	/**
	 * In this function we get an input stream to a socket, from where we need to read information. Then while there is something to read from the strem we will
	 * read the bytes and tranfer them trough the arrays till all information is retrieved from the stream and we return the result buffer.
	 */
	
	public static byte[] getByteArray(BufferedInputStream input) throws IOException{
		byte[] resultBuff = new byte[0];
		byte[] buff = new byte[128];
		int byteNum = -1;

		while ((byteNum = input.read(buff, 0, buff.length)) > -1) {
			byte[] tempBuff = new byte[resultBuff.length + byteNum];
			System.arraycopy(resultBuff, 0, tempBuff, 0, resultBuff.length);
			System.arraycopy(buff, 0, tempBuff, resultBuff.length, byteNum);
			resultBuff = tempBuff;
		}
/*		if(endOfLine != -1){
			byte[] tempBuff = new byte[resultBuff.length + endOfLine];
			System.arraycopy(resultBuff, 0, tempBuff, 0, resultBuff.length);
			System.arraycopy(buff, 0, tempBuff, resultBuff.length, endOfLine);
			resultBuff = tempBuff;
		}*/
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
