/**
 * 
 */
package commands;

/**
 * @author Grupo 3
 *
 */
public abstract class Command implements Runnable {

	public String _code;
	public String[] _arguments;
	
	public abstract void run();
}
