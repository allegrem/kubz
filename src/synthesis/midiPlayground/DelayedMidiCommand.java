/**
 * 
 */
package synthesis.midiPlayground;

/**
 * @author allegrem
 * 
 */
public class DelayedMidiCommand {

	public final MidiCommand midiCommand;

	public final Float delay;

	/**
	 * @param midiCommand2
	 * @param delay
	 */
	public DelayedMidiCommand(MidiCommand midiCommand2, Float delay) {
		super();
		this.midiCommand = midiCommand2;
		this.delay = delay;
	}

	/**
	 * @return the midiCommand
	 */
	public MidiCommand getMidiCommand(int tune) {
		int param1 = midiCommand.getParam1();
		if (midiCommand.getCommand() == MidiCommand.NOTE_ON
				|| midiCommand.getCommand() == MidiCommand.NOTE_OFF)
			param1 += tune;
		return new MidiCommand(midiCommand.getCommand(), param1, midiCommand.getParam2());
	}

	/**
	 * @return the delay
	 */
	public Float getDelay() {
		return delay;
	}

	public Float getDelayInSeconds(int tempo) {
		return delay * 60f / (float) tempo;
	}
}
