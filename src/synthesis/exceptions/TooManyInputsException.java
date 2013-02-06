package synthesis.exceptions;

/**
 * This exception is raised when an AudioBlock has reached its limit of inputs
 * and the plugin method is called on it.
 * @author allegrem
 */
public class TooManyInputsException extends Exception {

	private static final long serialVersionUID = 1L;

	public TooManyInputsException() {
		System.out.println("Error! Tried to plug in an AudioBlock whereas all" +
				" inputs were occupied! ");
	}
	
}
