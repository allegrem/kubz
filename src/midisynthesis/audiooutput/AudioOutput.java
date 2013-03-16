package midisynthesis.audiooutput;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;

import midisynthesis.audioblocks.MidiAudioBlock;


/**
 * This interface defines all the methods provided by an audio output. It should
 * be able to play an array of bytes. The output can be for example speakers, 
 * a stream over the network, an audio file,...
 * <br />
 * The usual way to play a sound is to first {@link AudioOutput#open() open} the 
 * stream, then {@link AudioOutput#play(byte[]) play} as many sounds as needed, 
 * and finally {@link AudioOutput#close() close} the stream.
 * @author allegrem
 */
public interface AudioOutput {
	
	/**
	 * Defines the AudioFormat of the outputs.
	 */
	public static final AudioFormat audioFormat = 
			new AudioFormat(MidiAudioBlock.SAMPLE_RATE/2, 16, 1, true, true);
	
	
	/**
	 * Opens the output stream.
	 * @throws IOException if the stream failed to open.
	 */
	public void open() throws IOException;
	
	
	/**
	 * Closes the output stream.
	 * @throws IOException if the stream failed to close.
	 */
	public void close() throws IOException;
	
	
	/**
	 * Sends a sound to the stream.
	 * @param arr An array of bytes representing the sound.
	 * @throws AudioException if an error occurred while playing the sound.
	 * @see AudioOutput#audioFormat 
	 */
	public void play(byte[] arr) throws AudioException;
	
}
