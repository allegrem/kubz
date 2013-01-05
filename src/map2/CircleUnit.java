/**
 * Unit� Monstre de type cercle.
 * @author valeh
 */

package map2;



import static org.lwjgl.opengl.GL11.glColor3ub;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import map2.Map;
import map2.Point;
import map2.Unit;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;
import org.newdawn.slick.Color;

public class CircleUnit extends Unit {


	public CircleUnit(Point position, ReadableColor color,Map map) {
		super(position, color,map);

	}

	@Override
	public void paint() {

		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glColor3f(1.0f,0.0f,0.0f); 
		
		double xCenter = super.getX();
		double yCenter = super.getY();
		
		GL11.glVertex3d(xCenter, yCenter, 0.0);
		GL11.glEnd();


		GL11.glDisable(GL11.GL_TEXTURE_2D);
				
		Disk disk=new Disk();
		GL11.glColor3ub((byte)actualColor.getRed(),(byte)actualColor.getGreen(),(byte)actualColor.getBlue());
		GL11.glTranslated(getX(), getY(), 0);
		disk.draw(0f, (float)size/2, 50, 1);
		GL11.glTranslated(-getX(), -getY(), 0);
	
	}
	
	public boolean isInZone(Point p){
		double pX = p.getX();
		double pY = MapCreator.display_height-p.getY();
		/*
		 * On calcule la distance du centre au point
		 */
		double d=Math.hypot(getX()-pX,getY()-pY);
		
		if(d<=size/2)
			return true;
		return false;
	}

	@Override
	public String getType() {
		return "C";
	}


}
