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
 * This class handles a sound and can perform some Fourier transforms and
 * filtering. The sound is linked with a {@link FmInstrument}, and each time the
 * FmInstrument is modified, the sound (and its spectrum) is updated. The length
 * of the sound can also be edited. Finally the sound can be filtered with a
 * {@link BandsFilter}, but in this case, if the FmInstrument changes again, the
 * filtered sound will be erased. That's why a {@link Sound#lock()} method is
 * provided to prevent the sound from being modified by instrument's updates.
 * 
 * @author allegrem
 */
public class Sound extends Observable implements Observer {

	private float length;

	private byte[] sound = null;

	private Complex[] spectrum = null;

	private FmInstrument instrument;

	private boolean locked = false; // block instrument observation

	public Sound(FmInstrument instrument, float length) {
		super();

		setInstrument(instrument);

		setLength(length);
	}

	/**
	 * Change the length of the sound. The sound is updated after editing the
	 * length.
	 * 
	 * @param length
	 *            The length of the sound in seconds.
	 * @throws InvalidParameterException
	 *             if the length is negative
	 */
	public void setLength(float length) throws InvalidParameterException {
		if (length < 0)
			throw new InvalidParameterException("negative sound length");
		this.length = length;
		updateSound();
	}

	/**
	 * Compute the sound using the instrument. At the end of the method,
	 * {@link Sound#updateSpectrum()} is called.
	 */
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

	/**
	 * Compute the spectrum of the sound. It uses a FFT algorithm.
	 */
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

		// compute fourier transform
		FastFourierTransformer fourier = new FastFourierTransformer(
				DftNormalization.STANDARD);
		spectrum = fourier
				.transform(originalSoundDouble, TransformType.FORWARD);
	}

	/**
	 * Return the last computed sound (time domain).
	 */
	public byte[] getSound() {
		return sound;
	}

	/**
	 * Return the last computed spectrum of the sound (frequency domain).
	 */
	public Complex[] getSpectrum() {
		return spectrum;
	}

	/**
	 * Update the sound and its spectrum, unless the Sound is locked.
	 * 
	 * @see Sound#lock()
	 * @see Sound#unlock()
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (!locked)
			updateSound();

		System.out.println("sound updated");
	}

	/**
	 * Return the {@link FmInstrument} used to compute the sound.
	 */
	public FmInstrument getInstrument() {
		return instrument;
	}

	/**
	 * Modify the instrument linked with the sound and observe it. Then the
	 * sound is updated.
	 */
	public void setInstrument(FmInstrument instrument2) {
		this.instrument = instrument2;
		instrument2.addObserver(this);
		updateSound();
	}

	protected void applyFilter(BandsFilter filter) {
		// TODO
	}

	public Sound filter(BandsFilter filter) {
		Sound filteredSound = new Sound(instrument, length);
		filteredSound.applyFilter(filter);
		return filteredSound;
	}

	/**
	 * Lock the sound so that it is not updated if the instrument is modified.
	 */
	public void lock() {
		locked = true;
	}
	/**
	 * Unlock the sound so that it is updated if the instrument is modified.
	 */
	public void unlock() {
		locked = false;
	}

}
