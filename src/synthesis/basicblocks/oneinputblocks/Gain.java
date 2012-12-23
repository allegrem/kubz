package synthesis.basicblocks.oneinputblocks;

import synthesis.exceptions.RequireAudioBlocksException;

/**
 * This class handles a gain block which multiplies the input signal by a 
 * constant.
 * @see Gain#play(Float)
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
	 * @throws RequireAudioBlocksException if the input block is not able to 
	 * compute a sound.
	 */
	@Override
	public Float compute(Float t) throws RequireAudioBlocksException {
		return in.play(t) * gain;
	}

	
	/**
	 * @return out(t) = gain * in.phi(t)
	 * @see OneInputBlock#phi(Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		super.phi(t);
		return in.phi(t) * gain;
	}

}
