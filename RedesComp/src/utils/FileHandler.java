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
	public static void createFile(String filePath, int size , byte[] data) throws IOException{
		System.out.println(size);
		System.out.println(data.length);
		//size-1 due to the \n
		if(size-1 != data.length){
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
	
/*	public static String upFile(String filePath) throws IOException{
		File f = new File(filePath);
		if(f.exists()){
			BufferedReader br = new BufferedReader(new FileReader(f));
		}
		try{
			Files.createFile(Paths.get(filePath));
		}
		catch(NoSuchFileException e){
			Files.createDirectories(Paths.get(filePath).getParent());
			Files.createFile(Paths.get(filePath));
		}
		FileOutputStream f = new FileOutputStream(filePath);
		f.write(b);
		f.close();
	}*/
}
