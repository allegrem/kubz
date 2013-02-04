package player;

/**
 * Classe qui représente un joueur, a des référence vers ses unités et paramètres
 */

import gameEngine.GameEngine;


import OpenGL.GLDisplay;
import parameter.*;
import unit.*;


public  class Player {
	
	private Unit unit;
	private Parameter[] parameters ;
	public static int nParams =2;
	private float[] shield;

	private GameEngine gameEngine;
	
	/**
	 * Creation d'un joueur avec une Unit et deux Parameter
	 */
	public Player(GameEngine gameEngine){

		this.gameEngine=gameEngine;
		this.unit = new Unit(this);
		this.parameters = new Parameter[2];
		parameters[0]= new Parameter();
		parameters[1]= new Parameter();
		this.shield = new float[1000];
		for(int i=0; i<11;i++)
			shield[i]=1f;
		
	}
	
	/**
	 * Methode qui retourne l'angle du premier cube parametre
	 * @return
	 */
	public int getChangeAngle1(){
		return (int) parameters[0].getAngle();
	}
	
	/**
	 * Methode retourne l'angle du second cube parametre
	 * @return
	 */
	public int getChangeAngle2(){
		return (int) parameters[1].getAngle();
	}
	
	/**
	 * methode qui retourne la distance entre les deux parametres
	 * @return
	 */
	public int getChangeDistance(){
		return (int) parameters[0].getPos().distanceTo(parameters[1].getPos());
	}
	
	
	
	
	/**
	 * Methodes relatives au shield de l'Unit
	 */
	public void setValues(int l, int r, float value){
		for(int i = l; i<=r; i++)
			shield[i]=value;
	}
	public float getValue(int index){
		return shield[index];
	}
	
	public Unit getUnit(){
		return unit;
	}
	
	
	
	
	/**
	 * Methodes qui gerent l'etat des parametres
	 */
	
	public void setPStatesToFrozen(){
		this.parameters[0].setToFrozen();
		this.parameters[1].setToFrozen();
	}
	
	public void setPStatesToSoundEdit(){
		this.parameters[0].setToSoundEdit();
		this.parameters[1].setToSoundEdit();
	}
	
	public void setPStatesToWaiting(){
		this.parameters[0].setToWaiting();
		this.parameters[1].setToWaiting();
	}
	
	public void setPStatesToPositionError(){
		this.parameters[0].setToFrozen();
		this.parameters[1].setToFrozen();
	}
	
	/**
	 * retourne un tableau avec les ParamState des different Parameters
	 * @return paramsState
	 */
	public ParameterState[] getStates() {
		ParameterState[] paramsState = {parameters[0].getState(),parameters[1].getState()};
		return paramsState;
	}
	
	/**
	 * Bloc des methodes oe on choisit l'etat de l'unite 
	 */	
	
	public void setUStateToAngle(){
		this.unit.setUStateToAngle();
	}
	
	public void setUStateToDirection(){
		this.unit.setUStateToDirection();
	}	
	
	public void setUStateToFrozen(){
		this.unit.setUStateToFrozen();
	}
	
	public void setUStateToMoving(){
		this.unit.setUStateToMoving();
	}
	
	public void setUStateToPositionError(){
		this.unit.setUStateToPositionError();
	}
	
	public void setUStateToSelect(){
		this.unit.setUStateToSelect();
	}
	
	public void setUStateToWaiting(){
		this.unit.setUStateToWaiting();
	}
	
	public UnitState getState(){
		return unit.getState();
	}
	
	/**
	 * A present on va utiliser les methode du haut pour les differentes phases de jeu
	 */
	public void WaitingTurn(){
		setPStatesToWaiting();
		setUStateToWaiting();
	}
	
	/**
	 * Methode qui declenche le choix de l'Unit e utiliser
	 */
	public void choosingUTurn(){
		setPStatesToWaiting();
		setUStateToSelect();
		while(!GLDisplay.tap) {
			
		}
	}
	/**
	 * Methode qui declenche le mouvement de Unit
	 */
	public void movingUTurn(){
		setPStatesToWaiting();
		setUStateToMoving();
		while (!GLDisplay.tap){
			if((GLDisplay.zKey)&&(unit.getPos().getY()>0)) unit.translate(0,-1);
			if((GLDisplay.sKey)&&(unit.getPos().getY()<240)) unit.translate(0,1);
			if((GLDisplay.qKey)&&(unit.getPos().getX()>0)) unit.translate(-1,0);
			if((GLDisplay.dKey)&&(unit.getPos().getX()<240)) unit.translate(1,0);
			//if(GLDisplay.wKey) unit.rotate(1);
			//if(GLDisplay.xKey) unit.rotate(-1);	
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return;
	}
	/**
	 * Methode qui declenche la creation du son via les Parameter
	 */
	public void soundEditPTurn(){
		setPStatesToSoundEdit();
		setUStateToWaiting();
		while (!GLDisplay.tap){
				if(GLDisplay.zKey) parameters[GLDisplay.choice].translate(0,1);
				if(GLDisplay.sKey) parameters[GLDisplay.choice].translate(0, -1);
				if(GLDisplay.qKey) parameters[GLDisplay.choice].translate(-1, 0);
				if(GLDisplay.dKey) parameters[GLDisplay.choice].translate(0, 1);
				if(GLDisplay.wKey) parameters[GLDisplay.choice].rotate(1);
				if(GLDisplay.xKey) parameters[GLDisplay.choice].rotate(-1);						
		}
		GLDisplay.tap = false;
	}	
	/**
	 * Methode qui declenche le choix de l'angle ou de l'ouverture d'attque de Unit
	 */
	public void UDirection(){
		setPStatesToWaiting();
		setUStateToDirection();
		while (!GLDisplay.tap){
			if(GLDisplay.wKey) unit.rotateDirection(1);
			if(GLDisplay.xKey) unit.rotateDirection(-1);						
	}
		GLDisplay.tap = false;
	}
	
	public void UAperture(){
		setPStatesToWaiting();
		setUStateToDirection();
		while (!GLDisplay.tap){
			if(GLDisplay.wKey) unit.rotateAperture(1);
			if(GLDisplay.xKey) unit.rotateAperture(-1);						
	}
		GLDisplay.tap = false;
	}
	
	
	
	public void act(){
		//choosingUTurn();
		movingUTurn();
		
	}
	



	public GameEngine getGameEngine() {
		return gameEngine;
	}
	
}
