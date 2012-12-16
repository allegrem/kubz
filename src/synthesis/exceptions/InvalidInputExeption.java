package synthesis.exceptions;

/**
 * This exception is raised when a plugin method is called with an invalid
 * input number.
 * @author allegrem
 */
public class InvalidInputExeption extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidInputExeption() {
		System.out.println("Error! The given input number is invalid.");
	}
	
}
