/**
 * 
 */
package commands;

/**
 * @author Joao Antunes
 *
 */
public abstract class Command {

	public String _code;
	public String[] _arguments;
	
	public abstract void run();
}
