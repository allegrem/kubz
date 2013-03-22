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
import utilities.Point;
import views.attacks.AttackConeView;
import views.attacks.SinusoidalAttackView;
import views.informationViews.Link;
import views.interfaces.Displayable;
import views.interfaces.DisplayableFather;
import views.staticViews.BaseView;

public class Player {

	private ArrayList<Unit> unitList;
	private final Parameter[] parameters;
	private Base base;
	private final int nParams = 2;
	private boolean isTurn;
	private int choice;
	private int lastAngle1;
	private int compteurInstr;
	private int lastAngle2;
	private int compteurPattern;
	private final int unitid;
	private final int param1id;
	private final int param2id;
	private final double factor = 4;

	private final int power = 180;

	private final GameEngine gameEngine;

	private Link link;

	/**
	 * Creation d'un joueur avec deux Unit et deux Parameter
	 */
	public Player(GameEngine gameEngine, Base base, int unitid, int param1id,
			int param2id) {

		this.gameEngine = gameEngine;
		this.base = base;
		unitList = new ArrayList<Unit>();
		unitList.add(new Unit(this));
		this.parameters = new Parameter[2];
		parameters[0] = new Parameter(this);
		parameters[1] = new Parameter(this);
		
		if (base.getSens()==BaseView.BAS){
			parameters[0].setLocation(
					base.getCenter().getX() - parameters[0].getSize(), base
							.getCenter().getY() - parameters[0].getSize());
			parameters[1].setLocation(
					base.getCenter().getX() + parameters[1].getSize(), base
							.getCenter().getY() - parameters[0].getSize());
			unitList.get(0).setLocation((int) (base.getCenter().getX()) , (int) (base.getCenter().getY()-1.2*base.radius));
		}
		if (base.getSens()==BaseView.HAUT){
			parameters[0].setLocation(
					base.getCenter().getX() + parameters[0].getSize(), base
							.getCenter().getY()-parameters[0].getSize());
			parameters[1].setLocation(
					base.getCenter().getX() - parameters[1].getSize(), base
							.getCenter().getY()-parameters[0].getSize());
			unitList.get(0).setLocation((int) (base.getCenter().getX()) , (int) (base.getCenter().getY()+1.2*base.radius));
		}
		if (base.getSens()==BaseView.GAUCHE){
			parameters[0].setLocation(
					base.getCenter().getX() + parameters[0].getSize(), base
							.getCenter().getY() - parameters[0].getSize());
			parameters[1].setLocation(
					base.getCenter().getX() + parameters[1].getSize(), base
							.getCenter().getY() + parameters[0].getSize());
			unitList.get(0).setLocation((int) (base.getCenter().getX()+1.2*base.radius) , (int) (base.getCenter().getY()));
		}
		if (base.getSens()==BaseView.DROITE){
			parameters[0].setLocation(
					base.getCenter().getX() - parameters[0].getSize(), base
							.getCenter().getY() - parameters[0].getSize());
			parameters[1].setLocation(
					base.getCenter().getX() - parameters[1].getSize(), base
							.getCenter().getY() + parameters[0].getSize());
			unitList.get(0).setLocation((int) (base.getCenter().getX()-1.2*base.radius) , (int) (base.getCenter().getY()));
		}
	
		link = new Link(base, parameters[0], parameters[1]);
		gameEngine.getMap().add(link);
		compteurPattern = 0;
		compteurInstr = 0;
		lastAngle1 = (int) parameters[0].getAngle();
		lastAngle2 = (int) parameters[1].getAngle();
		this.param1id = param1id;
		this.param2id = param2id;
		this.unitid = unitid;
//		gameEngine.getCubeManager().getCube(unitid)
//				.setRGB(0, 0, 255, (short) 10);
//		gameEngine.getCubeManager().getCube(param1id)
//				.setRGB(0, 255, 0, (short) 10);
//		gameEngine.getCubeManager().getCube(param2id)
//				.setRGB(0, 255, 0, (short) 10);

	}
	

