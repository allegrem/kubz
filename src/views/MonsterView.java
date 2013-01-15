package views;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import map2.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Point;


/**
 * Une unité d'un joueur ou un monstre
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

	public MonsterView(Point position, ReadableColor color,Map map) {
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


	
	public boolean isInZone(Point p){
		double x1 = position.getX()-size/2, x2 = position.getX()+size/2;
		double y1 = position.getY()-size/2, y2 = position.getY()+size/2;
		
		double pX = p.getX();
		double pY = p.getY();
		
		
		if (pX>=x1 && pX<=x2 && pY>=y1 && pY<=y2)
			return true;
		return false;
	}
	public ReadableColor getColor(){
		return actualColor;
	}
	public void setColor(ReadableColor color){
		this.actualColor = color;
	}
	public String getCharac(){
		return this.getType()+" "+position.getX()+" "+position.getY()+
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
	
}
