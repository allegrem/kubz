package unit;

import gameEngine.GameEngine;
import utilities.Point;
import views.CubeControlledView;
import player.*;


public class Unit {
	
	private double life;
	private Point pos=new Point(10,10);
	private double aperture;
	private double direction;
	private UnitState state;
	private CubeControlledView view;
	private Player owner;
	private GameEngine gameEngine;
	
	
	public Unit(Player owner){
		this.state = new WaitingUState();
		this.owner = owner;
		gameEngine=owner.getGameEngine();
		view=new CubeControlledView(pos);
		gameEngine.getMap().add(view);
	
	}
	
	
	public Player getOwner() {
		return owner;
	}


	/**
	 * M�thodes relatives � la vie de Unit
	 */
	public void increaseLife(double inc){
		life = life + inc;
	}
	public void decreaseLife(double dec){
		life = life - dec;
	}
	public void setLife(double newLife){
		life = newLife;
	}
	public double getLife(){
		return life;
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
	 * M�thode relatives � l'angle d'ouverture de l'attaque 
	 * @param theta
	 * @param dTheta
	 */
	public void setAperture(double theta){
		aperture = theta;
		view.setAperture(theta);
	}
	public void rotateAperture(double dTheta){
		aperture = aperture + dTheta;
		view.rotateAperture(dTheta);
	}
	public double getAperture(){
		return aperture;
	}
	
	/**
	 * M�thode relatives � la direction de l'attaque 
	 * @param theta
	 * @param dTheta
	 */
	public void setDirection(double theta){
		direction = theta;
		view.setDirection(theta);
	}
	public void rotateDirection(double dTheta){
		direction = direction + dTheta;
		view.rotateDirection(dTheta);
	}
	public double getDirection(){
		return direction;
	}
	
	public void rotate(double dTheta){
		view.rotate(dTheta);
	}


	/**
	 * setters et getter de UnitState
	 */
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
