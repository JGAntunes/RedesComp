/**
 * 
 */
package socketwrappers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author Joao Antunes
 *
 */
public class MessageTCP {
	
	private String[] _strParams;
	private byte[] _data;
	
	MessageTCP(byte[] data, int args) throws UnsupportedEncodingException{
		int i = 0, offset = 0, total = 0;
		int argsRead = 0;
		byte[] argsBuffer = null;
		byte[] dataBuffer = null;
		
		System.out.println(args);
		System.out.println(data.length);
		System.out.println(new String(data, "UTF-8"));
		
		ArrayList<byte[]> processor = new ArrayList<byte[]>();
		for(byte b : data){
			if((char) b == ' ' || (char) b == '\n'){
				argsBuffer = new byte[i];
				System.arraycopy(data, offset, argsBuffer, 0, i);
				offset = total+1;
				processor.add(argsBuffer);
				i = 0;
				argsRead++;
				if(argsRead == args && (data.length - offset - 1) > 0){
					//doesn't get \n
					System.out.println(data.length - offset - 1);
					dataBuffer = new byte[data.length - offset - 1];
					System.arraycopy(data, offset, dataBuffer, 0, data.length - offset - 1);
					break;
				}
			}
			else{
				i++;
			}
			total++;
		}
		i = 0;
		_strParams = new String[processor.size()];
		for(byte[] bs : processor){
			_strParams[i] = new String(bs, "UTF-8");
			i++;
		}
		_data =dataBuffer;
	}
	
	MessageTCP(String[] args, byte[] data) throws UnsupportedEncodingException{
		_strParams = args;
		_data = data;
	}

	public byte[] getData() {
		return _data;
	}

	public void setData(byte[] data) {
		_data = data;
	}

	public String[] getStrParams() {
		return _strParams;
	}

	public void setStrParams(String[] strParams) {
		_strParams = strParams;
	}
}
