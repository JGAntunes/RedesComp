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
		ArrayList<byte[]> processor = new ArrayList<byte[]>();
		for(byte b : data){
			if((char) b == ' ' || (char) b == '\n'){
				argsBuffer = new byte[i];
				System.arraycopy(data, offset, argsBuffer, 0, i);
				processor.add(argsBuffer);
				offset = i + 1;
				i = 0;
				argsRead++;
				if(argsRead == args){
					dataBuffer = new byte[data.length - total];
					System.arraycopy(data, total, dataBuffer, 0, data.length - total);
					break;
				}
			}
			total++;
			i++;
		}
		i = 0;
		_strParams = new String[processor.size()];
		for(byte[] bs : processor){
			_strParams[i] = new String(bs, "UTF-8");
			i++;
		}
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
