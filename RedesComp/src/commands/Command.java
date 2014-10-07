/**
 * 
 */
package commands;

/**
 * @author Joao Antunes
 *
 */
public abstract class Command implements Runnable {

	public String _code;
	public String[] _arguments;
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public abstract void run();
}
