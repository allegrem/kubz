package player;

/**
 * Classe qui represente un joueur, a des reference vers ses unites et parametres
 * 
 * Nouvelle version (jouable avec les 5 parametres)
 * 
 * @author Felix
 */

import java.util.ArrayList;

import midisynthesis.Melody;
import midisynthesis.instruments.InstrumentLibrary;
import midisynthesis.patterns.MidiPatternsLibaray;
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
import utilities.Point;
import views.attacks.AttackConeView;
import views.attacks.SinusoidalAttackView;
import views.informationViews.AudioRender;
import views.informationViews.InstrumentsChoice;
import views.informationViews.Link;

public class Player{

	private ArrayList<Unit> unitList;
	private Parameter[] parameters;
	private Base base;
	private int nParams = 2;
	private boolean isTurn;
	private int choice;
	private int lastAngle1;
	private int compteurInstr;
	private int lastAngle2;
	private int compteurPattern;


	int power = 100;

	private GameEngine gameEngine;
	private AudioRender audioRender;
	private Link link;

	/**
	 * Creation d'un joueur avec deux Unit et deux Parameter
	 */
	public Player(GameEngine gameEngine, Base base) {
		
		this.gameEngine = gameEngine;
		this.base = base;
		unitList = new ArrayList<Unit>();
		unitList.add(new Unit(this));
		this.parameters = new Parameter[2];
		parameters[0] = new Parameter(this);
		parameters[1] = new Parameter(this);
		parameters[0].setLocation(
				base.getCenter().getX() - parameters[0].getSize(), base
						.getCenter().getY());
		parameters[1].setLocation(
				base.getCenter().getX() + parameters[1].getSize(), base
						.getCenter().getY());
		link=new Link(base,parameters[0],parameters[1]);
		gameEngine.getMap().add(link);
		compteurPattern = 0;
		compteurInstr = 0;
		lastAngle1 = (int) parameters[0].getAngle();
		lastAngle2 = (int) parameters[1].getAngle();
		
	}

	/**
	 * Methode qui retourne le nombre de changement d'instrument, en rapport avec l'angle de Parameter1
	 * Pour le choix de l'instrument.
	 * @return
	 */
	public int getChangeInstrument() {
		int sensibility = 45;
		compteurInstr = parameters[0].getAngle() - lastAngle1;
		lastAngle1 = parameters[0].getAngle();
		int changeInstrument = Math.round(compteurInstr/sensibility);
		compteurInstr = compteurInstr - changeInstrument*sensibility;
		return changeInstrument;
	}

	/**
	 * Methode retourne l'angle du second cube parametre
	 * Pour le pattern de la melody (paramètre plus ou moins discret)
	 * @return
	 */
	public int getChangePattern() {
		int sensibility = 45;
		compteurPattern = parameters[1].getAngle() - lastAngle2;
		lastAngle2 = parameters[1].getAngle();
		int changePattern = Math.round(compteurPattern/sensibility);
		compteurPattern = compteurPattern - changePattern*sensibility;
		return changePattern;
	}

	/**
	 * methode qui retourne la distance entre le cube Parameter1 et le centre de la base
	 *  Pour le paramètre d'instrument.
	 * @return
	 */
	public int getCube1Distance() {
		int sensibilite = 1;
		int offset = 0;
		return (int) (offset + sensibilite*parameters[0].getPos().distanceTo(base.getCenter()));
	}
	
	/**
	 * methode qui retourne la distance entre le cube Parameter2 et le centre de la base
	 * Pour la note de depart
	 * @return
	 */
	public int getCube2Distance() {
		int sensibilite = 1;
		int offset = 0;
		return (int) (offset + sensibilite*parameters[1].getPos().distanceTo(base.getCenter()));
	}
	
	
	/**
	 * Methode qui permet de déterminet l'angle entre les deux cubes Parameter
	 * Pour le Tempo
	 * @return
	 */
	public int getCubesAperture() {
		int sensibility = 1;
		int offset = 0;
		double dx1 = parameters[0].getX() - base.getCenter().getX();
		double dy1 = parameters[0].getY() - base.getCenter().getY();
		double dx2 = parameters[1].getX() - base.getCenter().getX();
		double dy2 = parameters[1].getY() - base.getCenter().getY();
		double scalar = dx1*dx2 +dy1*dy2;
		double n1 = dx1*dx1 + dy1*dy1;
		double n2 = dx2*dx2 + dy2*dy2;
		double theta = 180*Math.acos(scalar/(n1*n2))/Math.PI;
		return (int) ((int) sensibility*theta+offset) ;
	}

