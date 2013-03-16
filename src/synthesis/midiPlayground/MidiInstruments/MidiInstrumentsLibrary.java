package synthesis.midiPlayground.MidiInstruments;

import java.util.ArrayList;

public class MidiInstrumentsLibrary {

	private static final ArrayList<Class<?>> instruments;    
    static {
    	instruments = new ArrayList<Class<?>>();
    	instruments.add(MidiBellInstrument.class);
    	instruments.add(MidiTwoOscInstrument.class);
    	instruments.add(MidiWoodInstrument.class);
    	instruments.add(SinusInstrument.class);
    }
	
	public static MidiInstrument getNextInstrument(MidiInstrument i) throws InstantiationException, IllegalAccessException {
		boolean next = false;
		for(Class<?> c : instruments) {
			if (next)
				return (MidiInstrument) c.newInstance();
			if (i.getClass() == c)
				next = true;
		}
		if (next) //return the first if the last was the one
			return (MidiInstrument) instruments.get(0).newInstance();
		else
			return null;
	}
	
	public static MidiInstrument getPreviousInstrument(MidiInstrument i) throws InstantiationException, IllegalAccessException {
		Class<?> prev = null;
		for(Class<?> c : instruments) {
			if (i.getClass() == c) {
				if(prev == null) //return the last if the first is the one
					return (MidiInstrument) instruments.get(instruments.size()-1).newInstance();
				else
					return (MidiInstrument) prev.newInstance();
			}
			prev = c;
		}
		return null;
	}
	
}
