package player;

/**
 * Classe qui représente un joueur, a des référence vers ses unités et paramètres
 * @author Felix
 */

import parameter.*;
import unit.*;


public abstract class Player {
	
	private Unit unit;
	private Parameter[] parameters ;
	
	/**
	 * Création d'un joueur avec une Unit et deux Parameter
	 */
	public Player(){
		this.unit = new Unit();
		this.parameters = new Parameter[2];
		parameters[0]= new Parameter();
		parameters[1]= new Parameter();
		
	}
	
	
	/**
	 * Méthodes qui gèrent l'état des paramètres
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
	 * Bloc des méthodes où on choisit l'état de l'unité 
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
}
