package synthesis.basicBlocks;

import synthesis.exceptions.RequireAudioBlocksException;

/**
 * This class handles a gain block which multiplies the input signal by a 
 * constant.
 * @see Gain#play(int)
 * @author allegrem
 */
public class Gain extends OneInputBlock {

	private final Float gain;
	

	/**
	 * Creates a Gain block.
	 * @param gain the gain of the input signal.
	 */
	public Gain(Float gain) {
		super();
		this.gain = gain;
	}

	/**
	 * The sound produced is given by : out(t) = gain * in(t).
	 * @see OneInputBlock#play(int)
	 */
	@Override
	public Float play(int t) throws RequireAudioBlocksException {
		super.play(t);
		return in.play(t) * gain;
	}

}
