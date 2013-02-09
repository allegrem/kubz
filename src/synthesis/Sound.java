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
 * filtering. The sound is linked with a {@link FmInstrumentFmInstrument}, and
 * each time the FmInstrument is modified, the sound (and its spectrum) is
 * updated. The length of the sound can also be edited. Finally the sound can be
 * filtered with a {@link BandsFilter}, but in this case, if the FmInstrument
 * changes again, the filtered sound will be erased. That's why a
 * {@link Sound#lock()} method is provided to prevent the sound from being
 * modified by instrument's updates.
 * 
 * @author allegrem
 */
public class Sound extends Observable implements Observer {

	private static final Float SAMPLING_TIME = 0.01f;

	private float length;

	private byte[] sound = null;

	private Complex[] spectrum = null;

	private FmInstrument instrument;

	private boolean locked = false; // block instrument observation

	/**
	 * Create a new Sound. The sound is updated then.
	 * 
	 * @param instrument
	 *            The {@link FmInstrument} to use.
	 * @param length
	 *            The default length of the played sounds.
	 */
	public Sound(FmInstrument instrument, float length) {
		super();
		setInstrument(instrument); // FIXME double call of update here...
		setLength(length); // /... and here
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
			throw new InvalidParameterException("Negative sound length");
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

		// notify observers
		setChanged();
		notifyObservers();
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

	// methode MA
	protected void applyFilter2(BandsFilter filter) {
		// apply filter
		int barLength = spectrum.length / 2 / filter.getBarNumber() + 1;
		for (int i = 0; i < spectrum.length / 2; i++) {
			int barNumber = i / barLength;
			double barValue = (double) filter.getBar(barNumber) / 100.;
			spectrum[i] = spectrum[i].multiply(barValue);
			spectrum[spectrum.length - i - 1] = spectrum[spectrum.length
					- i - 1].multiply(barValue);
		}

		// inverse transform
		FastFourierTransformer fourier = new FastFourierTransformer(
				DftNormalization.STANDARD);
		Complex[] fourierInverse = fourier.transform(spectrum,
				TransformType.INVERSE);

		// keep real part
		for (int i = 0; i < sound.length; i++)
			sound[i] = (byte) fourierInverse[i].getReal();
	}

	// methode BD
	protected void applyFilter(BandsFilter filter) {
		int cursor = 0;
		int sampleLength = (int) (AudioBlock.SAMPLE_RATE * SAMPLING_TIME);

		while (cursor < sound.length) {
			// extract sample
			byte[] sample = new byte[sampleLength];
			for (int i = 0; i < sampleLength; i++)
				sample[i] = sound[i + cursor];

			// compute spectrum
			Complex[] sampleSpectrum = computeFourier(sample);

			// apply filter
			int barLength = sampleSpectrum.length / 2 / filter.getBarNumber()
					+ 1;
			for (int i = 0; i < sampleSpectrum.length / 2; i++) {
				int barNumber = i / barLength;
				double barValue = (double) filter.getBar(barNumber) / 100.;
				sampleSpectrum[i] = sampleSpectrum[i].multiply(barValue);
				sampleSpectrum[sampleSpectrum.length - i - 1] = sampleSpectrum[sampleSpectrum.length
						- i - 1].multiply(barValue);
			}

			// inverse transform
			FastFourierTransformer fourier = new FastFourierTransformer(
					DftNormalization.STANDARD);
			Complex[] fourierInverse = fourier.transform(sampleSpectrum,
					TransformType.INVERSE);

			// keep real part
			for (int i = 0; i < sampleLength; i++)
				sound[i + cursor] = (byte) fourierInverse[i].getReal();

			cursor += sampleLength;
		}

		updateSpectrum();
	}

	private Complex[] computeFourier(byte[] soundPiece) {
		// looking for the smallest power of two above sound length
		int power2Length = 1;
		while (power2Length < soundPiece.length)
			power2Length *= 2;

		// converting byte array to double array
		double[] doubleSoundPiece = new double[power2Length];
		for (int i = 0; i < soundPiece.length; i++)
			doubleSoundPiece[i] = soundPiece[i];
		for (int i = soundPiece.length; i < power2Length; i++)
			doubleSoundPiece[i] = 0; // add zeros at the end

		// compute fourier transform (never ask me why it works, i dont know!!)
		FastFourierTransformer fourier = new FastFourierTransformer(
				DftNormalization.STANDARD);
		return fourier.transform(doubleSoundPiece, TransformType.FORWARD);
	}

	/**
	 * Return the current sound filtered with the given filter. The returned
	 * sound is locked, so that any change in the instrument won't modify the
	 * filtered sound.
	 * 
	 * @param filter
	 *            The {@link BandsFilter} to apply.
	 */
	public Sound filter(BandsFilter filter) {
		Sound filteredSound = new Sound(instrument, length);
		filteredSound.lock();
		filteredSound.applyFilter(filter);
//		filteredSound.applyFilter2(filter);
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
	 * Then the sound is updated to catch up with the previous changes in the
	 * instrument.
	 */
	public void unlock() {
		locked = false;
		updateSound();
	}

}
