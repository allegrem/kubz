package synthesis.basicblocks.noinputblocks;

import synthesis.AudioBlock;

/**
 * This class generates a white noise, aka a flat-spectrum signal.
 * @author allegrem
 */
public class WhiteNoise implements AudioBlock {

	/** Returns a random number between -125 and +125.
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) {
		return (float) ((Math.random() - 0.5) * 250);
	}

	
	/** Returns a random number between -125 and +125.
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) {
		return (float) ((Math.random() - 0.5) * 250);
	}

}
