package synthesis.basicblocks.orderedinputsblocks;

//import java.util.concurrent.ConcurrentSkipListMap;
import synthesis.AudioBlock;
import synthesis.exceptions.InvalidInputException;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * This class handles a sine wave oscillator which works at variable frequency
 * and amplitude.
 * @author allegrem
 */
public class SineWaveOscillator implements AudioBlock {

	/**
	 * The frequency input of the SineWaveOscillator AudioBlock. 
	 */
	public static final int FREQUENCY_IN = 0;
	
	/**
	 * The amplitude input of the SineWaveOscillator AudioBlock. 
	 */
	public static final int AMPLITUDE_IN = 1;
	
	
	private AudioBlock frequency = null;
	private AudioBlock amplitude = null;
//	private ConcurrentSkipListMap<Float,Float> phiCache;

	
	/**
	 * Creates a new SineWaveOscillator with the given frequency and 
	 * amplitude blocks
	 * @param frequency The AudioBlock which will control the frequency of
	 * the oscillator
	 * @param amplitude The AudioBlock which will control the amplitude of
	 * the oscillator
	 */
	public SineWaveOscillator(AudioBlock frequency, AudioBlock amplitude) {
		super();
		this.frequency = frequency;
		this.amplitude = amplitude;
//		this.phiCache = new ConcurrentSkipListMap<Float,Float>();
	}
	
	
	/**
	 * Creates a new SineWaveOscillator with no block plugged in
	 */
	public SineWaveOscillator() {
		super();
	}
	
	
	/**
	 * Plugs out all the blocks plugged in.
	 */
	public void plugoutAll() {
		try {
			plugout(AMPLITUDE_IN);   
			plugout(FREQUENCY_IN);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Plugs in the given AudioBlock in the given input.
	 * @param a The AudioBlock to plug in.
	 * @param i The input number where to plug the AudioBlock in. See 
	 * {@link SineWaveOscillator} for the list of constants associated to inputs.
	 * @throws TooManyInputsException if an AudioBlock was already plugged in
	 * the given input.
	 * @throws InvalidInputException if the given input number is not valid.
	 */
	public void plugin(AudioBlock a, int i) 
			throws TooManyInputsException, InvalidInputException {
		switch (i) {
		case AMPLITUDE_IN:
			if (amplitude != null)
				throw new TooManyInputsException();
			amplitude = a;
			break;

		case FREQUENCY_IN:
			if (frequency != null)
				throw new TooManyInputsException();
			frequency = a;
			break;
			
		default:
			throw new InvalidInputException();
		}
	}
	
	
	/**
	 * Plugs out the AudioBlock plugged in the given input.
	 * @param i The input number to plug out. See {@link SineWaveOscillator} 
	 * for the list of constants associated to inputs.
	 * @throws InvalidInputException if the given input number is not valid.
	 */
	public void plugout(int i) throws InvalidInputException {
		switch (i) {
		case AMPLITUDE_IN:
			if (amplitude == null)
				System.out.println("Warning! Tried to plug out an AudioBlock" +
						"whereas no block was plugged in.");
			amplitude = null;
			break;
		
		case FREQUENCY_IN:
			if (frequency == null)
				System.out.println("Warning! Tried to plug out an AudioBlock" +
						"whereas no block was plugged in.");
			frequency = null;
			break;
			
		default:
			throw new InvalidInputException();
		}
	}


	/**
	 * @return s(t) = amplitude(t) * cos( frequency.phi(t) )
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) {		
		return (float) (amplitude.play(t) * 
				Math.cos(frequency.phi(t)));
	}


	/*
	 * (non-Javadoc)
	 * Deux idées : 
	 *  - intégrer réellement la fréquence donnée (lourd en calcul ou en 
	 *  mémoire si on met les résultats en cache) ; cette méthode ne semble pas
	 *  marcher pour l'instant
	 *  - retourner s(t) = amplitude(t) / frequency(t) * sin( frequency.phi(t) )
	 *  cette méthode fonctionne pour de la FM simple à deux oscillateurs (non
	 *  testée sur des architectures plus complexes)
	 *  
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) {
/*		//looks for the closest smaller time computed
		Float sum = 0f;
		Float startTime = phiCache.floorKey(t);
		
		if(startTime != null)
			sum = phiCache.get(startTime);
		else
			startTime = 0f;
			
		//computes the integral starting at the previous time computed
		Float step = 1f / SAMPLE_RATE;
		for(Float u = startTime + step; u < t; u += step)
			sum += frequency.play(u);
		
		phiCache.put(t, sum);  //comment this line to disable caching
		return (float) (2 * Math.PI * step * sum);*/
		
		
		return (float) (amplitude.play(t) / frequency.play(t) * 
				Math.sin(frequency.phi(t)));  //a verifier !!!
	}

}
