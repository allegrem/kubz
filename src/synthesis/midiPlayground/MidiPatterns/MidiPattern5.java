package synthesis.midiPlayground.MidiPatterns;

import java.util.ArrayList;

import synthesis.midiPlayground.DelayedMidiCommand;
import synthesis.midiPlayground.MidiCommand;

/**
 * 
 * @author valeh
 *
 */

//starting tune : 76 (E5)

public class MidiPattern5 extends MidiPattern{
	
	public MidiPattern5(){
		super();
		pattern = new ArrayList<DelayedMidiCommand>();
		buildPattern();		
	}

	@Override
	protected void buildPattern() {
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 1f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 1.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -1, 100), 1.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -1, 100), 2f));

		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 2f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 2.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -1, 100), 2.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -1, 100), 3f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 3f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 3.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -5, 100), 3.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -5, 100), 4f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -2, 100), 4f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -2, 100), 4.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -4, 100), 4.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -4, 100), 5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -7, 100), 5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -7, 100), 7f));
		
		
	}
	
	

}
