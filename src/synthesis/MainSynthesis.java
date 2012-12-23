package synthesis;

import java.util.ArrayList;
import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.noinputblocks.Noise;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.oneinputblocks.Offset;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.filters.Filter;



/**
 * This main is used to test the synthesis engine. This should not be used
 * in the final project.
 * @author allegrem
 */
public class MainSynthesis {

	/**
	 * WARNING! This main should not be used in the final project!!
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		//windInstrument tests
		final Float vibratoFreq = 5f;  //f_v
		final Float vibratoFactor = 0.01f;  //alpha
		final Float frequency = 440f;  //f_m
		final Float jitterFactor = 0.0000005f;  //sigma
		final Float ampNoiseFactor1 = 0.0001f;  //K_1
		final Float envFactor = 10f;  //I
		final Float ampNoiseFactor2 = 10f;  //K_2
		final Float freqFactor = 1.1f;  //H
		
		
		//building wind instrument
		AudioBlock vibrato = new FixedSineWaveOscillator(vibratoFreq, 
				frequency*vibratoFactor);
		
		Gain lowFreqNoise = new Gain(jitterFactor*frequency);
		lowFreqNoise.plugin(new Noise()); //WARNING! Mising low pass filter
		
		Adder freqInput1 = new Adder(new ArrayList<AudioBlock>()); //Berk...
		freqInput1.plugin(vibrato);
//		freqInput1.plugin(lowFreqNoise);
		freqInput1.plugin(new Constant(frequency));
		
		
		Gain noise = new Gain(ampNoiseFactor1);
		noise.plugin(new Noise()); //low pass?
		
		Adder ampInput1 = new Adder(new ArrayList<AudioBlock>()); //Berk...
		ampInput1.plugin(noise);
		ampInput1.plugin(new Constant(envFactor)); //WARNING! Missing envelope
		
		
		SineWaveOscillator osc1 = new SineWaveOscillator(freqInput1, ampInput1);
		
		
		Gain amplifiedOsc1 = new Gain(100f);
		amplifiedOsc1.plugin(osc1);
		
		
		Gain amplifiedFreqInput1 = new Gain(freqFactor);
		amplifiedFreqInput1.plugin(freqInput1);
		
		Adder freqInput2 = new Adder(new ArrayList<AudioBlock>()); //Berk...
		freqInput2.plugin(amplifiedOsc1);
		freqInput2.plugin(amplifiedFreqInput1);
		
		Gain ampInput2 = new Gain(ampNoiseFactor2);
		ampInput2.plugin(ampInput1);
		
		AudioBlock windy = new SineWaveOscillator(freqInput2, ampInput2);
		
		byte[] output = computeSound(0f, 1f, windy);
		
		
		
		
		//test integrale fonction phi
/*		FixedSineWaveOscillator o1 = new FixedSineWaveOscillator(440f, 440f);
		Offset off = new Offset(440f);
		off.plugin(o1);
		SineWaveOscillator o2 = new SineWaveOscillator(off, new Constant(120f));
		
		SineWaveOscillator o1_ = new SineWaveOscillator(new Constant(440f), new Constant(440f));
		Offset off_ = new Offset(440f);
		off_.plugin(o1_);
		SineWaveOscillator o2_ = new SineWaveOscillator(off_, new Constant(120f));

		
		byte[] output = computeSound(0f, 1f, o2);
		byte[] output_ = computeSound(0f, 1f, o2_);*/
		
		
		
		//playing sound
		SpeakersOutput speakersOutput = new SpeakersOutput();
		speakersOutput.open();
		speakersOutput.play(output);
		speakersOutput.close();
		
		//save to wav 1
		WavFileOutput wavFileOutput1 = new WavFileOutput("fmout.wav");
		wavFileOutput1.open();
		wavFileOutput1.play(output);
		wavFileOutput1.close();
		
		//save to wav 2
		WavFileOutput wavFileOutput2 = new WavFileOutput("fmout2.wav");
		wavFileOutput2.open();
		wavFileOutput2.close();
	}
	
	
	public static byte[] computeSound(Float start, Float length, AudioBlock a) 
			throws RequireAudioBlocksException {
		byte[] arr = new byte[(int) (length*AudioBlock.SAMPLE_RATE)];
		
		for(int i = 0; i<length*AudioBlock.SAMPLE_RATE ; i++) {
			float f = a.play((start + i)/AudioBlock.SAMPLE_RATE);
			arr[i] = (byte) f;
			if(i % 1000 == 0)
				System.out.println(((int) (i / (length*AudioBlock.SAMPLE_RATE) * 100)) + "%");
		}
		
		return arr;
	}
	
}
