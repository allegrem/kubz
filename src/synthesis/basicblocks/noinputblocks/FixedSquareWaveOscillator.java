package synthesis.basicblocks.noinputblocks;

import synthesis.AudioBlock;

/**
 * This class handles a square wave oscillator which works at a fixed frequency
 * and amplitude.
 * @author allegrem
 */
public class FixedSquareWaveOscillator implements AudioBlock {
	
	private final Float frequency;
	private final Float amplitude;
	
	
	/**
	 * Creates a new FixedSquareWaveOscillator.
	 * @param frequency The frequency of the sinus in Hertz
	 * @param amplitude The amplitude of the sinus.
	 */
	public FixedSquareWaveOscillator(Float frequency, Float amplitude) {
		super();
		this.frequency = frequency;
		this.amplitude = amplitude;
	}


	/**
	 * Returns a square function with the given amplitude.
	 * @return s(t) = a  if 0 < t < 1/2f <br />
	 * 	s(t) = -a  if 1/2f < t < 1/f
	 *  where a is the amplitude and f the frequency
	 */
	@Override
	public Float play(Float t) {
		if (Math.sin(2*Math.PI*frequency*t) >= 0)
			return amplitude;
		else
			return -1 * amplitude;
	}


	@Override
	public Float phi(Float t) {
		// TODO Auto-generated method stub
		System.out.println("ERROR! NOT YET IMPLEMENTED!!");
		return null;
	}
	
	
}
