package unit;

import generalite.Point;

public class Unit {
	
	private Point pos;
	private UnitState state;
	
	
	public Unit(){
		this.state = new WaitingUState();
	}

	//setters et getter de la position
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	//setters et getter de l'état
	public void setUStateToAngle(){
		this.state = new AngleUState();
	}
	public void setUStateToDirection(){
		this.state = new DirectionUState();
	}	
	public void setUStateToFrozen(){
		this.state = new FrozenUState();
	}
	public void setUStateToMoving(){
		this.state = new MovingUState();
	}
	public void setUStateToPositionError(){
		this.state = new PositionErrorUState();
	}
	public void setUStateToSelect(){
		this.state = new SelectUState();
	}	
	public void setUStateToWaiting(){
		this.state = new WaitingUState();
	}	
	public UnitState getState(){
		return state;
	}
	

}
