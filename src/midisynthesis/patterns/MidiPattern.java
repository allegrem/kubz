/**
 * 
 */
package midisynthesis.patterns;

import java.util.ArrayList;

import midisynthesis.midicommand.DelayedMidiCommand;


/**
 * @author allegrem
 *
 */
public abstract class MidiPattern {

	protected ArrayList<DelayedMidiCommand> pattern = null;
	protected int index = 0;

	public MidiPattern() {
		super();
		pattern = new ArrayList<DelayedMidiCommand>();
		buildPattern();
	}
	
	
	
	protected abstract void buildPattern();
	
	
	public DelayedMidiCommand getNext() {
		DelayedMidiCommand c = pattern.get(index);
		index++;
		if (index == pattern.size())
			index = 0;
		return c;
	}
	
}
