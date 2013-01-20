package views;

import java.util.ArrayList;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Point;


public interface Displayable {
	public void paint();
	public int getTimeOut();
	public void setTimeOut(int time);
	
	public boolean isInZone(Point mousePoint);
	public void setColor(ReadableColor color);
	public String getCharac();

}
