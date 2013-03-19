package monster;

import midisynthesis.Melody;
import midisynthesis.instruments.InstrumentLibrary;
import midisynthesis.patterns.MidiPatternsLibaray;

public class DefenceType {
	protected Melody melody;
	
	public DefenceType(){
		this.melody = new Melody();
		
		//generation aleatoire d'une melody
		int tune = (int) Math.random();
		int parameter = (int) Math.random();
		int tempo = (int) Math.random();
		int pattern = (int) Math.random();
		int instrum = (int) Math.random();
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
			melody.setPattern(MidiPatternsLibaray.getNextPattern(melody.getPattern()));
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}

	public Melody getMelody() {
		return melody;
	}

	public void setMelody(Melody melody) {
		this.melody = melody;
	}

}