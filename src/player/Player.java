package player;

import parameter.*;
import unit.*;


public abstract class Player {
	
	private Unit unit;
	private Parameter[] parameters;
	
	public Player(){
		
	}
	
	
/*Bloc où on choisit l'état des paramettres*/
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
	public ParameterState[] getStates() {
		/*  Bon là ça déconne un peu faut que je revois le type tableu
		 * Parameter params[] = new Parameter[2](); 		
		 *return {parameters[0].getState(),parameters[1].getState()};.*/
		return null;
	}
	
/*Bloc où on choisit l'état de l'unité */	
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
