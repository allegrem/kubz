package synthesis.midiPlayground;

import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;

public class VelocityBlock implements MidiAudioBlock {
	
	private Float velocity = 0f;

	@Override
	public Float play(Float t) {
		return velocity;
	}

	@Override
	public Float phi(Float t) {
		return (float) (2 * Math.PI * velocity * t);
	}

	@Override
	public void command(MidiCommand command) {
		if (command.getCommand() == MidiCommand.NOTE_ON)
			velocity = (float) command.getParam2();
		else if (command.getCommand() == MidiCommand.NOTE_OFF)
			velocity = 0f;
	}

}
