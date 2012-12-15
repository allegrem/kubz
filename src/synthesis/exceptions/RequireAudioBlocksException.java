package synthesis.exceptions;

public class RequireAudioBlocksException extends Exception{

	/**
	 * This exception is raised when an AudioBlock doesn't have
	 * all of it's plugged-in AudioBlocks' output at t
	 * @author valeh
	 */
	private static final long serialVersionUID = 1L;
	
	public RequireAudioBlocksException(){
		System.out.println("Missing AudioBlocks");
	}

}
