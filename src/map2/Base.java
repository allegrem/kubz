/**
 * Classe correspondant a la Base d'un joueur, repr�sent�e par un cercle.
 * @author valeh
 */
package map2;



import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;

public class Base {
	
	private static float radius = 50.0f;   //On code le rayon des bases "en dur".
	private Point center;
	private ReadableColor color;
	
	public Base(Point center,ReadableColor color){
		this.center = center;
		this.color=color;
	}
	
	public void paint(){
		
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());		
		for (int n=0 ; n<200 ; n ++) {
			float xorigin = (float) center.getX();
			float yorigin = (float) center.getY();
			
			float xn = xorigin + (float) ( radius*Math.cos( (2*n*Math.PI)/100 ) );  //centre du cercle<->nouvelle origine du rep�re
			float yn = yorigin + (float) ( radius*Math.sin( (2*n*Math.PI)/100 ) );  //x=R*cos(theta),y=R*sin(theta)et on dessine 100 points soit tous les Pi/100 
																				  //point pour faire le tour 
			
			GL11.glVertex3f(xn,yn,0.0f);
		}
		GL11.glEnd();
		
	Disk disk=new Disk();
		
		GL11.glColor3ub((byte)(color.getRed()-100),(byte)(color.getGreen()),(byte)(color.getBlue()));	
		GL11.glTranslated(center.getX(), center.getY(), 0);
		disk.draw(0f, radius, 50, 1);
		GL11.glTranslated(-center.getX(), -center.getY(), 0);
	}

}