	/**
	 * Methode qui retourne le nombre de changement d'instrument, en rapport
	 * avec l'angle de Parameter1 Pour le choix de l'instrument.
	 * 
	 * @return
	 */
	public int getChangeInstrument() {
		int sensibility = 45;
		compteurInstr = parameters[0].getAngle() - lastAngle1;
		lastAngle1 = parameters[0].getAngle();
		int changeInstrument = Math.round(compteurInstr / sensibility);
		compteurInstr = compteurInstr - changeInstrument * sensibility;
		return changeInstrument;
	}

	/**
	 * Methode retourne l'angle du second cube parametre Pour le pattern de la
	 * melody (paramï¿½tre plus ou moins discret)
	 * 
	 * @return
	 */
	public int getChangePattern() {
		int sensibility = 45;
		compteurPattern = parameters[1].getAngle() - lastAngle2;
		lastAngle2 = parameters[1].getAngle();
		int changePattern = Math.round(compteurPattern / sensibility);
		compteurPattern = compteurPattern - changePattern * sensibility;
		return changePattern;
	}

	/**
	 * methode qui retourne la distance entre le cube Parameter1 et le centre de
	 * la base Pour le paramï¿½tre d'instrument.
	 * 
	 * @return
	 */
	
	// va de 15 a 80
	public int getCube1Distance() {
		int sensibilite = 1;
		int offset = 0;
		return (int) (offset + sensibilite
				* parameters[0].getPos().distanceTo(base.getCenter()));
	}

	/**
	 * methode qui retourne la distance entre le cube Parameter2 et le centre de
	 * la base Pour la note de depart
	 * 
	 * @return
	 */
	
	// va de 15 a 80
	public int getCube2Distance() {
		int sensibilite = 1;
		int offset = 0;
		return (int) (offset + sensibilite
				* parameters[1].getPos().distanceTo(base.getCenter()));
	}

	/**
	 * Methode qui permet de dï¿½terminet l'angle entre les deux cubes Parameter
	 * Pour le Tempo
	 * 
	 * @return
	 */
	
