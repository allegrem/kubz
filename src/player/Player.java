package player;

/**
 * Classe qui repr�sente un joueur, a des r�f�rence vers ses unit�s et param�tres
 * @author Felix
 */

import parameter.*;
import unit.*;


public abstract class Player {
	
	private Unit unit;
	private Parameter[] parameters ;
	
	/**
	 * Cr�ation d'un joueur avec une Unit et deux Parameter
	 */
	public Player(){
		this.unit = new Unit();
		this.parameters = new Parameter[2];
		parameters[0]= new Parameter();
		parameters[1]= new Parameter();
		
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
	}
	/**
	 * M�thode qui d�clenche le mouvement de Unit
	 */
	public void movingUTurn(){
		setPStatesToWaiting();
		setUStateToMoving();
	}
	/**
	 * M�thode qui d�clenche la cr�ation du son via les Parameter
	 */
	public void soundEditPTurn(){
		setPStatesToSoundEdit();
		setUStateToWaiting();
	}	
	/**
	 * M�thode qui d�clenche le choix de l'angle ou de l'ouverture d'attque de Unit
	 */
	public void UTurn(){
		setPStatesToWaiting();
		setUStateToDirection();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
