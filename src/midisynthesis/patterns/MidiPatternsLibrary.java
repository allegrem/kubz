package midisynthesis.patterns;

import java.util.ArrayList;


public class MidiPatternsLibrary {

	private static final ArrayList<Class<?>> patterns;    
    static {
    	patterns = new ArrayList<Class<?>>();
    	patterns.add(MidiPattern1.class);
    	patterns.add(MidiPattern2.class);
    	patterns.add(MidiPattern3.class);
    	patterns.add(MidiPattern4.class);
    	patterns.add(MidiPattern5.class);
    	patterns.add(MidiPattern6.class);
    	patterns.add(MidiPattern7.class);
    }
	
	public static MidiPattern getNextPattern(MidiPattern i) throws InstantiationException, IllegalAccessException {
		boolean next = false;
		for(Class<?> c : patterns) {
			if (next)
				return (MidiPattern) c.newInstance();
			if (i.getClass() == c)
				next = true;
		}
		if (next) //return the first if the last was the one
			return (MidiPattern) patterns.get(0).newInstance();
		else
			return null;
	}
	
	public static MidiPattern getPreviousPattern(MidiPattern i) throws InstantiationException, IllegalAccessException {
		Class<?> prev = null;
		for(Class<?> c : patterns) {
			if (i.getClass() == c) {
				if(prev == null) //return the last if the first is the one
					return (MidiPattern) patterns.get(patterns.size()-1).newInstance();
				else
					return (MidiPattern) prev.newInstance();
			}
			prev = c;
		}
		return null;
	}
	
}
