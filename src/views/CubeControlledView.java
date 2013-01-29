package views;


import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import map2.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Maths;
import utilities.Point;
import utilities.Vector;

/**
 * Un objet controlle par un cube.
 * Cela peut etre l'unite d'un joueur
 * ou un cube servant de parametre
 * @author paul + 2-3 modifs de Felix
 *
 */
public class CubeControlledView implements DisplayableFather{
	private double size= 30;
	private int height = 30;
	private Point position;
	private ArrayList<DisplayableChild> children= new ArrayList<DisplayableChild>();
	private int duration=0;
	private ReadableColor color=Color.RED;
	private boolean untracked=false; //L'unite est-elle sur la table ?
	private double angle = 0;
	private double aperture;
	private double direction;
	
	/**
	 * Nouveau cubeControlled
	 * @param position Sa position (centre)
	 * @param color Sa couleur
	 */
	public CubeControlledView(Point position, ReadableColor color) {
		this.position = position;
	}
	
	/**
	 * Deplacement relatif du monstre
	 * @param dx Deplacement selon x
	 * @param dy Deplacement selon y
	 */
	public void translate(int dx, int dy) {
		position.translate(dx, dy);
	}

/**
 * Nouvelle position a partir des 
 * nouvelles coordonnes
 * @param x
 * @param y
 */
	public void setLocation(int x, int y) {
		position.move(x, y);

	}

	/**
	 * Nouvelle position a partir de l'emplacement d'un point
	 * @param p
	 */
	public void setLocation(Point p) {
		position.setLocation(p);

	}

	@Override
	public double getX() {
		return position.getX();
	}

	@Override
	public double getY() {

		return position.getY();
	}
	
	/**
	 * méthodes relatives à l'angle du cube, pour le Parameter ...
	 * @param theta
	 * @param dTheta
	 * @author Felix
	 */
	public void setAngle(double theta){
		angle = theta;
	}
	public void rotate(double dTheta){
		angle = angle + dTheta;
	}
	public double getAngle(){
		return angle;
	}
	
	/**
	 * Méthode relatives à l'angle d'ouverture de l'attaque 
	 * @param theta
	 * @param dTheta
	 * @author Felix
	 */
	public void setAperture(double theta){
		aperture = theta;
	}
	public void rotateAperture(double dTheta){
		aperture = aperture + dTheta;;
	}
	public double getAperture(){
		return aperture;
	}
	
	/**
	 * Méthode relatives à la direction de l'attaque 
	 * @param theta
	 * @param dTheta
	 * @author Felix
	 */
	public void setDirection(double theta){
		direction = theta;
	}
	public void rotateDirection(double dTheta){
		direction = direction + dTheta;;
	}
	public double getDirection(){
		return direction;
	}


	@Override
	public boolean isInZone(Point p){
		double x1 = position.getX()-size/2, x2 = position.getX()+size/2;
		double y1 = position.getY()-size/2, y2 = position.getY()+size/2;
		
		double pX = p.getX();
		double pY = p.getY();
		
		
		if (pX>=x1 && pX<=x2 && pY>=y1 && pY<=y2)
			return true;
		return false;
	}
	
	@Override
	public void paint() {
		
		/**
		 * Si l'unite n'est plus sur la table, on affiche un carre rouge
		 */
		if (untracked){
			glBegin(GL_QUADS);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glNormal3f(0, 0, -1.0f);
			GL11.glColor3ub((byte) (color.getRed()), (byte) (color.getGreen()) , (byte) (color.getBlue()));
			
			glVertex3d(position.getX()-MonsterView.size/2, position.getY()-MonsterView.size/2, 0);
			glVertex3d(position.getX()+MonsterView.size/2, position.getY()-MonsterView.size/2, 0);
			glVertex3d(position.getX()+MonsterView.size/2, position.getY()+MonsterView.size/2, 0);
			glVertex3d(position.getX()-MonsterView.size/2, position.getY()+MonsterView.size/2, 0);
			GL11.glEnd();
		}
		
	}


	@Override
	public ArrayList<DisplayableChild> getChildren() {
		return children;
	}

	@Override
	public void addChild(DisplayableChild child) {
		children.add(child);
		child.setFather(this);
		
	}
	
	public void removeChild(DisplayableChild child){
		children.remove(child);
	}

	@Override
	public int getTimeOut() {
		return duration;
	}

	@Override
	public void setTimeOut(int time) {
		this.duration=time;
		
	}

	@Override
	public void setColor(ReadableColor color) {
		this.color=color;
		
	}

	@Override
	public String getCharac() {
		return "CubeControlled";
	}
	
	public void paintChildren(){
		for(DisplayableChild child:children){
			child.paint();
		}
	}

	@Override
	public boolean collisionCanOccure(Point point, float taille) {
		double dist=size*Math.sqrt(2)/2;
		Vector vect= Maths.makeVector(point.getX(), point.getY(), 0, getX(), getY(), 0);
		if(dist+taille>=vect.norme())
			return true;
		return false;
	}
}
