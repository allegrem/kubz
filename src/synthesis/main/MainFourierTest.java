package synthesis.main;

import java.io.IOException;

import synthesis.AudioBlock;
import synthesis.SynthesisUtilities;
import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.ADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.AudioException;
import synthesis.exceptions.RequireAudioBlocksException;

public class MainFourierTest {

	/**
	 * @param args
	 * @throws RequireAudioBlocksException 
	 * @throws AudioException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws RequireAudioBlocksException, AudioException, IOException {
		
		/*FixedSineWaveOscillator osc1 = new FixedSineWaveOscillator(1f,(float) (500));
		Adder add = new Adder(osc1,new Constant((float) 500));
		SineWaveOscillator osc2 = new SineWaveOscillator(add,new Constant((float) 500));*/
		
		ADSR env1 = new ADSR(new Constant(0.3f), new Constant(0.2f), new Constant(0.8f), new Constant(0.1f), 1f, new Constant(100f));
		AudioBlock fm = new Constant(600f);
		Gain fp = new Gain(1.5f, fm);
		Adder adder1 = new Adder(new Gain(4f,fm),env1);
		SineWaveOscillator osc1 = new SineWaveOscillator(fm,
				adder1);
		Adder adder2 = new Adder(fp,osc1);
		SineWaveOscillator osc2 = new SineWaveOscillator(adder2,env1);
		
		
		
		byte[] sound = SynthesisUtilities.computeSound(0f, 3f, osc2);
		SpeakersOutput speaker = new SpeakersOutput();
		speaker.open();
		speaker.play(sound);
		speaker.close();
		
		
		
		System.out.println(sound.length);
	}

}
