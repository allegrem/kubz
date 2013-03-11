/**
 * 
 */
package synthesis.midiPlayground;

import java.util.ArrayList;

/**
 * @author allegrem
 *
 */
//public abstract class MidiPattern {
public class MidiPattern {

	public final ArrayList<DelayedMidiCommand> pattern;
	public int index = 0;

	/**
	 * 
	 */
	public MidiPattern() {
		super();
		pattern = new ArrayList<DelayedMidiCommand>();
		buildPattern();
	}
	
	
	//protected abstract void buildPattern();
	
	//pattern test
	private void buildPattern() {
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 1f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 1.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 4, 100), 2f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 4, 100), 2.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 7, 100), 3f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 7, 100), 3.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 12, 100), 4f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 12, 100), 4.5f));
	}
	
	
	public DelayedMidiCommand getNext() {
		DelayedMidiCommand c = pattern.get(index);
		index++;
		if (index == pattern.size())
			index = 0;
		return c;
	}
	
}
