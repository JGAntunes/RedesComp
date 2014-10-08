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
	
	public static byte[] getByteArray(BufferedInputStream input) throws IOException{
		byte[] resultBuff = new byte[0];
		byte[] buff = new byte[128];
		int byteNum = -1;
		int endOfLine = -1;

		while ((byteNum = input.read(buff, 0, buff.length)) > -1 && (endOfLine = buff.toString().indexOf('\n')) == -1) {
			byte[] tempBuff = new byte[resultBuff.length + byteNum];
			System.arraycopy(resultBuff, 0, tempBuff, 0, resultBuff.length);
			System.arraycopy(buff, 0, tempBuff, resultBuff.length, byteNum);
			resultBuff = tempBuff;
		}
		if(endOfLine != -1){
			byte[] tempBuff = new byte[resultBuff.length + endOfLine];
			System.arraycopy(resultBuff, 0, tempBuff, 0, resultBuff.length);
			System.arraycopy(buff, 0, tempBuff, resultBuff.length, endOfLine);
			resultBuff = tempBuff;
		}
		
		return resultBuff;
	}
	
	public static byte[] concatByte(byte[] init, byte[] end) throws IOException{
		byte[] resultBuff = new byte[init.length + end.length];
		System.arraycopy(init, 0, resultBuff, 0, init.length);
		System.arraycopy(end, 0, resultBuff, init.length, end.length);	
		return resultBuff;
	}
}
