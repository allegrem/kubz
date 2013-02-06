package synthesis.filters;

import java.security.InvalidParameterException;
import java.util.Observable;

/**
 * This class handles a several bands filter. The number of bands is set in the
 * constructor. The whole spectrum (0 to 11kHz) is linearly sliced in bands. The
 * spectrum of the filter can be discontinuous. When the filter is modified, its
 * observers are notified. This class also gives a
 * {@link BandsFilter#resetBars(int)} method to reset the filter, and a
 * {@link BandsFilter#random(int)} method to compute a random filter.
 * 
 * @author allegrem
 */
public class BandsFilter extends Observable {

	private int[] bars;

	/**
	 * Create a new BandsFilter and initialize it as an all-pass.
	 * 
	 * @param barNumber
	 *            the number of bands for the filter.
	 * @throws InvalidParameterException
	 *             if the barNumber parameter is negative.
	 */
	public BandsFilter(int barNumber) {
		if (barNumber < 0)
			throw new InvalidParameterException("negative number of bars");
		bars = new int[barNumber];
		for (int i = 0; i < barNumber; i++)
			bars[i] = 100;
	}

	/**
	 * Change the value of the i-th bar. Then observers are notified.
	 * 
	 * @param i
	 *            the number of the bar to change.
	 * @param value
	 *            the new value of the bar (0 is -infinity dB ; 100 is 0dB).
	 * @throws InvalidParameterException
	 *             if the i parameter is out of range.
	 */
	public void setBar(int i, int value) {
		if (i >= 0 && i < bars.length) {
			bars[i] = value;
			setChanged();
			notifyObservers();
		} else
			throw new InvalidParameterException("number of band out of range");
	}

	// public byte[] filter(byte[] soundBytes) {
	// if (bypass)
	// return soundBytes;
	//
	// // looking for the smallest power of two above sound length
	// int power2Length = 1;
	// while (power2Length < soundBytes.length)
	// power2Length *= 2;
	//
	// // converting byte array to double array
	// double[] sound = new double[power2Length];
	// for (int i = 0; i < soundBytes.length; i++)
	// sound[i] = soundBytes[i];
	// for (int i = soundBytes.length; i < power2Length; i++)
	// sound[i] = 0; // add zeros at the end
	//
	// double counterBefore = 0, counterAfter = 0;
	//
	// // compute fourier transform
	// FastFourierTransformer fourierTransform = new FastFourierTransformer(
	// DftNormalization.STANDARD);
	// Complex[] spectrum = fourierTransform.transform(sound,
	// TransformType.FORWARD);
	// for (int x = 0; x < spectrum.length / 4; x++) {
	// counterBefore += spectrum[x].abs()
	// + spectrum[x + spectrum.length / 4].abs()
	// + spectrum[spectrum.length - x - 1 - spectrum.length / 4]
	// .abs() + spectrum[spectrum.length - x - 1].abs();
	// double multiplier = bars[x * 40 / spectrum.length] / 100.;
	// spectrum[x] = spectrum[x].multiply(multiplier);
	// spectrum[x + spectrum.length / 4] = spectrum[x + spectrum.length
	// / 4].multiply(multiplier);
	// spectrum[spectrum.length - x - 1 - spectrum.length / 4] =
	// spectrum[spectrum.length
	// - x - 1 - spectrum.length / 4].multiply(multiplier);
	// spectrum[spectrum.length - x - 1] = spectrum[spectrum.length - x
	// - 1].multiply(multiplier);
	// counterAfter += spectrum[x].abs()
	// + spectrum[x + spectrum.length / 4].abs()
	// + spectrum[spectrum.length - x - 1 - spectrum.length / 4]
	// .abs() + spectrum[spectrum.length - x - 1].abs();
	// }
	//
	// System.out.println("ratio:"
	// + (int) (counterAfter / counterBefore * 100));
	//
	// // inverse transform
	// Complex[] complexResult = fourierTransform.transform(spectrum,
	// TransformType.INVERSE);
	// byte[] realResult = new byte[soundBytes.length];
	//
	// // keep real part
	// for (int i = 0; i < soundBytes.length; i++)
	// realResult[i] = (byte) complexResult[i].getReal();
	//
	// return realResult;
	// }

	/**
	 * Return the value of the i-th bar (0 is -infinity dB ; 100 is 0dB).
	 * 
	 * @param i
	 *            the number of the bar to get.
	 * @throws InvalidParameterException
	 *             if the i parameter is out of range.
	 */
	public int getBar(int i) {
		if (i >= 0 && i < bars.length)
			return bars[i];
		else
			throw new InvalidParameterException("Bar index out of range");
	}

	/**
	 * Change the value of all the bars. Then observers are notified.
	 * 
	 * @param value
	 *            the new value of the bars (0 is -infinity dB ; 100 is 0dB).
	 */
	public void resetBars(int value) {
		for (int i = 0; i < bars.length; i++)
			bars[i] = value;
		setChanged();
		notifyObservers();
	}

	/**
	 * Replace the value of all the bars with a value between 0 (-inifinity dB)
	 * and 100 (0dB). Then observers are notified.
	 */
	public void random() {
		for (int i = 0; i < bars.length; i++)
			bars[i] = (int) (Math.random() * 100);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Return the number of bars of the filter.
	 */
	public int getBarNumber() {
		return bars.length;
	}
	
}
