package synthesis.basicBlocks;

import synthesis.AudioBlock;
import synthesis.exceptions.TooManyInputsException;

/**
 * This abstract class handles the plugin and plugout methods for AudioBlock
 * with only one input.
 * @author allegrem
 */
public abstract class OneInputBlock implements AudioBlock {

	private AudioBlock in;
	
	@Override
	public void plugin(AudioBlock a, int i) throws TooManyInputsException {
		if (in == null)
			in = a;
		else
			throw new TooManyInputsException();
	}

	@Override
	public void plugout(AudioBlock a) {
		if (in == a)
			in = null;
	}

	@Override
	public void plugout(int i) {
		if (in == null)
			System.out.println("Warning: tried to plug out an AudioBlock but " +
					"no block was plugged in!");
		in = null;
	}
	
	public void plugout() {
		plugout(0);
	}

	@Override
	public void plugoutAll() {
		if (in == null)
			System.out.println("Warning: tried to plug out an AudioBlock but " +
					"no block was plugged in!");
		in = null;
	}
	
}
