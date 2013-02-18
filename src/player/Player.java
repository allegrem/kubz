package player;

/**
 * Classe qui represente un joueur, a des reference vers ses unites et parametres
 * @author Felix
 */

import monster.zoo.*;
import base.Base;
import gameEngine.GameEngine;
import OpenGL.KeyboardManager;
import player.parameter.*;
import player.unit.*;
import synthesis.Sound;
import synthesis.filters.BandsFilter;
import synthesis.fmInstruments.FmInstruments3Params;
import synthesis.fmInstruments.PianoInstrument2;
import synthesis.fmInstruments.TwoOscFmInstrument;
import synthesis.fmInstruments.WoodInstrument;
import synthesis.fmInstruments.XylophoneInstrument;
import views.attacks.AttackConeView;
import views.attacks.SinusoidalAttackView;
import views.informationViews.AudioRender;
import views.informationViews.InstrumentsChoice;

public class Player {

	private Unit unit;
	private Parameter[] parameters;
	private Base base;
	private int nParams = 2;
	private BandsFilter shield;
	private boolean isTurn;
	private int choice;
	private int lastAngle1;
	private int lastAngle2;
	private AudioRender audioRender;

	int power = 100;

	private GameEngine gameEngine;

	/**
	 * Creation d'un joueur avec une Unit et deux Parameter
	 */
	public Player(GameEngine gameEngine) {

		this.gameEngine = gameEngine;
		this.base = base;
		this.unit = new Unit(this);
		this.parameters = new Parameter[2];
		parameters[0] = new Parameter(this);
		parameters[1] = new Parameter(this);
		parameters[0].setLocation(100,100);
		parameters[1].setLocation(200,100);
		audioRender=new AudioRender(gameEngine.getDisplay(), unit.getSound(), parameters[0], parameters[1]);
		gameEngine.getMap().add(audioRender);
		this.shield = new BandsFilter(11);
		this.shield.random();
	}

	/**
	 * Methode qui retourne l'angle du premier cube parametre
	 * 
	 * @return
	 */
	public int getChangeAngle1() {
		int retour = lastAngle1 - parameters[0].getAngle();
		lastAngle1 = parameters[0].getAngle();
		return retour;
	}

	/**
	 * Methode retourne l'angle du second cube parametre
	 * 
	 * @return
	 */
	public int getChangeAngle2() {
		int retour = lastAngle2 - parameters[1].getAngle();
		lastAngle2 = parameters[1].getAngle();
		return retour;
	}

	/**
	 * methode qui retourne la distance entre les deux parametres
	 * 
	 * @return
	 */
	public int getChangeDistance() {
		return (int) parameters[0].getPos().distanceTo(parameters[1].getPos());
	}

	public BandsFilter getShield() {
		return shield;
	}

	public void setShield(BandsFilter shield) {
		this.shield = shield;
	}

	public Unit getUnit() {
		return unit;
	}

	public Parameter[] getParameters() {
		return parameters;
	}

	/**
	 * Methodes qui gerent l'etat des parametres
	 */

	public void setPStatesToFrozen() {
		this.parameters[0].setToFrozen();
		this.parameters[1].setToFrozen();
	}

	public void setPStatesToSoundEdit() {
		this.parameters[0].setToSoundEdit();
		this.parameters[1].setToSoundEdit();
	}

	public void setPStatesToWaiting() {
		this.parameters[0].setToWaiting();
		this.parameters[1].setToWaiting();
	}

	public void setPStatesToPositionError() {
		this.parameters[0].setToFrozen();
		this.parameters[1].setToFrozen();
	}

	/**
	 * retourne un tableau avec les ParamState des different Parameters
	 * 
	 * @return paramsState
	 */
	public ParameterState[] getStates() {
		ParameterState[] paramsState = { parameters[0].getState(),
				parameters[1].getState() };
		return paramsState;
	}

	/**
	 * Bloc des methodes oe on choisit l'etat de l'unite
	 */

	public void setUStateToAngle() {
		this.unit.setUStateToAngle();
	}

	public void setUStateToDirection() {
		this.unit.setUStateToDirection();
	}

	public void setUStateToFrozen() {
		this.unit.setUStateToFrozen();
	}

	public void setUStateToMoving() {
		this.unit.setUStateToMoving();
	}

	public void setUStateToPositionError() {
		this.unit.setUStateToPositionError();
	}

