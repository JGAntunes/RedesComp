/**
 * 
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * @author Joao Antunes
 *
 */
/**
 * @author Joao Antunes
 *
 */

public class FileHandler {


	/**
	 * Function that creates the file. It receives as arguments the path, the size and the data
	 * that has to be saved in the file.
	 * First we create an array of bytes were we save all the data that we need to save on
	 * the file, if the size of the data that we get doesnt correspond with the one that was
	 * specified on the size we abort.
	 * If this doesnt happen we proceed to the creation of the file in the specified path, if the 
	 * directory doesnt exist we have to creat it before creating the file.
	 * Now we create an output stream so we can write the data on the file, after all the data 
	 * is written we close the file.
	 */
	
	public static void createFile(String filePath, int size , byte[] data) throws IOException{
		if(size != data.length){
			throw new IllegalArgumentException();
		}
		try{
			Files.createFile(Paths.get(filePath));
		}
		catch(NoSuchFileException e){
			Files.createDirectories(Paths.get(filePath).getParent());
			Files.createFile(Paths.get(filePath));
		}
		FileOutputStream f = new FileOutputStream(filePath);
		f.write(data);
		f.close();
	}
	
	public static byte[] upFile(String filePath) throws IOException{
		File f = new File(filePath);
		if(f.exists()){
			return Files.readAllBytes(f.toPath());
		}
		else{
			return null;
			//file doesnt exist
		}
	}
}
