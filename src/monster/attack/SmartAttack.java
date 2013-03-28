package monster.attack;

import midisynthesis.instruments.InstrumentLibrary;
import midisynthesis.patterns.MidiPatternsLibrary;
import monster.zoo.Monster;

public class SmartAttack extends AttackType {

	public SmartAttack(Monster monster) {
		super(monster);
		generateAttackMelody();
	}

	protected void generateAtackMelody() {
		if ((monster.getCible() != null)&&(monster.getCible()==previousTarget)) {
			if (attackMelody.getTempo() > monster.getCible().getDefenceMelody()
					.getTempo())
				attackMelody.setTempo(attackMelody.getTempo() - 10);
			else
				attackMelody.setTempo(attackMelody.getTempo() + 10);
			if (attackMelody.getTune() > monster.getCible().getDefenceMelody()
					.getTune())
				attackMelody.setTune(attackMelody.getTune() - 10);
			else
				attackMelody.setTempo(attackMelody.getTune() + 10);
			if (attackMelody.getParameter() > monster.getCible()
					.getDefenceMelody().getParameter())
				attackMelody.setParameter(attackMelody.getParameter() - 10);
			else
				attackMelody.setParameter(attackMelody.getParameter() + 10);
			if (attackMelody.getPattern() != monster.getCible()
					.getDefenceMelody().getPattern())
				try {
					attackMelody.setPattern(MidiPatternsLibrary
							.getNextPattern(attackMelody.getPattern()));
				} catch (InstantiationException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			if (attackMelody.getInstrument() != monster.getCible()
					.getDefenceMelody().getInstrument())
				try {
					attackMelody.setInstrument(InstrumentLibrary
							.getNextInstrument(attackMelody.getInstrument()));
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
		}
		if((monster.getCible() != null)&&(monster.getCible()!=previousTarget)){
			generateRandomAttackMelody();
		}
	}

}
