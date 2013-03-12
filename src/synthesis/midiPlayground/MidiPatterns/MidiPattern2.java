package synthesis.midiPlayground.MidiPatterns;

import java.util.ArrayList;
import synthesis.midiPlayground.*;
import synthesis.midiPlayground.DelayedMidiCommand;

public class MidiPattern2 extends MidiPattern{
	
	public  MidiPattern2(){
		super();
		pattern = new ArrayList<DelayedMidiCommand>();
		buildPattern();
	}

	@Override
	protected void buildPattern() {
		
		
	}

}
