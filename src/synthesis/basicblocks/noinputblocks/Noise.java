/**
 * 
 */
package synthesis.basicblocks.noinputblocks;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * @author allegrem
 *
 */
public class Noise implements AudioBlock {

	/* (non-Javadoc)
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return (float) ((Math.random() - 0.5) * 250);
	}

	/* (non-Javadoc)
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return (float) ((Math.random() - 0.5) * 250);
	}

}
