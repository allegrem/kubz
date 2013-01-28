package synthesis;

import java.io.IOException;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
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
		
		FixedSineWaveOscillator osc1 = new FixedSineWaveOscillator(1f,(float) (500));
		Adder add = new Adder(osc1,new Constant((float) 500));
		SineWaveOscillator osc2 = new SineWaveOscillator(add,new Constant((float) 500));
		
		
		
		byte[] sound = SynthesisUtilities.computeSound(0f, 3f, osc2);
		SpeakersOutput speaker = new SpeakersOutput();
		speaker.open();
		speaker.play(sound);
		speaker.close();
		
		System.out.println(sound.length);
	}

}
