package monster.attack;

/**
 * @author Felix
 */

import player.unit.*;
import synthesis.Sound;
import synthesis.fmInstruments.FmInstruments3Params;
import synthesis.fmInstruments.WoodInstrument;

import monster.zoo.Monster;

import views.attacks.SinusoidalAttackView;

public abstract class AttackType {
	protected Monster monster;
	protected Sound sound;
	protected int power = 100; // "distance" ou "puissance" de l'attaque codee
								// ici en dur

	public AttackType(Monster monster) {
		this.monster = monster;
		FmInstruments3Params instrument = WoodInstrument
				.getFmInstruments3Params();
		instrument.random();
		sound = new Sound(instrument, 3f);
	}


	public void attack(Unit unit) {
		if (monster.getCible() != null) {
			double oppose = -(monster.getCible().getX() - monster.getX());
			double adjacent = monster.getCible().getY() - monster.getY();
			SinusoidalAttackView attack = new SinusoidalAttackView(20, 180
					* Math.atan2(oppose, adjacent) / Math.PI, power,
					monster.getView());
			monster.getView().addChild(attack);
			int degats = sound
					.filter(monster.getCible().getOwner().getShield())
					.getDegats() / 30000000; // d�gats diminues sinon ca fait
												// trop
												// mal au joueur
			System.out.println(degats);
			//sound.playToSpeakers(); //A NE PAS ENLEVER QUAND JE FAIS MON NAZI SUR LES COMMENTAIRES
			if (power > monster.getPos()
					.distanceTo(monster.getCible().getPos())) {
				monster.getCible().decreaseLife(degats);
			}
		}

	}

}
