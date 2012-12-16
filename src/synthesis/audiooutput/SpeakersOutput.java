package synthesis.audiooutput;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import synthesis.AudioBlock;

/**
 * @author allegrem
 *
 */
public class SpeakersOutput implements AudioOutput {
	
	private SourceDataLine line;

	/**
	 * @see synthesis.audiooutput.AudioOutput#open()
	 */
	@Override
	public void open() throws LineUnavailableException {
		AudioFormat af = new AudioFormat(AudioBlock.SAMPLE_RATE/2, 16, 1, true, true);
        line = AudioSystem.getSourceDataLine(af);
        line.open(af);
        line.start();
	}

	
	/**
	 * @see synthesis.audiooutput.AudioOutput#close()
	 */
	@Override
	public void close() {
		line.drain();
        line.close();
	}
	

	/**
	 * @see synthesis.audiooutput.AudioOutput#play(byte[])
	 */
	@Override
	public void play(byte[] arr) {
		line.write(arr, 0, arr.length);
	}

}
