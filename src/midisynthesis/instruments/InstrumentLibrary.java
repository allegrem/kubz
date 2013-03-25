package midisynthesis.instruments;

import java.util.ArrayList;

public class InstrumentLibrary {

	private static final ArrayList<Class<?>> instruments;    
    static {
    	instruments = new ArrayList<Class<?>>();
    	instruments.add(Bell.class);
    	instruments.add(TwoOscInstrument.class);
    	instruments.add(WoodInstrument.class);
    	instruments.add(Xylophone.class);
    	instruments.add(GhostSinus.class);
    }
	
	public static Instrument getNextInstrument(Instrument i) throws InstantiationException, IllegalAccessException {
		boolean next = false;
		for(Class<?> c : instruments) {
			if (next)
				return (Instrument) c.newInstance();
			if (i.getClass() == c)
				next = true;
		}
		if (next) //return the first if the last was the one
			return (Instrument) instruments.get(0).newInstance();
		else
			return null;
	}
	
	public static Instrument getPreviousInstrument(Instrument i) throws InstantiationException, IllegalAccessException {
		Class<?> prev = null;
		for(Class<?> c : instruments) {
			if (i.getClass() == c) {
				if(prev == null) //return the last if the first is the one
					return (Instrument) instruments.get(instruments.size()-1).newInstance();
				else
					return (Instrument) prev.newInstance();
			}
			prev = c;
		}
		return null;
	}
	
}
