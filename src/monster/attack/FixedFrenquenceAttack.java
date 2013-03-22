package monster.attack;

/**
 * Monstre qui attaque tout le temps � la m�me fr�quence de toute sa puissance
 * @author Felix
 */

import midisynthesis.instruments.Instrument;
import midisynthesis.patterns.MidiPattern;
import monster.zoo.Monster;

public class FixedFrenquenceAttack extends AttackType {


	public FixedFrenquenceAttack(Monster monster, int tune, int tempo, int parameter, MidiPattern pattern, Instrument instrument) {
		super(monster);
		this.attackMelody.setTempo(tempo);
		this.attackMelody.setTune(tune);
		this.attackMelody.setParameter(parameter);
		this.attackMelody.setPattern(pattern);
		this.attackMelody.setInstrument(instrument);
	}
	
	

}
