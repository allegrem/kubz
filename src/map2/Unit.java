package map2;

import org.lwjgl.util.Color;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.ReadablePoint;

public abstract class Unit {
	private Point position;
	private Color color;
	protected ReadableColor actualColor;
	
	
	public Unit(Point position,Color color){
		this.position=position;
		this.color=color;
		actualColor=color;
	}
	
	public abstract void paint();
	
	public void translate(int dx, int dy){
		position.translate(dx,dy);
	}
	
	public void translate(ReadablePoint p){
		position.translate(p);
	}
	
	public void setLocation(int x, int y){
		position.setLocation(x,y);
		
	}
	
	public void setLocation(ReadablePoint p){
		position.setLocation(p);
		
	}
	
	public void unitUntracked(){
		actualColor=Color.RED;
		
	}
	
	public void unitTracked(){
		actualColor=color;
		
	}
}
