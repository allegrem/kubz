package synthesis.midiPlayground.MidiPatterns;

import java.util.ArrayList;
import synthesis.midiPlayground.*;


/**
 * 
 * @author valeh
 *
 */

//starting tune : 77 (F5)

public class MidiPattern2 extends MidiPattern{
	
	public  MidiPattern2(){
		super();
		pattern = new ArrayList<DelayedMidiCommand>();
		buildPattern();
	}

	@Override
	protected void buildPattern() {
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 1f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 1.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -1, 100), 2f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -1, 100), 2.5f));

		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 3f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 3.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -1, 100), 4f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -1, 100), 4.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 5.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -5, 100), 6f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -5, 100), 6.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -5, 100), 7f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -5, 100), 7.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -5, 100), 8f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -5, 100), 8.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -5, 100), 9f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -5, 100), 9.5f));

		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -1, 100), 10f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -1, 100), 10.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 11f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 11.5f));

		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -1, 100), 12f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -1, 100), 12.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 13f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 13.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -3, 100), 14f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -3, 100), 14.5f));
		
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, -3, 100), 15f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, -3, 100), 15.5f));
		
	}

}
