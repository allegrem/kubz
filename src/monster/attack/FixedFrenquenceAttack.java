package monster.attack;

/**
 * Monstre qui attaque tout le temps à la même fréquence de toute sa puissance
 * @author Felix
 */

import synthesis.Sound;
import synthesis.fmInstruments.FmInstruments3Params;
import synthesis.fmInstruments.TwoOscFmInstrument;

import monster.Monster;

public class FixedFrenquenceAttack extends AttackType {

	private int strenght;
	private final Sound sound;

	public FixedFrenquenceAttack(Monster monster, int strenght) {
		super(monster);
		this.strenght = strenght;
		
		FmInstruments3Params instrument = TwoOscFmInstrument.getFmInstruments3Params();
		instrument.random();
		sound = new Sound(instrument, 3f);
		
		//connerie pour les degats
		int degats = sound.filter(monster.getCible().getOwner().getShield()).getDegats();
	}

}
