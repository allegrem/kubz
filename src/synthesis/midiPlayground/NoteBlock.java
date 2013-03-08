package synthesis.midiPlayground;

import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;

public class NoteBlock implements MidiAudioBlock {
	
	private Float note = 0f;

	@Override
	public Float play(Float t) {
		return note;
	}

	@Override
	public Float phi(Float t) {
		return (float) (2 * Math.PI * note * t);
	}

	@Override
	public void command(MidiCommand command) {
		if (command.getCommand() == MidiCommand.NOTE_ON)
			note = noteToFreq(command.getParam1());
	}
	
	private Float noteToFreq(int note) {
		return (float) ((440f / 32f) * Math.pow(2, ((note - 9f) / 12f)));
	}
}