	public void removeUnit(Unit unit) {
		gameEngine.getUnitList().remove(unit);
		unit.getView().removeChild(unit.getLifeView());
		gameEngine.getMap().remove(unit.getView());
		unit.getView().setUnTracked(false);
		unitList.remove(unit);
		if (unitList.isEmpty()) {
			gameEngine.removePlayer(this);
		}
	}


	public ArrayList<Unit> getUnitList() {
		return unitList;
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

	public void setUStateToAngle(Unit unit) {
		unit.setUStateToAngle();
	}

	public void setUStateToDirection(Unit unit) {
		unit.setUStateToDirection();
	}

	public void setUStateToFrozen(Unit unit) {
		unit.setUStateToFrozen();
	}

	public void setUStateToMoving(Unit unit) {
		unit.setUStateToMoving();
	}

	public void setUStateToPositionError(Unit unit) {
		unit.setUStateToPositionError();
	}

	public void setUStateToSelect(Unit unit) {
		unit.setUStateToSelect();
	}

	public void setUStateToWaiting(Unit unit) {
		unit.setUStateToWaiting();
	}

	public UnitState getState(Unit unit) {
		return unit.getState();
	}

	/**
	 * A present on va utiliser les methode du haut pour les differentes phases
	 * de jeu
	 */

	public void WaitingTurn() {
		setPStatesToWaiting();
		for (Unit unit : unitList) {
			setUStateToWaiting(unit);
		}
	}

	/**
	 * Methode qui declenche le choix de l'Unit e utiliser
	 */
	public void choosingUTurn() {
		setPStatesToWaiting();
		for (Unit unit : unitList) {
			setUStateToSelect(unit);
		}
		while (!KeyboardManager.tap) {

		}
	}

	/**
	 * Methode qui declenche le mouvement de Unit
	 */
	public void movingUTurn(Unit unit) {
		setPStatesToWaiting();
		setUStateToMoving(unit);
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

	/*public void chooseWeaponTurn(Unit unit) {
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
		unit.getView().removeChild(instChoice);
		KeyboardManager.tap = false;
	}*/

	/**
	 * Methode qui declenche la creation du son via les Parameter Ici les
	 * Parameters sont liees au Player
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	/*=============>>>> a modifier pour que le parametre ne puisse pas sortir
	 * de la base (Demi-cercle et pas cercle ! Donc peut sortir d'un cote...)
	 * 
	 */
	public void soundEditPTurn(Unit unit) throws InstantiationException, IllegalAccessException {
		Melody melody = unit.getAttackMelody();
		setPStatesToSoundEdit();
		setUStateToWaiting(unit);
		double size = unit.getSize() * Math.sqrt(2) / 2;
		boolean isModified = true;
		while (!KeyboardManager.tap) {
			if ((KeyboardManager.zKey)
					&& (base.getCenter().distanceTo(new Point(parameters[choice].getX(),parameters[choice].getY()-1))<Base.radius)) {
				parameters[choice].translate(0, -1);
				isModified = true;
			}
			if ((KeyboardManager.sKey)
					&& (base.getCenter().distanceTo(new Point(parameters[choice].getX(),parameters[choice].getY()+1))<Base.radius)) {
				parameters[choice].translate(0, 1);
				isModified = true;
			}
			if ((KeyboardManager.qKey)
					&& (base.getCenter().distanceTo(new Point(parameters[choice].getX()-1,parameters[choice].getY()))<Base.radius)) {
				parameters[choice].translate(-1, 0);
				isModified = true;
			}
			if ((KeyboardManager.dKey)
					&& (base.getCenter().distanceTo(new Point(parameters[choice].getX()+1,parameters[choice].getY()))<Base.radius)) {
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
				if (melody.isPlaying())
					melody.pause();
				else
					melody.unpause();
			}
			if (!(KeyboardManager.zKey || KeyboardManager.sKey
					|| KeyboardManager.qKey || KeyboardManager.dKey
					|| KeyboardManager.wKey || KeyboardManager.xKey)) {
				if (isModified) {
					//on modifie l'instrument en fonctionde l'angle du Parameter1
					int iterInstrum = getChangeInstrument();
					//on passe a l'intrument suivant autant de fois que necessaire
					if(iterInstrum>0)
						for(int i=0;i<iterInstrum;i++){
							try {
								melody.setInstrument(InstrumentLibrary
										.getNextInstrument(melody.getInstrument()));
							} catch (InstantiationException | IllegalAccessException e1) {
								e1.printStackTrace();
							}
						}
					//on passe a l'intrument precedent autant de fois que necessaire
					if(iterInstrum<0)
						for(int i=0;i<-iterInstrum;i++){
							try {
								melody.setInstrument(InstrumentLibrary
										.getPreviousInstrument(melody.getInstrument()));
							} catch (InstantiationException | IllegalAccessException e1) {
								e1.printStackTrace();
							}
						}
					
					//on modifie le Pattern en fonction de l'angle du Parameter2
					int iterPattern = getChangePattern();
					//on passe au Pattern suivant autant de fois que necessaire
					if(iterPattern>0)
						for(int i=0;i<iterPattern;i++){
							try {
								melody.setPattern(MidiPatternsLibaray
										.getNextPattern(melody.getPattern()));
							} catch (InstantiationException | IllegalAccessException e1) {
								e1.printStackTrace();
							}
						}
					//on passe au Pattern precedent autant de fois que necessaire
					if(iterPattern<0)
						for(int i=0;i<-iterPattern;i++){
							try {
								melody.setPattern(MidiPatternsLibaray
										.getPreviousPattern(melody.getPattern()));
							} catch (InstantiationException | IllegalAccessException e1) {
								e1.printStackTrace();
							}
						}
					
					//On regle le parametre d'instrument
					melody.setParameter(getCube1Distance());
					
					//on regle la note de depart
					melody.setTune(getCube2Distance());
					
					//on regle le tempo
					melody.setTempo(getCubesAperture());
						
					isModified = false;
					
				}

			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//zik
		KeyboardManager.tap = false;
		unit.setAttackMelody(melody);
	}

	/**
	 * Methode qui declenche le choix de l'ouverture d'attaque de Unit
	 */


	public void UDirection(Unit unit) {
		setPStatesToWaiting();
		setUStateToDirection(unit);
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

	public void UAperture(Unit unit) {
		setPStatesToWaiting();
		setUStateToDirection(unit);
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

	/*public void UAttack(Unit unit) {
		SinusoidalAttackView attack = new SinusoidalAttackView(
				unit.getAperture(), unit.getDirection(), power, unit.getView());
		unit.getView().addChild(attack);

		//unit.getSound().playToSpeakers();
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
	}*/

	public void act() throws InstantiationException, IllegalAccessException {
		isTurn = true;
		for (Unit unit : unitList) {
			//audioRender.setSound(unit.getSound());
			movingUTurn(unit);
			//chooseWeaponTurn(unit);
			soundEditPTurn(unit);
			UAperture(unit);
			UDirection(unit);
			//UAttack(unit);
		}
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

	public boolean isInMap(Point newPos) {
		if (newPos.getX() < 0)
			return false;
		if (newPos.getX() > gameEngine.getWidth())
			return false;
		if (newPos.getY() < 0)
			return false;
		if (newPos.getY() > gameEngine.getHeight())
			return false;
		return true;
	}

	public boolean isInBase(Point newPos) {
		if (newPos.distanceTo(base.getCenter()) < base.radius)
			return true;
		return false;
	}

}
