package synthesis.fmInstruments;

import java.util.ArrayList;
import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.WhiteNoise;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.TooManyInputsException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * @author allegrem
 * 
 */
//public class WindInstrument extends FmInstrument {
//
//	private final ParameterAudioBlock vibratoFreq; // f_v
//	private final ParameterAudioBlock vibratoFactor; // alpha
//	private final ParameterAudioBlock frequency; // f_m
//	private final ParameterAudioBlock jitterFactor; // sigma
//	private final ParameterAudioBlock ampNoiseFactor1; // K_1
//	private final ParameterAudioBlock envFactor; // I
//	private final ParameterAudioBlock ampNoiseFactor2; // K_2
//	private final ParameterAudioBlock freqFactor; // H
//
//	public WindInstrument()
//			throws TooManyInputsException {
//		super();
//		
//		AudioBlock vibrato = new SineWaveOscillator(vibratoFreq,
//				new Multiplier(frequency, vibratoFactor));
//
//		Multiplier lowFreqNoise = new Multiplier(jitterFactor, frequency);
//		lowFreqNoise.plugin(new WhiteNoise()); // WARNING! Mising low pass
//												// filter
//
//		Adder freqInput1 = new Adder(new ArrayList<AudioBlock>()); // Berk...
//		freqInput1.plugin(vibrato);
//		// freqInput1.plugin(lowFreqNoise);
//		freqInput1.plugin(frequency);
//
//		Multiplier noise = new Multiplier(ampNoiseFactor1, new WhiteNoise()); // low
//																				// pass?
//
//		Adder ampInput1 = new Adder(new ArrayList<AudioBlock>()); // Berk...
//		ampInput1.plugin(noise);
//		ampInput1.plugin(envFactor); // WARNING! Missing envelope
//
//		SineWaveOscillator osc1 = new SineWaveOscillator(freqInput1, ampInput1);
//
//		Gain amplifiedOsc1 = new Gain(100f);
//		amplifiedOsc1.plugin(osc1);
//
//		Multiplier amplifiedFreqInput1 = new Multiplier(freqFactor, freqInput1);
//
//		Adder freqInput2 = new Adder(new ArrayList<AudioBlock>()); // Berk...
//		freqInput2.plugin(amplifiedOsc1);
//		freqInput2.plugin(amplifiedFreqInput1);
//
//		Multiplier ampInput2 = new Multiplier(ampNoiseFactor2, ampInput1);
//
//		setOut(new SineWaveOscillator(freqInput2, ampInput2));
//	}
//
//}
