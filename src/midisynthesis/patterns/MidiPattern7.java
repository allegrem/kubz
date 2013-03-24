package midisynthesis.patterns;

import java.util.ArrayList;

import midisynthesis.midicommand.DelayedMidiCommand;
import midisynthesis.midicommand.MidiCommand;

/**
 * 
 * @author valeh
 *
 */

//starting tune = 49 (E3)

public class MidiPattern7 extends MidiPattern {
	
	public MidiPattern7() {
		super();
		pattern = new ArrayList<DelayedMidiCommand>();
		buildPattern();
	}

	@Override
	protected void buildPattern() {
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 1f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 1.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 1, 100), 1.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 1, 100), 2f));

		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 2, 100), 2f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 2, 100), 3f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 1, 100), 3f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 1, 100), 3.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 3.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 4f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 1, 100), 4f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 1, 100), 5f));
		
	
	}
	
	

}
