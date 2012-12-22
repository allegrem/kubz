package synthesis;

import java.util.ArrayList;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.noinputblocks.Noise;
import synthesis.basicblocks.oneinputblocks.Offset;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
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
		
		//playground
		FixedSineWaveOscillator osc = new FixedSineWaveOscillator(100f, 10*100f);
		Offset off = new Offset(1000f);
		off.plugin(osc);
		SineWaveOscillator osc2 = new SineWaveOscillator(off, new Constant(60f));
		
		AudioBlock out = osc2; //this should have a reference to the bottom AudioBlock
		
		
		Noise noise = new Noise();
		out = noise;
		
/* one zero filter	*/	
		ArrayList<Float> feedback = new ArrayList<Float>();
		feedback.add(0.25f);
		
		ArrayList<Float> feedforward = new ArrayList<Float>();
		feedforward.add(1f);
		feedforward.add(-1f);
		
		
		
/* two pole filter
 		ArrayList<Float> feedback = new ArrayList<Float>();
		feedback.add(1f);
		feedback.add(-0.6f);
		feedback.add(0.99f);
		
		ArrayList<Float> feedforward = new ArrayList<Float>();
		feedforward.add(1f); */
		
		Filter filter = new Filter(feedback, feedforward);
		filter.plugin(noise);
		
		AudioBlock out2 = filter;
		
		//test code for adder
/*		FixedSineWaveOscillator osc3 = new FixedSineWaveOscillator(200f, 25f);
		FixedSineWaveOscillator osc4 = new FixedSineWaveOscillator(400f, 25f);
		Adder add = new Adder(new ArrayList<AudioBlock>());
		add.plugin(osc3);
		add.plugin(osc4);
		out = add;*/

		
		//playing sound
		SpeakersOutput speakersOutput = new SpeakersOutput();
		speakersOutput.open();
		speakersOutput.play(computeSound(0f, 1f, out));
		speakersOutput.play(computeSound(0f, 1f, out2));
		speakersOutput.close();
		
		
		//save to wav
		WavFileOutput wavFileOutput = new WavFileOutput("fmout.wav");
		wavFileOutput.open();
		wavFileOutput.play(computeSound(0f, 10f, out));
		wavFileOutput.close();
		
		//save to wav
		WavFileOutput wavFileOutput2 = new WavFileOutput("fmout2.wav");
		wavFileOutput2.open();
		wavFileOutput2.play(computeSound(0f, 10f, out2));
		wavFileOutput2.close();
	}
	
	
	public static byte[] computeSound(Float start, Float length, AudioBlock a) 
			throws RequireAudioBlocksException {
		byte[] arr = new byte[(int) (length*AudioBlock.SAMPLE_RATE)];
		
		for(int i = 0; i<length*AudioBlock.SAMPLE_RATE ; i++) {
			float f = a.play((start + i)/AudioBlock.SAMPLE_RATE);
			arr[i] = (byte) f;
		}
		
		return arr;
	}
	
}
