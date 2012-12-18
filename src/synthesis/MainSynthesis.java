package synthesis;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.oneinputblocks.Offset;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.exceptions.RequireAudioBlocksException;

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
		
		
		//playing sound
		SpeakersOutput speakersOutput = new SpeakersOutput();
		speakersOutput.open();
		speakersOutput.play(computeSound(0f, 1f, out));
		speakersOutput.play(computeSound(0f, 1f, osc));
		speakersOutput.close();
		
		
		//save to wav
		WavFileOutput wavFileOutput = new WavFileOutput("fmout.wav");
		wavFileOutput.open();
		wavFileOutput.play(computeSound(0f, 1f, out));
		wavFileOutput.play(computeSound(0f, 1f, osc));
		wavFileOutput.close();
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
