package synthesis.midiPlayground;

import java.io.IOException;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.exceptions.AudioException;

public abstract class MidiInstrument extends Thread {

	private static final int MAX_SIMULTANEOUS_NOTES = 10;

	private final SubMidiInstrument[] subInstruments;

	private int lastSubInstrumentIndex;

	private boolean keepPlaying = true;

	public MidiInstrument() {
		subInstruments = new SubMidiInstrument[MAX_SIMULTANEOUS_NOTES];
		lastSubInstrumentIndex = MAX_SIMULTANEOUS_NOTES - 1;
		for (int i = 0; i < subInstruments.length; i++)
			subInstruments[i] = new SubMidiInstrument(buildInstrument());
	}

	protected abstract MidiAudioBlock buildInstrument();

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
			for (int s = 0; s < samples; s++) // normalize the output level
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
	}

	public void startPlaying() {
		keepPlaying = true;
	}

	public void stopPlaying() {
		keepPlaying = false;
	}

}