	public void setUStateToSelect() {
		this.unit.setUStateToSelect();
	}

	public void setUStateToWaiting() {
		this.unit.setUStateToWaiting();
	}

	public UnitState getState() {
		return unit.getState();
	}

	/**
	 * A present on va utiliser les methode du haut pour les differentes phases
	 * de jeu
	 */
	public void WaitingTurn() {
		setPStatesToWaiting();
		setUStateToWaiting();
	}

	/**
	 * Methode qui declenche le choix de l'Unit e utiliser
	 */
	public void choosingUTurn() {
		setPStatesToWaiting();
		setUStateToSelect();
		while (!KeyboardManager.tap) {

		}
	}

	/**
	 * Methode qui declenche le mouvement de Unit
	 */
	public void movingUTurn() {
		setPStatesToWaiting();
		setUStateToMoving();
		double size = unit.getSize() * Math.sqrt(2) / 2;
		while (!KeyboardManager.tap) {
			if ((KeyboardManager.zKey) && (unit.getY() - size > 0))
				unit.translate(0, -1);
			if ((KeyboardManager.sKey)
					&& (unit.getY() + size < gameEngine.getHeight()))
				unit.translate(0, 1);
			if ((KeyboardManager.qKey) && (unit.getX() - size > 0))
				unit.translate(-1, 0);
			if ((KeyboardManager.dKey)
					&& (unit.getX() + size < gameEngine.getWidth()))
				unit.translate(1, 0);
			if (KeyboardManager.wKey)
				unit.rotate(1);
			if (KeyboardManager.xKey)
				unit.rotate(-1);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		KeyboardManager.tap = false;
	}

	public void chooseWeaponTurn() {
		InstrumentsChoice instChoice = new InstrumentsChoice(gameEngine);
		unit.getView().addChild(instChoice);
		int fmChoice = 0;
		while (!KeyboardManager.tap) {

			if (unit.getInstrumentChoiceAngle() >= 0) {
				instChoice
						.setChosen((int) ((unit.getInstrumentChoiceAngle() % 360) / 90));
				fmChoice = (int) ((unit.getInstrumentChoiceAngle() % 360) / 90);
			} else {
				instChoice.setChosen((int) (((360 * (1 + Math.abs((int) ((unit
						.getInstrumentChoiceAngle() / 360)))) + unit
						.getInstrumentChoiceAngle()) % 360) / 90));
				fmChoice = (int) (((360 * (1 + Math.abs((int) ((unit
						.getInstrumentChoiceAngle() / 360)))) + unit
						.getInstrumentChoiceAngle()) % 360) / 90);

			}
			if (KeyboardManager.wKey)
				unit.rotateInstrumentChoice(1);
			if (KeyboardManager.xKey)
				unit.rotateInstrumentChoice(-1);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// modification de l'instrument en cours d'utilisation
		if (fmChoice == 0)
			unit.getSound().setInstrument(
					PianoInstrument2.getFmInstruments3Params());
		if (fmChoice == 1)
			unit.getSound().setInstrument(
					TwoOscFmInstrument.getFmInstruments3Params());
		if (fmChoice == 2)
			unit.getSound().setInstrument(
					WoodInstrument.getFmInstruments3Params());
		if (fmChoice == 3)
			unit.getSound().setInstrument(
					XylophoneInstrument.getFmInstruments3Params());
		unit.getView().removeChild(instChoice);
		KeyboardManager.tap = false;
	}

	/**
	 * Methode qui declenche la creation du son via les Parameter
	 */
	public void soundEditPTurn() {
		setPStatesToSoundEdit();
		setUStateToWaiting();
		double size = unit.getSize() * Math.sqrt(2) / 2;
		boolean isModified = true;
		while (!KeyboardManager.tap) {
			if ((KeyboardManager.zKey)
					&& (parameters[choice].getY() - size > 0)) {
				parameters[choice].translate(0, -1);
				isModified = true;
			}
			if ((KeyboardManager.sKey)
					&& (parameters[choice].getY() + size < (gameEngine.getHeight()))) {
				parameters[choice].translate(0, 1);
				isModified = true;
			}
			if ((KeyboardManager.qKey)
					&& (parameters[choice].getX() - size > (0))) {
				parameters[choice].translate(-1, 0);
				isModified = true;
			}
			if ((KeyboardManager.dKey)
					&& (parameters[choice].getX() + size < (gameEngine.getWidth()))) {
				parameters[choice].translate(1, 0);
				isModified = true;
			}
			if (KeyboardManager.wKey) {
				parameters[choice].rotate(1);
				isModified = true;
			}
			if (KeyboardManager.xKey) {
				parameters[choice].rotate(-1);
				isModified = true;
			}
			if (KeyboardManager.aKey) {
				unit.getSound().playToSpeakers();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (!(KeyboardManager.zKey || KeyboardManager.sKey
					|| KeyboardManager.qKey || KeyboardManager.dKey
					|| KeyboardManager.wKey || KeyboardManager.xKey)) {
				if (isModified) {
					FmInstruments3Params instrument = (FmInstruments3Params) unit
							.getSound().getInstrument();
					instrument.changeParams(getChangeAngle1(),
							getChangeAngle2(), getChangeDistance());
					isModified = false;
				}

			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		FmInstruments3Params instrument = (FmInstruments3Params) unit
				.getSound().getInstrument();
		instrument.changeParams(getChangeAngle1(), getChangeAngle2(),
				getChangeDistance());
		KeyboardManager.tap = false;
	}

	/**
	 * Methode qui declenche le choix de l'ouverture d'attaque de Unit
	 */
	public void UDirection() {
		setPStatesToWaiting();
		setUStateToDirection();
		AttackConeView attackCone = new AttackConeView(unit.getAperture(),
				unit.getDirection(), 100, unit.getView());
		unit.getView().addChild(attackCone);
		while (!KeyboardManager.tap) {
			if (KeyboardManager.wKey) {
				unit.rotateDirection(1);
			}
			if (KeyboardManager.xKey) {
				unit.rotateDirection(-1);
			}
			if (unit.getDirection() > 0) {
				attackCone.setDirection((long) unit.getDirection());
			} else {
				attackCone.setDirection((long) (360 + unit.getDirection()));
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		unit.getView().removeChild(attackCone);
		KeyboardManager.tap = false;
	}

	public void UAperture() {
		setPStatesToWaiting();
		setUStateToDirection();
		AttackConeView attackCone = new AttackConeView(unit.getAperture(),
				unit.getDirection(), 100, unit.getView());
		unit.getView().addChild(attackCone);
		while (!KeyboardManager.tap) {
			if (KeyboardManager.wKey && unit.getAperture() > 0) {
				unit.rotateAperture(-1);
				attackCone.setAperture(unit.getAperture());
			}
			if (KeyboardManager.xKey && unit.getAperture() < 360) {
				unit.rotateAperture(1);
				attackCone.setAperture(unit.getAperture());
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		unit.getView().removeChild(attackCone);
		KeyboardManager.tap = false;

	}

	public void UAttack() {
		SinusoidalAttackView attack = new SinusoidalAttackView(
				unit.getAperture(), unit.getDirection(), power, unit.getView());
		unit.getView().addChild(attack);
		Sound sound = unit.getSound();
		//unit.getSound().playToSpeakers(); //A NE PAS ENLEVER QUAND JE FAIS MON NAZI SUR LES COMMENTAIRES
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!gameEngine.getMonsterList().isEmpty()) {
			for (int i = 0; (i < gameEngine.getMonsterList().size())
					&& (gameEngine.getMonsterList().get(i) != null); i++) {
				Monster monster = gameEngine.getMonsterList().get(i);
				double xdiff = monster.getPos().getX() - unit.getPos().getX();
				double ydiff = monster.getPos().getY() - unit.getPos().getY();
				double theta = (180 * Math.atan2(ydiff, xdiff) / Math.PI) - 90;
				if ((monster.getPos().distanceTo(unit.getPos()) < power)
						&& (((unit.getDirection() - unit.getAperture() / 2) % 360 <= theta % 360) && ((unit
								.getDirection() + unit.getAperture() / 2) % 360 >= theta % 360))) {
					System.out.println("player :"
							+ sound.filter(monster.getDefence().getShield())
									.getDegats() / 30000000);
					monster.decreaseLife(sound.filter(
							monster.getDefence().getShield()).getDegats() / 30000000);

				}
				gameEngine.getMonsterList().trimToSize();
			}
		}
	}

	public void act() {
		isTurn = true;
		movingUTurn();
		chooseWeaponTurn();
		soundEditPTurn();
		UAperture();
		UDirection();
		UAttack();
		isTurn = false;

	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public int getnParams() {
		return nParams;
	}

}
