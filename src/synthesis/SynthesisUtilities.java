/**
 * 
 */
package synthesis;

import synthesis.exceptions.RequireAudioBlocksException;

/**This class aims to simplify the code elsewhere since it contains the recurrent method 
 * computeSound.
 * @author allegrem
 *
 */
public class SynthesisUtilities {

	@Deprecated
	public static byte[] computeSound(Float start, Float length, AudioBlock a) 
			throws RequireAudioBlocksException {
		byte[] arr = new byte[(int) (length*AudioBlock.SAMPLE_RATE)];
		
		for(int i = 0; i<length*AudioBlock.SAMPLE_RATE ; i++) {
			float f = a.play((start + i)/AudioBlock.SAMPLE_RATE);
			arr[i] = (byte) f;
		}
		
		return arr;
	}
	
}
