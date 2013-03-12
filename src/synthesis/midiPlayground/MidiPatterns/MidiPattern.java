/**
 * 
 */
package synthesis.midiPlayground.MidiPatterns;

import java.util.ArrayList;

import synthesis.midiPlayground.DelayedMidiCommand;
import synthesis.midiPlayground.MidiCommand;

/**
 * @author allegrem
 *
 */
public abstract class MidiPattern {
//public class MidiPattern {

	protected ArrayList<DelayedMidiCommand> pattern = new ArrayList<DelayedMidiCommand>();
	protected int index = 0;

	/**
	 * 
	 */
	/*public MidiPattern() {
		super();
		pattern = new ArrayList<DelayedMidiCommand>();
		buildPattern();
	}*/
	
	
	
	//protected abstract void buildPattern();
	
	//pattern test
	protected abstract void buildPattern();
	
	
	public DelayedMidiCommand getNext() {
		DelayedMidiCommand c = pattern.get(index);
		index++;
		if (index == pattern.size())
			index = 0;
		return c;
	}
	
}
