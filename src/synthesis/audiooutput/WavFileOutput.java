package synthesis.audiooutput;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import synthesis.AudioBlock;

/**
 * @author allegrem
 *
 */
public class WavFileOutput implements AudioOutput {
	
	private final String fileName;
	
	private ArrayList<Byte> arr = null;
	

	/**
	 * @param fileName
	 */
	public WavFileOutput(String fileName) {
		super();
		this.fileName = fileName;
		arr  = new ArrayList<Byte>();
	}

	
	/**
	 * @see synthesis.audiooutput.AudioOutput#open()
	 */
	@Override
	public void open() {
	}
	

	/**
	 * @throws IOException 
	 * @see synthesis.audiooutput.AudioOutput#close()
	 */
	@Override
	public void close() throws IOException {
		byte[] byte_arr = new byte[arr.size()];
		for(int i=0; i<arr.size(); i++)
			byte_arr[i] = arr.get(i);
		
		AudioSystem.write(new AudioInputStream(new ByteArrayInputStream(byte_arr), 
					new AudioFormat(AudioBlock.SAMPLE_RATE/2, 16, 1, true, true), 
					arr.size()), 
				AudioFileFormat.Type.WAVE, 
				new File(fileName));
	}
	

	/**
	 * @throws Exception 
	 * @see synthesis.audiooutput.AudioOutput#play(byte[])
	 */
	@Override
	public void play(byte[] arr) throws Exception {
		for(int i=0; i<arr.length; i++)
			this.arr.add(arr[i]);
	}

}
