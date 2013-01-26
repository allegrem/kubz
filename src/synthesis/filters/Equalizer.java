package synthesis.filters;

import java.util.ArrayList;
import java.util.Observable;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 * @author allegrem
 * 
 */
public class Equalizer extends Observable {

	private int[] bars;

	public Equalizer(int barNumber) {
		bars = new int[barNumber];
		for (int i = 0; i < barNumber; i++)
			bars[i] = 100;
	}

	public void setBar(int i, int value) {
		if (i >= 0 && i < bars.length) {
			bars[i] = value;
			setChanged();
			notifyObservers();
		}
	}

	public byte[] filter(byte[] soundBytes) {
		// looking for the smallest power of two above sound length
		int power2Length = 1;
		while (power2Length < soundBytes.length)
			power2Length *= 2;

		// converting byte array to double array
		double[] sound = new double[power2Length];
		for (int i = 0; i < soundBytes.length; i++)
			sound[i] = soundBytes[i];
		for (int i = soundBytes.length; i < power2Length; i++)
			sound[i] = 0; // add zeros at the end

		double counterBefore = 0, counterAfter = 0;

		// compute fourier transform
		FastFourierTransformer fourierTransform = new FastFourierTransformer(
				DftNormalization.STANDARD);
		Complex[] spectrum = fourierTransform.transform(sound,
				TransformType.FORWARD);
		for (int x = 0; x < spectrum.length / 4; x++) {
			counterBefore += spectrum[x].abs()
					+ spectrum[x + spectrum.length / 4].abs()
					+ spectrum[spectrum.length - x - 1 - spectrum.length / 4]
							.abs() + spectrum[spectrum.length - x - 1].abs();
			double multiplier = bars[x * 40 / spectrum.length] / 100.;
			spectrum[x] = spectrum[x].multiply(multiplier);
			spectrum[x + spectrum.length / 4] = spectrum[x + spectrum.length
					/ 4].multiply(multiplier);
			spectrum[spectrum.length - x - 1 - spectrum.length / 4] = spectrum[spectrum.length
					- x - 1 - spectrum.length / 4].multiply(multiplier);
			spectrum[spectrum.length - x - 1] = spectrum[spectrum.length - x
					- 1].multiply(multiplier);
			counterAfter += spectrum[x].abs()
					+ spectrum[x + spectrum.length / 4].abs()
					+ spectrum[spectrum.length - x - 1 - spectrum.length / 4]
							.abs() + spectrum[spectrum.length - x - 1].abs();
		}

		System.out.println("before:" + counterBefore + "; after:"
				+ counterAfter + "; ratio:"
				+ (int) (counterAfter / counterBefore * 100));

		// inverse transform
		Complex[] complexResult = fourierTransform.transform(spectrum,
				TransformType.INVERSE);
		byte[] realResult = new byte[complexResult.length];

		// keep real part
		for (int i = 0; i < complexResult.length; i++)
			realResult[i] = (byte) complexResult[i].getReal();

		return realResult;
	}

}
