package synthesis;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.fmInstruments.WindInstrument;

public class MainFmInstrTest {

	/**
	 * Just to test the bell sound.
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		FixedADSR env0 = new FixedADSR(0.000000001f, 0.7f, 0.09f, 0.0001f, 1f);
		//FixedSineWaveOscillator osc0 = new FixedSineWaveOscillator(100f, 10*100f); 
		env0.plugin(new Constant(320f)); //this will be by default
		//AudioBlock input0 = env0;
		Float freq0 = 90.0f;
		SineWaveOscillator swo = new SineWaveOscillator(new Constant(freq0),env0);
		
		Float freq1 = (float) (freq0);
		Adder adder = new Adder( new Constant(freq1),swo );
		//adder.plugin(new Constant(freq1));
		//adder.plugin(swo);
		
		FixedADSR env1 = new FixedADSR(0.000000001f, 0.7f, 0.09f, 0.0001f, 1f);
		//FixedSineWaveOscillator osc1 = new FixedSineWaveOscillator(100f, 10*100f); 
		env1.plugin(new Constant(520f)); //this will be by default
		
		SineWaveOscillator swoOut = new SineWaveOscillator(adder,env1);
		AudioBlock out = swoOut;
		
		//computing sound
		byte[] output = SynthesisUtilities.computeSound(0f, 1f, out);
		//playing sound
		SpeakersOutput speakersOutput = new SpeakersOutput();
		speakersOutput.open();
		speakersOutput.play(output);
		speakersOutput.close();
		//save to wav
		WavFileOutput wavFileOutput1 = new WavFileOutput("fmout3.wav");
		wavFileOutput1.open();
		wavFileOutput1.play(output);
		wavFileOutput1.close();
		
		
		
		
	}

}
