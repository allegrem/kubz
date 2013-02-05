package synthesis;

import java.security.InvalidParameterException;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.filters.BandsFilter;
import synthesis.fmInstruments.FmInstrument;

/**
 * @author allegrem
 * 
 */
public class Sound extends Observable implements Observer {

	private float length;

	private byte[] sound = null;

	private Complex[] spectrum = null;

	private FmInstrument instrument;

	public Sound(FmInstrument instrument, float length) {
		super();

		setInstrument(instrument);

		setLength(length);
	}

	public void setLength(float length) throws InvalidParameterException {
		if (length < 0)
			throw new InvalidParameterException("negative sound length");
		this.length = length;
		updateSound();
	}

	private void updateSound() {
		try {
			sound = new byte[(int) (length * AudioBlock.SAMPLE_RATE)];
			for (int i = 0; i < length * AudioBlock.SAMPLE_RATE; i++) {
				float f = instrument.play((i) / AudioBlock.SAMPLE_RATE);
				sound[i] = (byte) f;
			}
		} catch (RequireAudioBlocksException e) {
			e.printStackTrace();
		}
		updateSpectrum();
	}

	private void updateSpectrum() {
		// looking for the smallest power of two above sound length
		int power2Length = 1;
		while (power2Length < sound.length)
			power2Length *= 2;

		// converting byte array to double array
		double[] originalSoundDouble = new double[power2Length];
		for (int i = 0; i < sound.length; i++)
			originalSoundDouble[i] = sound[i];
		for (int i = sound.length; i < power2Length; i++)
			originalSoundDouble[i] = 0; // add zeros at the end

		// compute fourier transform (never ask me why it works, i dont know!!)
		FastFourierTransformer fourier = new FastFourierTransformer(
				DftNormalization.STANDARD);
		spectrum = fourier.transform(originalSoundDouble,
				TransformType.FORWARD);
	}

	public byte[] getSound() {
		return sound;
	}

	public Complex[] getSpectrum() {
		return spectrum;
	}

	@Override
	public void update(Observable o, Object arg) {
		updateSound();

		System.out.println("sound updated");
	}

	public FmInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(FmInstrument instrument2) {
		this.instrument = instrument2;
		instrument2.addObserver(this);
		updateSound();
	}
	
	protected void applyFilter(BandsFilter filter) {
		//TODO
	}
	
	public Sound filter(BandsFilter filter) {
		Sound filteredSound = new Sound(instrument, length);
		filteredSound.applyFilter(filter);
		return filteredSound;
	}

}
