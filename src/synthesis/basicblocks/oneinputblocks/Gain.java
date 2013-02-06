package synthesis.basicblocks.oneinputblocks;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

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
	 * Create a Gain block and plug in the given block.
	 * @param gain the gain of the input signal.
	 * @param block the block to plug in
	 */
	public Gain(Float gain, AudioBlock block) {
		super();
		this.gain = gain;
		try {
			plugin(block);
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * The sound produced is given by : out(t) = gain * in(t).
	 */
	@Override
	public Float compute(Float t) throws RequireAudioBlocksException {
		return in.play(t) * gain;
	}

	
	/**
	 * @return out(t) = gain * in.phi(t)
	 */
	@Override
	public Float computePhi(Float t) throws RequireAudioBlocksException {
		return in.phi(t) * gain;
	}

}
