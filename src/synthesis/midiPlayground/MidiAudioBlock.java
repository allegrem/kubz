package synthesis.midiPlayground;

import synthesis.AudioBlock;

public interface MidiAudioBlock extends AudioBlock {
	
	public void command(MidiCommand command);

}
