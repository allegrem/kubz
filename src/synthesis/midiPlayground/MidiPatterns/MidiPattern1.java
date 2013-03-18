package synthesis.midiPlayground.MidiPatterns;

import java.util.ArrayList;

import synthesis.midiPlayground.DelayedMidiCommand;
import synthesis.midiPlayground.*;



public class MidiPattern1 extends MidiPattern{
	
	public MidiPattern1(){
		super();
		pattern = new ArrayList<DelayedMidiCommand>();
		buildPattern();
	}
	
	@Override
	protected void buildPattern(){
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 0, 100), 1f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 0, 100), 1.5f));			
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 4, 100), 2f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 4, 100), 2.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 7, 100), 3f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 7, 100), 3.5f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_ON, 12, 100), 4f));
		pattern.add(new DelayedMidiCommand(new MidiCommand(MidiCommand.NOTE_OFF, 12, 100), 4.5f));
	}

	
}
