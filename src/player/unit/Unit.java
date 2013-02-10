package player.unit;

import cube.Cube;
import gameEngine.GameEngine;
import traitementVideo.VirtualCube;
import utilities.Point;
import views.CubeControlledView;
import views.informationViews.LifeView;
import views.interfaces.DisplayableFather;
import player.*;


public class Unit extends CubeOwner{
	
	private double life;
	private double size;
	private Point pos=new Point(10,10);
	private double aperture;
	private double direction;
	private double instrumentChoiceAngle=0;
	private UnitState state;
	private CubeControlledView view;
	private Player owner;
	private GameEngine gameEngine;
	private int power;
	private Cube cube;
	private VirtualCube vCube;
	
	
	public Unit(Player owner){
		this.state = new WaitingUState();
		this.owner = owner;
		gameEngine=owner.getGameEngine();
		view=new CubeControlledView(pos);
		view.addChild(new LifeView(view));
		size=view.getSize();
		gameEngine.getMap().add(view);
		
	}
	
	/**
	 * Renvoie le Player auquel est lie cet Unit
	 * @return
	 */
	public Player getOwner(){
		return owner;
	}

	/**
	 * Retourne le cube physique auquel est associé Unit
	 * @return
	 */
	public Cube getCube() {
		return cube;
	}
	
	/**
	 * Retourne le cube du traitement de l'image auquel est associe Unit
	 * @return
	 */
	public VirtualCube getVCube(){
		return vCube;
	}

	/**
	 * Mï¿½thodes relatives ï¿½ la vie de Unit
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
	 * Mï¿½thode relatives ï¿½ l'angle d'ouverture de l'attaque 
	 * @param theta
	 * @param dTheta
	 */
	public void setAperture(double theta){
		aperture = theta;
		view.setAperture(theta);
		view.setAngle(theta);
	}
	public void rotateAperture(double dTheta){
		if ((aperture+dTheta>=0)&&(aperture+dTheta<=360)) aperture = aperture + dTheta;		
		view.rotateAperture(dTheta);
		view.rotate(dTheta);
	}
	public double getAperture(){
		return aperture;
	}
	
	/**
	 * Mï¿½thode relatives ï¿½ la direction de l'attaque 
	 * @param theta
	 * @param dTheta
	 */
	public void setDirection(double theta){
		direction = theta;
		view.setDirection(theta);
		view.setAngle(theta);
	}
	public void rotateDirection(double dTheta){
		direction = direction + dTheta;
		view.rotateDirection(dTheta);
		view.rotate(dTheta);
	}
	public double getDirection(){
		return direction;
	}
	
	/**
	 * Mï¿½thode relatives au choix de l'instrument 
	 * @param theta
	 * @param dTheta
	 */
	public void setInstrumentChoice(double theta){
		instrumentChoiceAngle = theta;
		view.setIstrumentChoice(theta);
		view.setAngle(theta);
	}
	public void rotateInstrumentChoice(double dTheta){
		instrumentChoiceAngle = instrumentChoiceAngle + dTheta;
		view.rotateIstrumentChoice(dTheta);
		view.rotate(dTheta);
	}
	public double getInstrumentChoiceAngle(){
		return instrumentChoiceAngle;
	}	


	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}


	/**
	 * Simple rotation qui ne modifie aucun parametre
	 * @param dTheta
	 */
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


	public double getSize() {
		
		return size;
	}


	public DisplayableFather getView() {
		
		return view;
	}
	

}
