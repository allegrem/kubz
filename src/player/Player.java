package player;

/**
 * Classe qui repr�sente un joueur, a des r�f�rence vers ses unit�s et param�tres
 * @author Felix
 */

import gameEngine.GameEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.lwjgl.input.Keyboard;
import parameter.*;
import unit.*;
import views.CubeControlledView;


public  class Player implements ActionListener,KeyListener{
	
	private Unit unit;
	private Parameter[] parameters ;
	private int nParams =2;
	private float[] shield;

	
	private boolean zKey = false;
	private boolean qKey = false;
	private boolean sKey = false;
	private boolean dKey = false;
	private boolean wKey = false;
	private boolean xKey = false;
	private boolean tap = false;
	private int choice;
	private GameEngine gameEngine;
	
	/**
	 * Cr�ation d'un joueur avec une Unit et deux Parameter
	 */
	public Player(GameEngine gameEngine){
		this.unit = new Unit(this);
		this.gameEngine=gameEngine;
		this.parameters = new Parameter[2];
		parameters[0]= new Parameter();
		parameters[1]= new Parameter();
		this.shield = new float[1000];
		for(int i=0; i<11;i++)
			shield[i]=1f;
		
	}
	
	/**
	 * M�thodes relatives au shield de l'Unit
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
	 * M�thodes qui g�rent l'�tat des param�tres
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
	 * Bloc des m�thodes o� on choisit l'�tat de l'unit� 
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
	 * A present on va utiliser les m�thode du haut pour les diff�rentes phases de jeu
	 */
	public void WaitingTurn(){
		setPStatesToWaiting();
		setUStateToWaiting();
	}
	
	/**
	 * M�thode qui declenche le choix de l'Unit � utiliser
	 */
	public void choosingUTurn(){
		setPStatesToWaiting();
		setUStateToSelect();
		while(!tap) {
			movingUTurn();
		}
	}
	/**
	 * M�thode qui d�clenche le mouvement de Unit
	 */
	public void movingUTurn(){
		setPStatesToWaiting();
		setUStateToMoving();
		while (!tap){
			if(zKey) unit.translate(0,1);
			if(sKey) unit.translate(0, -1);
			if(qKey) unit.translate(-1, 0);
			if(dKey) unit.translate(0, 1);
		}
		return;
	}
	/**
	 * M�thode qui d�clenche la cr�ation du son via les Parameter
	 */
	public void soundEditPTurn(){
		setPStatesToSoundEdit();
		setUStateToWaiting();
		while (!tap){
				if(zKey) parameters[choice].translate(0,1);
				if(sKey) parameters[choice].translate(0, -1);
				if(qKey) parameters[choice].translate(-1, 0);
				if(dKey) parameters[choice].translate(0, 1);
				if(wKey) parameters[choice].rotate(1);
				if(xKey) parameters[choice].rotate(-1);						
		}
		tap = false;
	}	
	/**
	 * M�thode qui d�clenche le choix de l'angle ou de l'ouverture d'attque de Unit
	 */
	public void UDirection(){
		setPStatesToWaiting();
		setUStateToDirection();
		while (!tap){
			if(wKey) unit.rotateDirection(1);
			if(xKey) unit.rotateDirection(-1);						
	}
		tap = false;
	}
	
	public void UAperture(){
		setPStatesToWaiting();
		setUStateToDirection();
		while (!tap){
			if(wKey) unit.rotateAperture(1);
			if(xKey) unit.rotateAperture(-1);						
	}
		tap = false;
	}
	
	
	
	public void act(){
		choosingUTurn();
		
		
	}
	
	
	/**
	 * on controle les d�placements via "ZQSD"
	 * on controle la rotation avec "WX"
	 * on passe � l'�tape suivante avec "P"
	 * on change le Parameter sur lequel on agis via "TAB"
	 * 
	 */


	@Override
	public void keyPressed(KeyEvent arg0) {	
		if (arg0.getSource().equals(Keyboard.KEY_Z))
			zKey = true;
		if (arg0.getSource().equals(Keyboard.KEY_Q))
			qKey = true;
		if (arg0.getSource().equals(Keyboard.KEY_S))
			sKey = true;
		if (arg0.getSource().equals(Keyboard.KEY_D))
			dKey =  true;
		if (arg0.getSource().equals(Keyboard.KEY_W))
			wKey =  true;
		if (arg0.getSource().equals(Keyboard.KEY_X))
			xKey =  true;
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getSource().equals(Keyboard.KEY_Z))
			sKey = false;
		if (arg0.getSource().equals(Keyboard.KEY_Q))
			qKey = false;
		if (arg0.getSource().equals(Keyboard.KEY_S))
			sKey = false;
		if (arg0.getSource().equals(Keyboard.KEY_D))
			dKey =  false;
		if (arg0.getSource().equals(Keyboard.KEY_W))
			wKey =  false;
		if (arg0.getSource().equals(Keyboard.KEY_X))
			xKey =  false;
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		if (arg0.getSource().equals(Keyboard.KEY_P	))
			tap = true;	
		if (arg0.getSource().equals(Keyboard.KEY_TAB)){
			choice = (choice + 1)%nParams;
		}
			
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public GameEngine getGameEngine() {
		
		return gameEngine;
	}
	
}
