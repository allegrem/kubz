package midisynthesis.instruments;

import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.midicommand.MidiCommand;


public class SubInstrument {

	private final MidiAudioBlock out;

	private int note = 0;

	private float t = 0;

	/**
	 * @param out
	 */
	public SubInstrument(MidiAudioBlock out) {
		super();
		this.out = out;
	}

	public int getNote() {
		return note;
	}

	public void command(MidiCommand command) {
		if (command.getCommand() == MidiCommand.NOTE_ON) {
			note = command.getParam1();
			t = 0;
		} else if (command.getCommand() == MidiCommand.NOTE_OFF)
			note = 0;
		else if (command.getCommand() == MidiCommand.CHANNEL_MODE_MESSAGE
				&& command.getParam1() == MidiCommand.CHANNEL_MODE_MESSAGE_ALL_NOTES_OFF) {
			//all sound off: call again command() with the right note off message
			command(new MidiCommand(MidiCommand.NOTE_OFF, note, 0));
		}
		out.command(command);
		//System.out.println(command);
	}

	public byte playNextSample(int sampleRate) {
		t += 1f / (float) sampleRate;
		return out.play(t).byteValue();
	}

}
