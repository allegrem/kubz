package synthesis.basicblocks.orderedinputsblocks;

import synthesis.AudioBlock;
import synthesis.exceptions.InvalidInputException;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * This class handles a sine wave oscillator which works at variable frequency
 * and amplitude.
 * @author allegrem
 */
public class SineWaveOscillator implements AudioBlock {

	/**
	 * The frequency input of the SineWaveOscillator AudioBlock. 
	 */
	public static final int FREQUENCY_IN = 0;
	
	/**
	 * The amplitude input of the SineWaveOscillator AudioBlock. 
	 */
	public static final int AMPLITUDE_IN = 1;
	
	
	private AudioBlock frequency = null;
	private AudioBlock amplitude = null;

	
	/**
	 * Creates a new SineWaveOscillator with the given frequency and 
	 * amplitude blocks
	 * @param frequency The AudioBlock which will control the frequency of
	 * the oscillator
	 * @param amplitude The AudioBlock which will control the amplitude of
	 * the oscillator
	 */
	public SineWaveOscillator(AudioBlock frequency, AudioBlock amplitude) {
		super();
		this.frequency = frequency;
		this.amplitude = amplitude;
	}
	
	
	/**
	 * Creates a new SineWaveOscillator with no block plugged in
	 */
	public SineWaveOscillator() {
		super();
	}
	
	
	/**
	 * Plugs out all the blocks plugged in.
	 */
	public void plugoutAll() {
		try {
			plugout(AMPLITUDE_IN);   
			plugout(FREQUENCY_IN);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Plugs in the given AudioBlock in the given input.
	 * @param a The AudioBlock to plug in.
	 * @param i The input number where to plug the AudioBlock in. See 
	 * {@link SineWaveOscillator} for the list of constants associated to inputs.
	 * @throws TooManyInputsException if an AudioBlock was already plugged in
	 * the given input.
	 * @throws InvalidInputException if the given input number is not valid.
	 */
	public void plugin(AudioBlock a, int i) 
			throws TooManyInputsException, InvalidInputException {
		switch (i) {
		case AMPLITUDE_IN:
			if (amplitude != null)
				throw new TooManyInputsException();
			amplitude = a;
			break;

		case FREQUENCY_IN:
			if (frequency != null)
				throw new TooManyInputsException();
			frequency = a;
			break;
			
		default:
			throw new InvalidInputException();
		}
	}
	
	
	/**
	 * Plugs out the AudioBlock plugged in the given input.
	 * @param i The input number to plug out. See {@link SineWaveOscillator} 
	 * for the list of constants associated to inputs.
	 * @throws InvalidInputException if the given input number is not valid.
	 */
	public void plugout(int i) throws InvalidInputException {
		switch (i) {
		case AMPLITUDE_IN:
			if (amplitude == null)
				System.out.println("Warning! Tried to plug out an AudioBlock" +
						"whereas no block was plugged in.");
			amplitude = null;
			break;
		
		case FREQUENCY_IN:
			if (frequency == null)
				System.out.println("Warning! Tried to plug out an AudioBlock" +
						"whereas no block was plugged in.");
			frequency = null;
			break;
			
		default:
			throw new InvalidInputException();
		}
	}


	/**
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {		
		return (float) (amplitude.play(t) * 
				Math.cos(frequency.phi(t)));
	}


	@Override
	public Float phi(Float t) {
		
		return null;
	}

}
