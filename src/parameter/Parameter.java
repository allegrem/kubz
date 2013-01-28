package parameter;

import views.CubeControlledView;
import utilities.Point;

public class Parameter {
	
	private Point pos;
	private double angle;
	private ParameterState state;
	private CubeControlledView view;
	
	
	/**
	 * deplacement relatif de unit
	 * @param dx
	 * @param dy
	 */	
	public void translate(int dx, int dy) { 
		pos.translate(dx, dy);
		view.translate(dx, dy);
	}
	
	/**
	 * deplacement absolu de unit
	 * @param x
	 * @param y
	 */	
	public void setLocation(int x, int y) {		
		pos.move(x, y);
		view.setLocation(x,y);
	}
		
	/**
	 * deplacement absolu de unit
	 * @param p
	 */
	public void setLocation(Point p) {
		pos.setLocation(p);
		view.setLocation(p);
	}
	
	/** retourne la position en x,y ou Point**/
	public double getX(){
		return pos.getX();
	}
	public double getY(){
		return pos.getY();
	}
	public Point getPos(){
		return pos;
	}
	
	/**
	 * méthodes relatives à l'angle du cube
	 * @param theta
	 * @param dTheta
	 */
	public void setAngle(double theta){
		angle = theta;
		view.setAngle(theta);
	}
	public void rotate(double dTheta){
		angle = angle + dTheta;
		view.rotate(dTheta);
	}
	private double getAngle(){
		return angle;
	}
	
	/**
	 * Setters et getters relatifs à l'état du paramètre
	 */
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
