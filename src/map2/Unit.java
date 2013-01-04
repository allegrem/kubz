package map2;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

/**
 * Une unité d'un joueur ou un monstre
 * 
 * @author paul
 * 
 */
public abstract class Unit {
	protected static final double size= 15.0;
	protected static final int height = 15;
	private Point position;
	private Map map;
	private ReadableColor color;
	protected ReadableColor actualColor;

	public Unit(Point position, ReadableColor color,Map map) {
		this.map=map;
		this.position = position;
		this.color = color;
		actualColor = color;
	}

	public abstract void paint();
	public abstract String getType();

	public void translate(int dx, int dy) {
		position.translate(dx, dy);
	}

	

	public void setLocation(int x, int y) {
		position.move(x, y);

	}

	public void setLocation(Point p) {
		position.setLocation(p);

	}

	public double getX() {

		return position.getX();
	}

	public double getY() {

		return position.getY();
	}

	/**
	 * Si le cube n'est plus sur la table et n'est plus repéré par la caméra, on
	 * affiche l'unité en rouge
	 * 
	 */
	public void unitUntracked() {
		actualColor = Color.RED;

	}

	/**
	 * Si le cube est reposé sur la tabe, l'unité reprend sa couleur normale
	 * 
	 */
	public void unitTracked() {
		actualColor = color;

	}

	public void attaque(double angle, double direction, double power){
		angle*=2*Math.PI/360;
		direction*=2*Math.PI/360;
		
		
		
		glBegin(GL11.GL_TRIANGLES);
		glColor3ub((byte)Color.RED.getRed(),(byte)Color.RED.getGreen(),(byte)Color.RED.getBlue());
		glVertex3d(position.getX(),position.getY(),height/2);
		glVertex3d(power*Math.sin(direction+angle)+position.getX(),power*Math.cos(direction+angle)+position.getY(),height/2);
		glVertex3d(power*Math.sin(-(angle-direction))+position.getX(),power*Math.cos(-(angle-direction))+position.getY(),height/2);
		GL11.glEnd();
		
		map.paint();
	}
	
	public String getCharac(){
		return this.getType()+" "+position.getX()+" "+position.getY();
	}
	
	public boolean isInZone(Point p){
		double x1 = position.getX()-size/2, x2 = position.getX()+size/2;
		double y1 = position.getY()-size/2, y2 = position.getY()+size/2;
		double pX = p.getX();
		double pY = p.getY();
		
		if (pX>=x1 && pX<=x2 && pY>=y1 && pY<=y2)
			return true;
		return false;
	}
}
