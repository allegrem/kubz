package synthesis.midiPlayground;

import java.io.IOException;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.exceptions.AudioException;

public class MainMidiPlayground {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//The Keyboard 
		//MidiKeyboard keyboard = new MidiKeyboard();
		
		// ==========================
		
		Melody melody = new Melody();
		melody.startPlaying();
	}

}