	// va de 40 à 140 à peu près
	public int getCubesAperture() {
		int sensibility = 1;
		int offset = 0;
		double dx1 = parameters[0].getX() - base.getCenter().getX();
		double dy1 = parameters[0].getY() - base.getCenter().getY();
		double dx2 = parameters[1].getX() - base.getCenter().getX();
		double dy2 = parameters[1].getY() - base.getCenter().getY();
		double scalar = dx1 * dx2 + dy1 * dy2;
		double n1 = dx1 * dx1 + dy1 * dy1;
		double n2 = dx2 * dx2 + dy2 * dy2;
		double theta = 180 * Math.acos(scalar / (n1 * n2)) / Math.PI;
		return (int) ((int) sensibility * theta + offset);
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
		int i = 0;
		int j = 0;
		boolean collision;
		DisplayableFather view = unit.getView();
		float viewSize = (float) (view.getSize() / 2);
		while (!KeyboardManager.tap) {
//			unit.setDirection(-gameEngine.getCubeManager().getCube(unitid)
//					.getAngle() / factor);
			i = 0;
			j = 0;
			if ((KeyboardManager.zKey) && (unit.getY() - size > 0))
				j = -1;
			if ((KeyboardManager.sKey)
					&& (unit.getY() + size < gameEngine.getHeight()))
				j = 1;
			if ((KeyboardManager.qKey) && (unit.getX() - size > 0))
				i = -1;
			if ((KeyboardManager.dKey)
					&& (unit.getX() + size < gameEngine.getWidth()))
				i = 1;
			collision = false;
			for (Displayable obj : gameEngine.getMap().getObjects()) {
				if (obj != view
						&& obj.isInZone(new Point(view.getX() + i * viewSize,
								view.getY() + j * viewSize))) {
					collision = true;
					//System.out.println(obj);
				}

			}
			if (!collision)
				unit.translate(i, j);

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
		unit.updtaeSeenMonsters();
		KeyboardManager.tap = false;
	}
	
	

	public void UDirection(Unit unit) {
		setPStatesToWaiting();
		setUStateToDirection(unit);
		unit.updtaeSeenMonsters();
		ArrayList<Monster> seenMonsters = unit.getSeenMonsters();
//		AttackConeView attackCone = new AttackConeView(30, unit.getDirection(),
//				power, unit.getView());
//		unit.getView().addChild(attackCone);
		if(seenMonsters.size()>0){
			unit.setPreviousTarget(seenMonsters.get(0));
			unit.setTarget(seenMonsters.get(0));
			AttackConeView cibleView =  new AttackConeView(360, 0, 40, unit.getTarget().getView());
			unit.getTarget().getView().addChild(cibleView);
			unit.getTarget().getDefence().getMelody().unpause();
			while (!KeyboardManager.tap) {
				unit.updateTarget();		
				if(!(unit.getPreviousTarget().equals(unit.getTarget()))){
					unit.getPreviousTarget().getDefence().getMelody().pause();
					unit.getPreviousTarget().getView().removeChild(cibleView);
					cibleView =  new AttackConeView(360, 0, 40, unit.getTarget().getView());
					unit.getTarget().getView().addChild(cibleView);
					unit.getTarget().getDefence().getMelody().unpause();
				}
				
	//			unit.setDirection(-gameEngine.getCubeManager().getCube(unitid)
	//					.getAngle() / factor);
				if (KeyboardManager.wKey) {
					unit.rotateDirection(1);
				}
				if (KeyboardManager.xKey) {
					unit.rotateDirection(-1);
				}
//				if (unit.getDirection() > 0) {
//					attackCone.setDirection((long) unit.getDirection());
//				} else {
//					attackCone.setDirection((long) (360 + unit.getDirection()));
//				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			unit.getTarget().getView().removeChild(cibleView);
			unit.getTarget().getDefence().getMelody().pause();
//			unit.getView().removeChild(attackCone);
			KeyboardManager.tap = false;
		}
	}
	

	/**
	 * Methode qui declenche la creation du son via les Parameter Ici les
	 * Parameters sont liees au Player
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */

	public void soundEditPTurn(Unit unit) throws InstantiationException,
			IllegalAccessException {
		if(unit.getSeenMonsters().size()>0){
			Melody melody = unit.getAttackMelody();
			setPStatesToSoundEdit();
			setUStateToWaiting(unit);
			melody.unpause();
			while (!KeyboardManager.tap) {
	//			parameters[0].setAngle((int)(-gameEngine.getCubeManager().getCube(param1id)
	//					.getAngle() / factor));
	//			parameters[1].setAngle((int)(-gameEngine.getCubeManager().getCube(param2id)
	//					.getAngle() / factor));
				if ((KeyboardManager.zKey)
						&& (base.getCenter().distanceTo(
								new Point(parameters[choice].getX(),
										parameters[choice].getY() - 1)) < Base.radius)) {
					parameters[choice].translate(0, -1);
				}
				if ((KeyboardManager.sKey)
						&& (base.getCenter().distanceTo(
								new Point(parameters[choice].getX(),
										parameters[choice].getY() + 1)) < Base.radius)) {
					parameters[choice].translate(0, 1);
				}
				if ((KeyboardManager.qKey)
						&& (base.getCenter().distanceTo(
								new Point(parameters[choice].getX() - 1,
										parameters[choice].getY())) < Base.radius)) {
					parameters[choice].translate(-1, 0);
				}
				if ((KeyboardManager.dKey)
						&& (base.getCenter().distanceTo(
								new Point(parameters[choice].getX() + 1,
										parameters[choice].getY())) < Base.radius)) {
					parameters[choice].translate(1, 0);
				}
				if (KeyboardManager.wKey) {
					parameters[choice].rotate(1);
				}
				if (KeyboardManager.xKey) {
					parameters[choice].rotate(-1);
				}
				if (KeyboardManager.aKey) {
					if (melody.isPlaying())
						melody.pause();
					else
						melody.unpause();
				}
				// on modifie l'instrument en fonctionde l'angle du Parameter1
				int iterInstrum = getChangeInstrument();
				// on passe a l'intrument suivant autant de fois que necessaire
				if (iterInstrum > 0)
					for (int i = 0; i < iterInstrum; i++) {
						try {
							melody.setInstrument(InstrumentLibrary
									.getNextInstrument(melody.getInstrument()));
						} catch (InstantiationException | IllegalAccessException e1) {
							e1.printStackTrace();
						}
					}
				// on passe a l'intrument precedent autant de fois que necessaire
				if (iterInstrum < 0)
					for (int i = 0; i < -iterInstrum; i++) {
						try {
							melody.setInstrument(InstrumentLibrary
									.getPreviousInstrument(melody.getInstrument()));
						} catch (InstantiationException | IllegalAccessException e1) {
							e1.printStackTrace();
						}
					}
	
				// on modifie le Pattern en fonction de l'angle du Parameter2
				int iterPattern = getChangePattern();
				// on passe au Pattern suivant autant de fois que necessaire
				if (iterPattern > 0)
					for (int i = 0; i < iterPattern; i++) {
						try {
							melody.setPattern(MidiPatternsLibaray
									.getNextPattern(melody.getPattern()));
						} catch (InstantiationException | IllegalAccessException e1) {
							e1.printStackTrace();
						}
					}
				// on passe au Pattern precedent autant de fois que necessaire
				if (iterPattern < 0)
					for (int i = 0; i < -iterPattern; i++) {
						try {
							melody.setPattern(MidiPatternsLibaray
									.getPreviousPattern(melody.getPattern()));
						} catch (InstantiationException | IllegalAccessException e1) {
							e1.printStackTrace();
						}
					}
	
				// On regle le parametre d'instrument
				melody.setParameter(getCube1Distance());
	
				// on regle la note de depart
				melody.setTune(getCube2Distance());
	
				// on regle le tempo
				melody.setTempo(getCubesAperture());
	
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
			}
	
			// zik
			KeyboardManager.tap = false;
			melody.pause();
			unit.setAttackMelody(melody);
		}
	}




	public void UAttack(Unit unit) {
		if(unit.getSeenMonsters().size()>0){
			unit.setAperture(30);
			Monster target = unit.getTarget();
			double xdiff = target.getPos().getX() - unit.getPos().getX();
			double ydiff = target.getPos().getY() - unit.getPos().getY();
			double theta = (180 * Math.atan2(ydiff, xdiff) / Math.PI) - 90;
			SinusoidalAttackView attack = new SinusoidalAttackView(
					unit.getAperture(), theta, power, unit.getView());
			unit.getView().addChild(attack);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(target!=null){
				double dTempo = Math.exp(Math.abs(unit.getAttackMelody().getTempo()-target.getDefence().getMelody().getTempo()));
				double dTune = Math.exp(Math.abs(unit.getAttackMelody().getTune()-target.getDefence().getMelody().getTune()));
				double dParameter = Math.exp(Math.abs(unit.getAttackMelody().getParameter()-target.getDefence().getMelody().getParameter()));
				int dInstrum;
				if(unit.getAttackMelody().getInstrument().equals(target.getDefence().getMelody().getInstrument())) dInstrum = 1;
				else dInstrum = 0;
				int dPattern;
				if(unit.getAttackMelody().getPattern().equals(target.getDefence().getMelody().getPattern())) dPattern = 1;
				else dPattern = 0;
				int degats = (int) ((int) power* dPattern*dInstrum*dTune*dTempo*dParameter);
				//on inflige a present les degats a la cibe
				if (power > target.getPos().distanceTo(unit.getPos())) {
					target.decreaseLife(degats);
				}
				target.decreaseLife(degats);
			}
		}
	}

	public void act() throws InstantiationException, IllegalAccessException {
		isTurn = true;
		for (Unit unit : unitList) {
			movingUTurn(unit);
			UDirection(unit);
			soundEditPTurn(unit);
			UAttack(unit);
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
		if (newPos.distanceTo(base.getCenter()) < Base.radius)
			return true;
		return false;
	}

}
