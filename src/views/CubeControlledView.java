package views;


import java.util.ArrayList;

import map2.Map;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Point;


public class CubeControlledView implements DisplayableFather{
	private double size= 30;
	private int height = 30;
	private Point position;
	private Map map;
	private ArrayList<DisplayableChild> children= new ArrayList<DisplayableChild>();
	private int duration=0;
	private ReadableColor color=Color.RED;
	
	public CubeControlledView(Point position, ReadableColor color,Map map) {
		this.map=map;
		this.position = position;
	}
	
	public void translate(int dx, int dy) {
		position.translate(dx, dy);
	}

	

	public void setLocation(int x, int y) {
		position.move(x, y);

	}

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
	
	@Override
	public void paint() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public void paintChildren(){
		for(DisplayableChild child:children){
			child.paint();
		}
	}
}
