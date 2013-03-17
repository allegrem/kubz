package midisynthesis.audiooutput;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;



/**
 * This class handles the audio stream to the speakers. The sound is played
 * as soon as it is received. 
 * @author allegrem
 */
public class SpeakersOutput implements AudioOutput {
	
	private SourceDataLine line = null;

	/**
	 * Opens the line to speakers. Of course, the line must be opened before
	 * playing sound.
	 * @throws IOException if the line can not be opened.
	 * @see synthesis.audiooutput.AudioOutput#open()
	 */
	@Override
	public void open() throws IOException {
        try {
			line = AudioSystem.getSourceDataLine(AudioOutput.audioFormat);
			line.open(AudioOutput.audioFormat);
		} catch (LineUnavailableException e) {
			line = null;
			throw new IOException("Unable to open a line to speakers output.");
		}
        line.start();
	}

	
	/**
	 * Close the line to speakers.
	 * @see synthesis.audiooutput.AudioOutput#close()
	 */
	@Override
	public void close() {
		line.drain();
        line.close();
        line = null;
	}
	

	/**
	 * Plays the given array. The sound is immediately sent to the audio buffer.
	 * You can not add several sounds. They must be added before, or played on
	 * a different line.
	 * @throws AudioException if the line has not been opened.
	 * @see synthesis.audiooutput.AudioOutput#play(byte[])
	 */
	@Override
	public void play(byte[] arr) throws AudioException {
		if (line == null)
			throw new AudioException("Speakers output line is not opened!");
		line.write(arr, 0, arr.length);
	}

}
