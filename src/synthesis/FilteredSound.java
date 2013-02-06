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
public class FilteredSound extends Observable implements Observer {

	private float length;

	private byte[] sound = null;

	private Complex[] spectrum = null;
	
	private byte[] originalSound = null;

	private Complex[] originalSpectrum = null;


	private FmInstrument instrument;

	private BandsFilter bandsFilter;
	
	private boolean bypassFilter = false;

	/**
	 * 
	 */
	public FilteredSound(FmInstrument instrument, BandsFilter bandsFilter, float length) {
		super();

		setInstrument(instrument);
		
		this.bandsFilter = bandsFilter;
		bandsFilter.addObserver(this);
		
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
			originalSound = new byte[(int) (length*AudioBlock.SAMPLE_RATE)];
			for(int i = 0; i<length*AudioBlock.SAMPLE_RATE ; i++) {
				float f = instrument.play((i)/AudioBlock.SAMPLE_RATE);
				originalSound[i] = (byte) f;
			}
		} catch (RequireAudioBlocksException e) {
			e.printStackTrace();
		}
		updateOriginalSpectrum();
	}

	private void updateOriginalSpectrum() {
		// looking for the smallest power of two above sound length
		int power2Length = 1;
		while (power2Length < originalSound.length)
			power2Length *= 2;

		// converting byte array to double array
		double[] originalSoundDouble = new double[power2Length];
		for (int i = 0; i < originalSound.length; i++)
			originalSoundDouble[i] = originalSound[i];
		for (int i = originalSound.length; i < power2Length; i++)
			originalSoundDouble[i] = 0; // add zeros at the end

		// compute fourier transform (never ask me why it works, i dont know!!)
		FastFourierTransformer fourier = new FastFourierTransformer(
				DftNormalization.STANDARD);
		originalSpectrum = fourier.transform(originalSoundDouble, TransformType.FORWARD);

		updateSpectrum();
	}

	private void updateSpectrum() {
		//TODO currently no filtering
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
		
		setChanged();
		notifyObservers();
	}

	public byte[] getSound() {
		if (bypassFilter)
			return originalSound;
		else
			return sound;
	}

	public Complex[] getSpectrum() {
		if (bypassFilter)
			return originalSpectrum;
		else
			return spectrum;
	}

	public byte[] getOriginalSound() {
		return originalSound;
	}

	public Complex[] getOriginalSpectrum() {
		return originalSpectrum;
	}

	@Override
	public void update(Observable o, Object arg) {
		updateOriginalSound();
		
		System.out.println("sound updated");
	}
	
	public void toogleBypass() {
		bypassFilter = !bypassFilter;
	}

	public BandsFilter getBandsFilter() {
		return bandsFilter;
	}

	public FmInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(FmInstrument instrument2) {
		this.instrument = instrument2;
		instrument2.addObserver(this);
		updateOriginalSound();
	}

}
