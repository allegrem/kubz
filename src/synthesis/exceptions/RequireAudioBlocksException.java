package synthesis.exceptions;

/**
 * This exception is raised when an AudioBlock is asked to play a sound, but it
 * has not enough AudioBlock plugged in to compute the sound.
 * @author allegrem
 */
public class RequireAudioBlocksException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequireAudioBlocksException() {
		System.out.println("Error! Tried to play a sound whereas not enough" +
				"AudioBlocks were plugged in.");
	}
	
}
