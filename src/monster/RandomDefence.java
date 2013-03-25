package monster;

/**
 * @author Felix
 * @author Paul
 * 
 */

import midisynthesis.instruments.InstrumentLibrary;
import midisynthesis.patterns.MidiPatternsLibrary;

public class RandomDefence extends DefenceType{

	public RandomDefence() {
		super();
		//generation aleatoire d'une melody
		int tune = (int) (40 +60*Math.random());
		int parameter = (int) (127*Math.random());
		int tempo = (int) (40 +120*Math.random());
		int pattern = (int) (10*Math.random());
		int instrum = (int) (12*Math.random());
		melody.setTune(tune);
		melody.setTempo(tempo);
		melody.setParameter(parameter);
		for(int i=0;i<instrum;i++)
			try {
				melody.setInstrument(InstrumentLibrary.getNextInstrument(melody.getInstrument()));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		for(int j=0;j<pattern;j++) 
			try {
			melody.setPattern(MidiPatternsLibrary.getNextPattern(melody.getPattern()));
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}