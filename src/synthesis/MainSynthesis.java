package synthesis;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.oneinputblocks.Offset;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
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
	 * @throws IOException 
	 */
	public static void main(String[] args) throws TooManyInputsException, 
	RequireAudioBlocksException, LineUnavailableException, IOException {
		
		//playground
		FixedSineWaveOscillator osc = new FixedSineWaveOscillator(100f, 10*100f);
		Offset off = new Offset(1000f);
		off.plugin(osc);
		SineWaveOscillator osc2 = new SineWaveOscillator(off, new Constant(60f));
		
		AudioBlock out = osc2; //this should have a reference to the bottom AudioBlock
		
		
		//playing sound
		SourceDataLine line = initSoundSystem();
		playSound(0f, 1f, out, line);
		closeSoundSystem(line);
		
		
		//save to wav
		AudioFormat af = new AudioFormat(AudioBlock.SAMPLE_RATE/2, 16, 1, true, true);
				//i dont know why it plays the sound twice too fast, but it does :(
        TargetDataLine lineOut = AudioSystem.getTargetDataLine(af);
        lineOut.open(af);
        lineOut.start();
        
        byte[] arr = computeSound(0f, 1f, out);
        
		AudioSystem.write(new AudioInputStream(new ByteArrayInputStream(arr), af, arr.length), 
				AudioFileFormat.Type.WAVE, new File("fmout.wav"));
		line.drain();
        line.close();
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
	
	
	public static void playSound(Float start, Float length, AudioBlock a, 
			SourceDataLine line) throws RequireAudioBlocksException {
		byte[] arr = computeSound(start, length, a);
        line.write(arr, 0, arr.length);
	}
	
	
	public static SourceDataLine initSoundSystem() 
			throws LineUnavailableException {
		final AudioFormat af = 
				new AudioFormat(AudioBlock.SAMPLE_RATE/2, 16, 1, true, true);
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
