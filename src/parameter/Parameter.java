package parameter;

import gameEngine.GameEngine;
import player.Player;
import views.CubeControlledView;
import utilities.Point;

public class Parameter {
	
	private Point pos = new Point(10,10);
	private double angle;
	private double dAngle = 0;
	private ParameterState state;
	private Player owner;
	private GameEngine gameEngine;
	private double size;
	private CubeControlledView view;

	
	public Parameter(Player owner){
		this.state = new WaitingPState();
		this.setOwner(owner);
		gameEngine=owner.getGameEngine();
		view = new CubeControlledView(pos);
		size = view.getSize();
		gameEngine.getMap().add(view);
		
	}

	

	double getDAngle(){
		double vase = dAngle;
		dAngle = 0;
		return vase;		
	}
	
	
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
	 * @param d
	 * @param e
	 */	
	public void setLocation(double d, double e) {		
		pos.move(d, e);
		view.setLocation(d,e);
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
	public double getSize(){
		return size;
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
	public double getAngle(){
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

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
}
