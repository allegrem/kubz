/**
 * Unit� Monstre de type cercle.
 * @author valeh
 */

package map2;



import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;

import OpenGL.Displayable;

public class CircleMonsterView extends MonsterView {
	private Cylinder cylinder=new Cylinder();
	private ReadableColor color; 


	public CircleMonsterView(Point position,ReadableColor color,Map map) {
		super(position,color,map);
	}

	@Override
	public void paint() {

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());
		GL11.glTranslated(getX(), getY(), 0);
		cylinder.draw((float)(size/2),0 ,height, 50, 1);
		GL11.glTranslated(-getX(), -getY(), 0);
	
	}
	
	public boolean isInZone(Point p){
		double pX = p.getX();
		double pY = p.getY();
		/*
		 * On calcule la distance du centre au point
		 */
		double d=Math.hypot(getX()-pX,getY()-pY);
		
		if(d<=size/2)
			return true;
		return false;
	}
	

	
	public ReadableColor getColor(){
		return color;
	}
	@Override
	public void setColor(Color color) {
		this.color = color;
		
	}

	@Override
	public String getType() {
		return "C";
	}

	@Override
	public Displayable getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(Displayable object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTimeOut(int time) {
		// TODO Auto-generated method stub
		
	}



}
