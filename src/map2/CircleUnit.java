/**
 * Unité Monstre de type cercle.
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
				/*GL11.glBegin(GL11.GL_LINE_LOOP);
				GL11.glColor3ub((byte)actualColor.getRed(),(byte)actualColor.getGreen(),(byte)actualColor.getBlue());	
				for(int r=0;r<=size;r++){
				for (int n=0 ; n<100 ; n ++) {
					float xorigin = (float) getX();
					float yorigin = (float) getY();
					
					float xn = xorigin + (float) ( r*Math.cos( (2*n*Math.PI)/100 ) );  //centre du cercle<->nouvelle origine du repï¿½re
					float yn = yorigin + (float) ( r*Math.sin( (2*n*Math.PI)/100 ) );  //x=R*cos(theta),y=R*sin(theta)et on dessine 100 points soit tous les Pi/100 
																						  //point pour faire le tour 
					
					GL11.glVertex3f(xn,yn,0.0f);
				}
				}
				GL11.glEnd();*/
		Disk disk=new Disk();
		GL11.glColor3ub((byte)actualColor.getRed(),(byte)actualColor.getGreen(),(byte)actualColor.getBlue());
		GL11.glTranslated(getX(), getY(), 0);
		disk.draw(0f, (float)size, 50, 1);
		GL11.glTranslated(-getX(), -getY(), 0);
		
		

	}

	@Override
	public String getType() {
		return "C";
	}


}
