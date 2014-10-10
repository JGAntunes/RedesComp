/**
 * 
 */
package commands;

/**
 * @author Grupo 3
 *
 */
public abstract class Command {

	public String _code;
	public String[] _arguments;
	
	public abstract void run();
}
