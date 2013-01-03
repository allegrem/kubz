package map2;


import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;

public class CircleUnit extends Unit {


	public CircleUnit(Point position, ReadableColor color,Map map) {
		super(position, color,map);

	}

	@Override
	public void paint() {
				/*GL11.glBegin(GL11.GL_LINE_LOOP);
				GL11.glColor3ub((byte)actualColor.getRed(),(byte)actualColor.getGreen(),(byte)actualColor.getBlue());	
				for(int r=0;r<=size;r++){
				for (int n=0 ; n<100 ; n ++) {
					float xorigin = (float) getX();
					float yorigin = (float) getY();
					
					float xn = xorigin + (float) ( r*Math.cos( (2*n*Math.PI)/100 ) );  //centre du cercle<->nouvelle origine du rep�re
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


}
