package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.noinputblocks.Noise;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * @author allegrem
 *
 */
public class WindInstrument implements AudioBlock {
	
	private final Float vibratoFreq;  //f_v
	
	private final Float vibratoFactor;  //alpha
	
	private final Float frequency;  //f_m
	
	private final Float jitterFactor;  //sigma
	
	private final Float ampNoiseFactor1;  //K_1
	
	private final Float envFactor;  //I
	
	private final Float ampNoiseFactor2;  //K_2
	
	private final Float freqFactor;  //H
	
	
	private AudioBlock out;
	
	
	/**
	 * @param vibratoFreq
	 * @param frequency
	 * @param jitterFactor
	 * @param ampNoiseFactor1
	 * @param envFactor
	 * @param ampNoiseFactor2
	 * @param freqFactor
	 * @param vibratoFactor 
	 * @throws TooManyInputsException 
	 */
	public WindInstrument(Float vibratoFreq, Float frequency,
			Float jitterFactor, Float ampNoiseFactor1, Float envFactor,
			Float ampNoiseFactor2, Float freqFactor, Float vibratoFactor) throws TooManyInputsException {
		super();
		this.vibratoFreq = vibratoFreq;
		this.frequency = frequency;
		this.jitterFactor = jitterFactor;
		this.ampNoiseFactor1 = ampNoiseFactor1;
		this.envFactor = envFactor;
		this.ampNoiseFactor2 = ampNoiseFactor2;
		this.freqFactor = freqFactor;
		this.vibratoFactor = vibratoFactor;
		
		this.out = build();
	}
	
	
	private AudioBlock build() throws TooManyInputsException {
		AudioBlock vibrato = new FixedSineWaveOscillator(vibratoFreq, 
				frequency*vibratoFactor);
		
		Gain lowFreqNoise = new Gain(jitterFactor*frequency);
		lowFreqNoise.plugin(new Noise()); //WARNING! Mising low pass filter
		
		Adder freqInput1 = new Adder(new ArrayList<AudioBlock>()); //Berk...
		freqInput1.plugin(vibrato);
		freqInput1.plugin(lowFreqNoise);
		freqInput1.plugin(new Constant(frequency));
		
		
		Gain noise = new Gain(ampNoiseFactor1);
		noise.plugin(new Noise());
		
		Adder ampInput1 = new Adder(new ArrayList<AudioBlock>()); //Berk...
		ampInput1.plugin(noise);
		ampInput1.plugin(new Constant(envFactor)); //WARNING! Missing envelope
		
		
		SineWaveOscillator osc1 = new SineWaveOscillator(freqInput1, ampInput1);
		
		
		Gain amplifiedFreqInput1 = new Gain(freqFactor);
		amplifiedFreqInput1.plugin(freqInput1);
		
		Adder freqInput2 = new Adder(new ArrayList<AudioBlock>()); //Berk...
		freqInput2.plugin(osc1);
		freqInput2.plugin(amplifiedFreqInput1);
		
		Gain ampInput2 = new Gain(ampNoiseFactor2);
		ampInput2.plugin(ampInput1);
		
		return new SineWaveOscillator(freqInput2, ampInput2);
	}
	

	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return out.play(t);
	}

	
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return out.play(t);
	}
	
	
	
	
}
