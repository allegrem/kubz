package synthesis;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
		ArrayList<Double> soundFiltered = new ArrayList<Double>();
		float time = 0;			
		int sampleLength = (int) (AudioBlock.SAMPLE_RATE*20/1000); //*20ms sampling
		int bandfreq = (int) (this.length*AudioBlock.SAMPLE_RATE/2)/11; //length of each band of the filter (11 bands on the whole)
		for (int i=0;i<(int) (this.length*AudioBlock.SAMPLE_RATE/2)/sampleLength;i++){  //(length*SR/2) / sampleLength (n°of samples in the 
			time += i*(20/1000);	//the i-th sampling														//first half
			byte[] soundi = new byte[sampleLength];
			for (int j=0;j<sampleLength;j++){
				soundi[j] = sound[(int) (j+time*AudioBlock.SAMPLE_RATE)];
			}			
			Complex[] fourierCoeffs = computeFourier(soundi);
			int bari = (int) ( (time+1)*AudioBlock.SAMPLE_RATE/bandfreq ); //which bar choose
			int coeff = filter.getBar(bari);
			double[] fourierFiltered = new double[fourierCoeffs.length];
			for (int h = 0;h<fourierCoeffs.length;h++){						
				fourierFiltered[i] = abs(fourierCoeffs[h]) * coeff;
			}
			Complex[] fourierInverse = computeFourierInverse(fourierFilterd);
			for (int k=0;k<fourierInverse.length;k++){
				double real = fourierFilterd[k].getReal();
				soundFiltered.add(new Double(real));				
			}
															
		}
		
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
		return fourier.transform(doubleSoundPiece,
				TransformType.FORWARD);
	}
	
	public Sound filter(BandsFilter filter) {
		Sound filteredSound = new Sound(instrument, length);
		filteredSound.applyFilter(filter);
		return filteredSound;
	}

}
