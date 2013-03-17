package synthesis.basicblocks.oneinputblocks;

/**
 * This class handles an offset block which adds a constant to the input signal.
 * @see Offset#play(Float)
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
	 */
	@Override
	public Float compute(Float t) {
		return in.play(t) + offset;
	}

	
	/**
	 * @return out(t) = 2 * Pi * offset * t + in.phi(t)
	 */
	@Override
	public Float computePhi(Float t) {
		return (float) (2 * Math.PI * offset * t + in.phi(t));
	}

}
