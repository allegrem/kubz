package monster.attack;

import midisynthesis.instruments.InstrumentLibrary;
import midisynthesis.patterns.MidiPatternsLibrary;
import monster.zoo.Monster;

public class SmartAttack extends AttackType {

	public SmartAttack(Monster monster) {
		super(monster);
		generateAttackMelody();
	}

	@Override
	/**
	 * Le monstre va essayer de se rapprocher de la melody de la cible, ici pour les 
	 * parametre Tune, Tempo et Parameter(=timbre) on implémente un correcteur proportionel de gain 0.5
	 */
	protected void generateAttackMelody() {
		if ((monster.getCible() != null)
				&& (monster.getCible() == previousTarget)) {
			attackMelody.setTempo(attackMelody.getTempo()
					- (int) 0.5
					* (attackMelody.getTempo() - monster.getCible()
							.getDefenceMelody().getTempo()));
			attackMelody.setTune(attackMelody.getTune()
					- (int) 0.5
					* (attackMelody.getTune() - monster.getCible()
							.getDefenceMelody().getTune()));
			attackMelody.setParameter(attackMelody.getParameter()
					- (int) 0.5
					* (attackMelody.getParameter() - monster.getCible()
							.getDefenceMelody().getParameter()));
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
		if ((monster.getCible() != null)
				&& (monster.getCible() != previousTarget)) {
			generateRandomAttackMelody();
		}
	}

}
