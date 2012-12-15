package synthesis.basicBlocks;

import synthesis.AudioBlock;
import synthesis.exceptions.TooManyInputsException;

/**
 * This abstract class handles the plugin and plugout methods for AudioBlock
 * with only one input.
 * @author allegrem
 */
public abstract class OneInputBlock implements AudioBlock {

	protected AudioBlock in;
	

	/**
	 * Plugs the given AudioBlock.
	 * @param i This parameter is not used (gives 0 for example).
	 * @throws TooManyInputsException if there is already an AudioBlock plugged
	 * in
	 */
	@Override
	public void plugin(AudioBlock a, int i) throws TooManyInputsException {
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
	@Override
	public void plugout(AudioBlock a) {
		if (in == a)
			plugout();
	}

	/**
	 * Plugs out the plugged AudioBlock.
	 * @deprecated The method without parameter should be used
	 * @param i This parameter is not used.
	 * @see OneInputBlock#plugout()
	 */
	@Override
	public void plugout(int i) {
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
	 * This method is an alias for the plugout() method.
	 * @see OneInputBlock#plugout()
	 */
	@Override
	public void plugoutAll() {
		plugout();
	}
	
}
