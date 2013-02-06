package synthesis.exceptions;

import synthesis.AudioBlock;

/**
 * This exception is raised when an AudioBlock is asked to play a sound, but it
 * has not enough AudioBlock plugged in to compute the sound.
 * @author allegrem
 */
public class RequireAudioBlocksException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequireAudioBlocksException(AudioBlock a) {
		System.out.println("Error! In AudioBlock"+a+"Tried to play a sound whereas not enough" +
				"AudioBlocks were plugged in.");
	}
	
}
