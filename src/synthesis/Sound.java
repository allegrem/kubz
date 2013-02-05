package synthesis;

import java.security.InvalidParameterException;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.filters.Equalizer;
import synthesis.fmInstruments.FmInstrument;

/**
 * @author allegrem
 * 
 */
public class Sound {

	private float length;

	private byte[] sound = null;

	private Complex[] spectrum = null;

	private byte[] originalSound = null;

	private Complex[] originalSpectrum = null;

	private final FmInstrument instrument;

	private final Equalizer equalizer;

	/**
	 * 
	 */
	public Sound(FmInstrument instrument, Equalizer equalizer, float length) {
		super();

		this.instrument = instrument;
		this.equalizer = equalizer;
		setLength(length);
	}

	public void setLength(float length) throws InvalidParameterException {
		if (length < 0)
			throw new InvalidParameterException("negative sound length");
		this.length = length;
		updateOriginalSound();
	}

	private void updateOriginalSound() {
		try {
			sound = SynthesisUtilities.computeSound(0f, length, instrument);
		} catch (RequireAudioBlocksException e) {
			e.printStackTrace();
		}
		updateOriginalSpectrum();
	}

	private void updateOriginalSpectrum() {
		// looking for the smallest power of two above sound length
		int power2Length = 1;
		while (power2Length < sound.length)
			power2Length *= 2;

		// converting byte array to double array
		double[] sound = new double[power2Length];
		for (int i = 0; i < sound.length; i++)
			sound[i] = sound[i];
		for (int i = sound.length; i < power2Length; i++)
			sound[i] = 0; // add zeros at the end

		// compute fourier transform (never ask me why it works, i dont know!!)
		FastFourierTransformer fourier = new FastFourierTransformer(
				DftNormalization.STANDARD);
		originalSpectrum = fourier.transform(sound, TransformType.FORWARD);

		updateSpectrum();
	}

	private void updateSpectrum() {
		//TODO
		spectrum = originalSpectrum.clone();
		
		updateSound();
	}

	private void updateSound() {
		//inverse Fourier transform
		FastFourierTransformer fourierTransform = new FastFourierTransformer(
				DftNormalization.STANDARD);
		Complex[] complexResult = fourierTransform.transform(spectrum,
				TransformType.INVERSE);
		sound = new byte[originalSound.length];

		// keep real part
		for (int i = 0; i < sound.length; i++)
			sound[i] = (byte) complexResult[i].getReal();
	}

	public byte[] getSound() {
		return sound;
	}

	public Complex[] getSpectrum() {
		return spectrum;
	}

	public byte[] getOriginalSound() {
		return originalSound;
	}

	public Complex[] getOriginalSpectrum() {
		return originalSpectrum;
	}

}
