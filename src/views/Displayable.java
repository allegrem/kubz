package views;

import org.lwjgl.util.Color;

import utilities.Point;


public interface Displayable {
	public void paint();
	public Displayable getChildren();
	public void addChild(Displayable object);
	public int getIndex();
	public void setIndex();
	public int getTimeOut();
	public void setTimeOut(int time);
	
	public boolean isInZone(Point mousePoint);
	public void setColor(Color color);
	public String getCharac();
}
