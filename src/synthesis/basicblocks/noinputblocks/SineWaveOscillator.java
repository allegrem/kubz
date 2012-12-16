package synthesis.basicblocks.noinputblocks;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * This class handles a sine wave oscillator which works at a fixed frequency
 * and amplitude.
 * @author allegrem
 */
public class SineWaveOscillator implements AudioBlock {

	private final Float frequency;
	private final Float amplitude;
	

	/**
	 * Creates a new SineWaveOscillator.
	 * TODO should we use Integer everywhere instead of Float ?? and what about
	 * the max amplitude of the signal ?
	 * @param frequency The frequency of the sinus in Hertz
	 * @param amplitude The amplitude of the sinus.
	 */
	public SineWaveOscillator(Float frequency, Float amplitude) {
		super();
		this.frequency = frequency;
		this.amplitude = amplitude;
	}


	/**
	 * 
	 * @see synthesis.AudioBlock#play(int)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return new Float(amplitude * Math.sin(2*Math.PI*frequency*t));
	}

}
