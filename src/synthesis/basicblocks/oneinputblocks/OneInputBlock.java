package synthesis.basicblocks.oneinputblocks;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * This abstract class handles the plugin and plugout methods for AudioBlock
 * with only one input.
 * @author allegrem
 */
public abstract class OneInputBlock implements AudioBlock {

	protected AudioBlock in = null;
	

	/**
	 * Plugs the given AudioBlock. This kind of block has only one input. You
	 * cannot plug more than one block in.
	 * @param a The AudioBlock to plug in.
	 * @throws TooManyInputsException if there is already an AudioBlock plugged
	 * in
	 */
	public void plugin(AudioBlock a) throws TooManyInputsException {
		if (in == null)
			in = a;
		else
			throw new TooManyInputsException();
	}

	
	/**
	 * Plugs out the given AudioBlock only if it is plugged in. If the plugged
	 * AudioBlock is not the given one, this method does nothing.
	 * @see OneInputBlock#plugout()
	 */
	public void plugout(AudioBlock a) {
		if (in == a)
			plugout();
	}

	
	/**
	 * Plugs out the plugged AudioBlock
	 * If no AudioBlock is plugged in, a warning message is displayed.
	 */
	public void plugout() {
		if (in == null)
			System.out.println("Warning: tried to plug out an AudioBlock but " +
					"no block was plugged in!");
		in = null;
	}

	
	/**
	 * This method defines the default behavior for a OneInputBlock. It throws 
	 * an exception if no input is plugged in. The output signal computing is 
	 * implemented in {@link OneInputBlock#compute(Float)}. 
	 * @throws RequireAudioBlocksException 
	 * @see synthesis.AudioBlock#play(Float)
	 */
	@Override
	public final Float play(Float t) {
		if (in == null)
			return 0f;
		return compute(t);
	}
	
	
	/**
	 * Computes the output signal.
	 * @param t the time
	 * @return the output signal
	 * @throws RequireAudioBlocksException if the input block is not able to 
	 * compute a sound.
	 */
	protected abstract Float compute(Float t); 
	
	
	/**
	 * This method defines the default behavior for a OneInputBlock. It throws 
	 * an exception if no input is plugged in. The output phi signal computing 
	 * is implemented in {@link OneInputBlock#computePhi(Float)}. 
	 * @see synthesis.AudioBlock#phi(Float)
	 */
	@Override
	public final Float phi(Float t) {
		if (in == null)
			return 0f;
		return computePhi(t);
	}
	
	
	/**
	 * Computes the output phi signal.
	 * @param t the time
	 * @return the output phi signal
	 * @throws RequireAudioBlocksException if the input block is not able to 
	 * compute the phi function. 
	 */
	protected abstract Float computePhi(Float t); 
	
	
}
