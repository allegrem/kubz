package parameter;

import generalite.Point;

public class Parameter {
	
	private Point pos;
	private ParameterState state;
	
	//setter et getter de la position
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	//setters et getter de l'état
	public void setToFrozen(){
		this.state = new FrozenPState();
	}
	public void setToSoundEdit(){
		this.state = new SoundEditPState();
	}
	public void setToWaiting(){
		this.state = new WaitingPState();
	}
	public void setToPositionError(){
		this.state = new PositionErrorPState();
	}
	public ParameterState getState() {
		return state;
	}
	
	
	

}
