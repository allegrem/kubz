package views;

import java.util.ArrayList;

import org.lwjgl.util.Color;

import utilities.Point;


public interface Displayable {
	public void paint();
	public int getTimeOut();
	public void setTimeOut(int time);
	
	public boolean isInZone(Point mousePoint);
	public void setColor(Color color);
	public String getCharac();
}
