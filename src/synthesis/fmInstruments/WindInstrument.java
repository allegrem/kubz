package synthesis.fmInstruments;

import java.util.ArrayList;

import parameter.ParameterAudioBlock;


import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.noinputblocks.WhiteNoise;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * @author allegrem
 * 
 */
public class WindInstrument implements FmInstrument {

	private AudioBlock vibratoFreq; // f_v

	private AudioBlock vibratoFactor; // alpha

	private AudioBlock frequency; // f_m

	private AudioBlock jitterFactor; // sigma

	private AudioBlock ampNoiseFactor1; // K_1

	private AudioBlock envFactor; // I

	private AudioBlock ampNoiseFactor2; // K_2

	private AudioBlock freqFactor; // H

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
	public WindInstrument(AudioBlock vibratoFreq, AudioBlock frequency,
			AudioBlock jitterFactor, AudioBlock ampNoiseFactor1,
			AudioBlock envFactor, AudioBlock ampNoiseFactor2,
			AudioBlock freqFactor, AudioBlock vibratoFactor)
			throws TooManyInputsException {
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
		AudioBlock vibrato = new SineWaveOscillator(vibratoFreq,
				new Multiplier(frequency, vibratoFactor));

		Multiplier lowFreqNoise = new Multiplier(jitterFactor, frequency);
		lowFreqNoise.plugin(new WhiteNoise()); // WARNING! Mising low pass
												// filter

		Adder freqInput1 = new Adder(new ArrayList<AudioBlock>()); // Berk...
		freqInput1.plugin(vibrato);
		// freqInput1.plugin(lowFreqNoise);
		freqInput1.plugin(frequency);

		Multiplier noise = new Multiplier(ampNoiseFactor1, new WhiteNoise()); // low
																				// pass?

		Adder ampInput1 = new Adder(new ArrayList<AudioBlock>()); // Berk...
		ampInput1.plugin(noise);
		ampInput1.plugin(envFactor); // WARNING! Missing envelope

		SineWaveOscillator osc1 = new SineWaveOscillator(freqInput1, ampInput1);

		Gain amplifiedOsc1 = new Gain(100f);
		amplifiedOsc1.plugin(osc1);

		Multiplier amplifiedFreqInput1 = new Multiplier(freqFactor, freqInput1);

		Adder freqInput2 = new Adder(new ArrayList<AudioBlock>()); // Berk...
		freqInput2.plugin(amplifiedOsc1);
		freqInput2.plugin(amplifiedFreqInput1);

		Multiplier ampInput2 = new Multiplier(ampNoiseFactor2, ampInput1);

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

	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

}
