package midisynthesis.audiooutput;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


/**
 * This class can save a sound stream into a Wave file. The sound is not written
 * in real time, but saved in a buffer until the {@link WavFileOutput#close() 
 * close} method is called. Then, all the sound stored in memory is written into
 * the output file.
 * @author allegrem
 */
public class WavFileOutput implements AudioOutput {
	
	private final String fileName;
	
	private ArrayList<Byte> arr = null;
	

	/**
	 * Creates a new WavFileOutput.
	 * @param fileName the name of the file where the sound will be written.
	 */
	public WavFileOutput(String fileName) {
		super();
		this.fileName = fileName;
		arr  = new ArrayList<Byte>();
	}

	
	/**
	 * This method actually does nothing.
	 * @see synthesis.audiooutput.AudioOutput#open()
	 */
	@Override
	public void open() {
	}
	

	/**
	 * Closes the stream. The content of the buffer is written into the file at
	 * this moment.
	 * <br />
	 * Warning! Closing twice a WavFileOutput will overwrite the first sound 
	 * saved in the file
	 * @throws IOException if an error occurs while writing the sound into the
	 * file.
	 * @see synthesis.audiooutput.AudioOutput#close()
	 */
	@Override
	public void close() throws IOException {
		byte[] byte_arr = new byte[arr.size()];
		for(int i=0; i<arr.size(); i++)
			byte_arr[i] = arr.get(i);
		
		AudioSystem.write(new AudioInputStream(new ByteArrayInputStream(byte_arr), 
					AudioOutput.audioFormat, 
					arr.size()), 
				AudioFileFormat.Type.WAVE, 
				new File(fileName));
		
		arr.clear();
	}
	

	/**
	 * Adds the given array to the buffer. The sound is not written immediately
	 * in the file!
	 * @see synthesis.audiooutput.AudioOutput#play(byte[])
	 */
	@Override
	public void play(byte[] arr) {
		for(int i=0; i<arr.length; i++)
			this.arr.add(arr[i]);
	}

}
