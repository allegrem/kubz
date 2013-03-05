package synthesis.midiPlayground.MidiInstruments;

import java.io.IOException;

import synthesis.audiooutput.AudioOutput;
import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.exceptions.AudioException;
import synthesis.midiPlayground.MidiCommand;
import synthesis.midiPlayground.SubMidiInstrument;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;

/**
 * This abstract class defines the default behavior of a MIDI Instrument. An
 * instrument can play several notes at the same time (the maximum number of
 * notes defined by the constant {@link MidiInstrument#MAX_SIMULTANEOUS_NOTES}).
 * For now, the instrument can be played in real time with the
 * {@link MidiInstrument#run()} method.
 * 
 * @author allegrem
 */
public abstract class MidiInstrument extends Thread {

	/**
	 * Maximal number of notes which can be played simultaneously.
	 */
	private static final int MAX_SIMULTANEOUS_NOTES = 10;

	private final SubMidiInstrument[] subInstruments; // each note is handled by
														// a subinstrument

	private int lastSubInstrumentIndex; // index in the subinstruments list

	private boolean keepPlaying = true; // set it to false to stop the run loop
	WavFileOutput wavFileOutput = new WavFileOutput("sound.wav"); //to hear all the modifs during the synthesis

	/**
	 * Create a new MidiInstrument. Each subinstrument loads the audioblocks as
	 * defined in the {@link MidiInstrument#buildInstrument()} method.
	 */
	public MidiInstrument() {
		subInstruments = new SubMidiInstrument[MAX_SIMULTANEOUS_NOTES];
		lastSubInstrumentIndex = MAX_SIMULTANEOUS_NOTES - 1;
		for (int i = 0; i < subInstruments.length; i++)
			subInstruments[i] = new SubMidiInstrument((synthesis.midiPlayground.MidiAudioBlock) buildInstrument());
	}

	/**
	 * Build the instrument. This method must be redefined in each instrument.
	 * It defines how the AudioBlocks are connected between them.
	 * 
	 * @return The output MidiAudioBlock.
	 */
	protected abstract MidiAudioBlock buildInstrument();

	/**
	 * This method handles MIDI commands. {@link MidiCommand#NOTE_ON}: add the
	 * note to the subinstrument list (remove the oldest note played if the list
	 * is full), then the command is given to the subinstrument.
	 * {@link MidiCommand#NOTE_OFF}: gives the command to the subinstrument
	 * which is playing this note. By default, the command is broadcasted to
	 * all the subinstruments.
	 */
	public void command(MidiCommand command) {
		switch (command.getCommand()) {
		case MidiCommand.NOTE_ON:
			incrLastSubInstrumentIndex();
			subInstruments[lastSubInstrumentIndex].command(command);
			break;

		case MidiCommand.NOTE_OFF:
			SubMidiInstrument instr = getSubInstrumentByNote(command
					.getParam1());
			if (instr != null)
				instr.command(command);
			break;

		default:
			for (SubMidiInstrument subInstrument : subInstruments)
				subInstrument.command(command);
			break;
		}

	}

	/**
	 * 
	 * @param sampleRate
	 * @param samples
	 * @return
	 */
	public byte[] play(int sampleRate, int samples) {
		int[] out = new int[samples];
		int notes = 0; // count number of simultaneous played notes

		for (SubMidiInstrument subInstrument : subInstruments) {
			int zeros = 0; // count number of zeros returned by playNextSample
			int s; // sample counter
			for (s = 0; s < samples; s++) {
				byte returnPlay = subInstrument.playNextSample(sampleRate);
				out[s] += returnPlay;
				if (returnPlay == 0) {
					zeros++;
					if (zeros > 10) // not more than 10 zeros in a row
						break;
				} else
					// if the result is not zero, reset the counter
					zeros = 0;
			}
			if (s == samples) // if the loop was not broken
				notes++;
		}

		byte[] outBytes = new byte[samples];
		if (notes > 0) {
			for (int s = 0; s < samples; s++)
				// normalize the output level
				outBytes[s] = (byte) (out[s] / notes);
		}
		return outBytes;
	}

	private void incrLastSubInstrumentIndex() {
		int startIndex = lastSubInstrumentIndex;
		do {
			lastSubInstrumentIndex++;
			if (lastSubInstrumentIndex == subInstruments.length)
				lastSubInstrumentIndex = 0;
		} while (lastSubInstrumentIndex != startIndex
				&& subInstruments[lastSubInstrumentIndex].getNote() != 0);
		if (lastSubInstrumentIndex == startIndex)
			lastSubInstrumentIndex++;
	}

	private SubMidiInstrument getSubInstrumentByNote(int note) {
		for (SubMidiInstrument subInstrument : subInstruments) {
			if (subInstrument.getNote() == note)
				return subInstrument;
		}
		return null;
	}

	public void run() {
		// open speakers output
		SpeakersOutput speakersOutput = new SpeakersOutput();
		//open WavFile as well (done implicitly)	
		try {
			speakersOutput.open();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("starting run in MidiInstrument");

		while (keepPlaying) {
			byte[] out = play(44100, 500);
			try {
				speakersOutput.play(out);
				wavFileOutput.play(out);
			} catch (AudioException e) {
				e.printStackTrace();
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("stopping run in MidiInstrument");

		// close speakers output
		speakersOutput.close();
		//if I close the wav here we won't hear anything in 'cause each passage in the while re-write 
		//over the existing sound in the wav (which is on some ms, so we won't hear anythnig)
	}

	public void startPlaying() {
		keepPlaying = true;
	}

	//for each instr, when it's finished, lets us hear the sound
	public void stopPlaying() {
		keepPlaying = false;
	}
		
	/**
	 * This method aims to produce the wav file containing the final sound
	 * i.e. the attack and let us here it through the speakers as well.
	 *<br /> It is called when the ser taps on the cube.
	 * @author valeh
	 */
	public void letSoundOut(){
		stopPlaying();
		try {
			wavFileOutput.close();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		
		byte[] finalPlay = play(44100,44100*2); //2s playing
		WavFileOutput finalWav = new WavFileOutput("Attack");
		finalWav.play(finalPlay);
		try {
			finalWav.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
	}
}
