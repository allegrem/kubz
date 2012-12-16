package synthesis.basicblocks.noinputblocks;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * This class handles an AudioBlock which return a constant value.
 * @author allegrem
 */
public class Constant implements AudioBlock {

	private final Float constant;
	
	
	/**
	 * Creates a Constant AudioBlock with the value of the constant.
	 * @param constant The value returned by the AudioBlock.
	 */
	public Constant(Float constant) {
		super();
		this.constant = constant;
	}


	/**
	 * Simply returns the value of the constant.
	 * @see synthesis.AudioBlock#play(int)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return constant;
	}

}
