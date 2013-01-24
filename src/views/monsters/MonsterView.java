package views.monsters;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import map2.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;


/**
 * Une unite d'un joueur ou un monstre
 * 
 * @author paul
 * 
 */
public abstract class MonsterView implements DisplayableFather{
	protected static final double size= 30;
	protected static final int height = 30;
	private Point position;
	private Map map;
	private ReadableColor color;
	protected ReadableColor actualColor;
	protected ArrayList<DisplayableChild> children= new ArrayList<DisplayableChild>();
	protected int duration=0;

	/**
	 * Nouveau monstre
	 * @param position Sa position (centre)
	 * @param color Sa couleur
	 */
	public MonsterView(Point position, ReadableColor color) {
		this.map=Map.getMap();
		this.position = position;
		this.color = color;
		actualColor = color;
	}

	@Override
	public abstract void paint();

	/**
	 * deplacement relatif du monstre
	 * @param dx
	 * @param dy
	 */
	public void translate(int dx, int dy) {
		position.translate(dx, dy);
	}

	/**
	 * Definition de la position du monstre a partir
	 * de nouvelles coordonnees
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		position.move(x, y);

	}

	/**
	 * Definition de la position du monstre a partir de
	 * celle d'un point
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
	
	/**
	 * 
	 * @return La couleur du monstre
	 */
	public ReadableColor getColor(){
		return actualColor;
	}
	
	@Override
	public void setColor(ReadableColor color){
		this.actualColor = color;
	}
	
	@Override
	public String getCharac(){
		return "Monster "+" "+position.getX()+" "+position.getY()+
				" "+actualColor.getRed()+" "+actualColor.getGreen()+" "+actualColor.getBlue();
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
	
	public void paintChildren(){
		for(DisplayableChild child:children){
			child.paint();
			
		}
	}
	
	public static double getSize(){
		return size;
	}
	
	public static int getHeight(){
		return height;
	}
}
