package synthesis.basicblocks.noinputblocks;

import synthesis.AudioBlock;

/**
 * This class handles a sine wave oscillator which works at a fixed frequency
 * and amplitude.
 * @author allegrem
 */
public class FixedSineWaveOscillator implements AudioBlock {

	private final Float frequency;
	private final Float amplitude;
	

	/**
	 * Creates a new FixedSineWaveOscillator.
	 * @param frequency The frequency of the cosinus in Hertz
	 * @param amplitude The amplitude of the cosinus.
	 */
	public FixedSineWaveOscillator(Float frequency, Float amplitude) {
		super();
		this.frequency = frequency;
		this.amplitude = amplitude;
	}


	/**
	 * Returns a cosinus function with the given amplitude.
	 * @return s(t) = a * cos(2 * Pi * f * t) where a is the amplitude and f is
	 * the frequency.
	 */
	@Override
	public Float play(Float t) {
		return (float) (amplitude * Math.cos(2*Math.PI*frequency*t));
	}

	
	/**
	 * Returns the phi function of a cosinus.
	 * @return s(t) = a / f * sin(2 * Pi * f * t) where a is the amplitude and 
	 * f is the frequency.
	 */
	@Override
	public Float phi(Float t) {
		return (float) (amplitude / frequency * Math.sin(2*Math.PI*frequency*t));
	}

}
