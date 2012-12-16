package synthesis;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import synthesis.basicblocks.Adder;
import synthesis.basicblocks.noinputblocks.SineWaveOscillator;
import synthesis.basicblocks.noinputblocks.SquareWaveOscillator;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.oneinputblocks.Offset;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * This main is used to test the synthesis engine. This should not be used
 * in the final project.
 * @author allegrem
 */
public class MainSynthesis {

	/**
	 * WARNING! This main should not be used in the final project!!
	 * @param args
	 * @throws TooManyInputsException 
	 * @throws RequireAudioBlocksException 
	 * @throws LineUnavailableException 
	 */
	public static void main(String[] args) throws TooManyInputsException, 
	RequireAudioBlocksException, LineUnavailableException {
		SineWaveOscillator osc = new SineWaveOscillator(110f, 120f);
		SquareWaveOscillator osc2 = new SquareWaveOscillator(440f, 20f);
		
		Offset off = new Offset(60f);
		off.plugin(osc);
		
		Gain g = new Gain(2f);
		g.plugin(osc);
		
		SourceDataLine line = initSoundSystem();
		playSound(0f, 1f, osc, line);
		playSound(0f, 2f, osc2, line);
		closeSoundSystem(line);
		
/*		byte[] arr = computeSound(0f, 1f, osc2); 
		for(int i = 0; i<1f*AudioBlock.sampleRate ; i++)
			System.out.println(arr[i]);*/
	}
	
	
	public static byte[] computeSound(Float start, Float length, AudioBlock a) 
			throws RequireAudioBlocksException {
		byte[] arr = new byte[(int) (length*AudioBlock.sampleRate)];
		
		for(int i = 0; i<length*AudioBlock.sampleRate ; i++) {
			float f = a.play((start + i)/AudioBlock.sampleRate);
			arr[i] = (byte) f;
		}
		
		return arr;
	}
	
	
	public static void playSound(Float start, Float length, AudioBlock a, 
			SourceDataLine line) throws RequireAudioBlocksException {
		byte[] arr = computeSound(start, length, a);
        line.write(arr, 0, arr.length);
	}
	
	
	public static SourceDataLine initSoundSystem() 
			throws LineUnavailableException {
		final AudioFormat af = 
				new AudioFormat(AudioBlock.sampleRate, 16, 1, true, true);
        SourceDataLine line = AudioSystem.getSourceDataLine(af);
        line.open(af);
        line.start();
        return line;
	}
	

	public static void closeSoundSystem(SourceDataLine line) {
		line.drain();
        line.close();
	}
}
