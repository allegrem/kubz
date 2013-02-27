package synthesis.midiPlayground;

import synthesis.AudioBlock;

/**
 * This interface extends the {@link AudioBlock} interface by adding a
 * {@link MidiAudioBlock#command(MidiCommand)}. This method can carry messages
 * through the {@link MidiAudioBlock}.
 * 
 * @author allegrem
 */
public interface MidiAudioBlock extends AudioBlock {

	/**
	 * The given command is handled by the {@link MidiAudioBlock} (note that
	 * some blocks do not react to some commands). Then the command is
	 * broadcasted to the blocks plugged in.
	 */
	public void command(MidiCommand command);

}
