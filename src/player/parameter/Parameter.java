package player.parameter;

/**
 * @author Felix
 */

import cube.Cube;
import gameEngine.GameEngine;
import player.CubeOwner;
import player.Player;
import views.CubeControlledView;
import traitementVideo.VideoCube;
import utilities.Point;

public class Parameter extends CubeOwner{
	
	private Point pos = new Point(10,10);
	private int angle;
	private int dAngle = 0;
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
		view.setInvisible3D(true);
		size = view.getSize();
		view.setInvisible3D(true);
		gameEngine.getMap().add(view);
		
	}

	

	double getDAngle(){
		double vase = dAngle;
		dAngle = 0;
		return vase;		
	}

	/**
	 * Retourne le cube physique auquel est associ� Parameter
	 * @return
	 */
	public Cube getCube() {
		return cube;
	}
	
	/**
	 * Retourne le cube du traitement de l'image auquel est associe Parameter
	 * @return
	 */
	public VideoCube getVCube(){
		return vCube;
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
	 * m�thodes relatives � l'angle du cube
	 * @param theta
	 * @param dTheta
	 */
	public void setAngle(int theta){
		angle = theta;
		view.setAngle(theta);
	}
	public void rotate(int dTheta){
		angle = angle + dTheta;
		view.rotate(dTheta);
	}
	public int getAngle(){
		return angle;
	}
	
	
	/**
	 * Setters et getters relatifs � l'�tat du param�tre
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
