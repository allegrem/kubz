package synthesis.basicBlocks;

import synthesis.exceptions.RequireAudioBlocksException;

/**
 * This class handles an offset block which adds a constant to the input signal.
 * @see Offset#play(int)
 * @author allegrem
 */
public class Offset extends OneInputBlock {

	private final Float offset;
	
	
	/**
	 * Creates an Offset block.
	 * @param offset the offset of the signal.
	 */
	public Offset(Float offset) {
		super();
		this.offset = offset;
	}


	/**
	 * The sound produced is given by : out(t) = offset + in(t).
	 * @see OneInputBlock#play(int)
	 */
	@Override
	public Float play(int t) throws RequireAudioBlocksException {
		super.play(t);
		return in.play(t) + offset;
	}

}
