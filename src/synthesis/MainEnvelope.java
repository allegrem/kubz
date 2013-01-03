/**
 * 
 */
package synthesis;

import java.io.IOException;
import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.noinputblocks.Noise;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.basicblocks.oneinputblocks.Offset;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.exceptions.AudioException;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * @author allegrem
 *
 */
public class MainEnvelope {

	/**
	 * @param args
	 * @throws TooManyInputsException 
	 * @throws IOException 
	 * @throws RequireAudioBlocksException 
	 * @throws AudioException 
	 */
	public static void main(String[] args) throws TooManyInputsException, IOException, AudioException, RequireAudioBlocksException {
		//playground
		FixedSineWaveOscillator osc = new FixedSineWaveOscillator(100f, 10*100f);
		Offset off = new Offset(1000f);
		off.plugin(osc);
		SineWaveOscillator osc2 = new SineWaveOscillator(off, new Constant(60f));
		
		AudioBlock out = osc2; //this (i.e. out) should have a reference to the bottom AudioBlock
		
		Noise noise = new Noise();
		out = noise;
		
		//testing adsr envelope
		Constant c = new Constant(120f);
		FixedADSR env = new FixedADSR(0.1f, 0.1f, 0.5f, 0.3f, 1f);
		env.plugin(c);
		out = env;
		
		
		//playing sound
		SpeakersOutput speakersOutput = new SpeakersOutput();
		speakersOutput.open();
		speakersOutput.play(computeSound(0f, 1f, out));
//		speakersOutput.play(computeSound(0f, 1f, out2));
		speakersOutput.close();
		
		
		//save to wav
		WavFileOutput wavFileOutput = new WavFileOutput("fmout.wav");
		wavFileOutput.open();
		wavFileOutput.play(computeSound(0f, 10f, out));
		wavFileOutput.close();
		
		//save to wav
		/*WavFileOutput wavFileOutput2 = new WavFileOutput("fmout2.wav");
		wavFileOutput2.open();
		wavFileOutput2.play(computeSound(0f, 10f, out2));
		wavFileOutput2.close();*/
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
