/**
 * Classe correspondant a la Base d'un joueur, repr�sent�e par un cercle.
 * @author valeh
 */
package map2;



import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;

public class Base {
	

	private static float radius = 80.0f;   //On code le rayon des bases "en dur".

	

	private Point center;
	private ReadableColor color;
	
	public Base(Point center,ReadableColor color){
		this.center = center;
		this.color=color;
	}
	
	public void paint(){	
		/*GL11.glPushMatrix();
		GL11.glScalef(1.0f,1.5f,1.0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glColor3f(0.0f,0.0f,1.0f);		
		for (int n=0 ; n<100 ; n ++) {
			float xorigin = (float) center.getX();
			float yorigin = (float) center.getY();
			
			float xn = xorigin + (float) ( radius*Math.cos( (2*n*Math.PI)/100 ) );  //centre du cercle<->nouvelle origine du rep�re
			float yn = yorigin + (float) ( radius*Math.sin( (2*n*Math.PI)/100 ) );  //x=R*cos(theta),y=R*sin(theta)et on dessine 100 points soit tous les Pi/100 
																				  //point pour faire le tour 
			GL11.glVertex3f(xn,yn,0.0f);
		}
		GL11.glEnd();
		GL11.glPopMatrix();*/

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Disk disk1 =new Disk();
		Disk disk2=new Disk();
		GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());		
		GL11.glTranslated(center.getX(), center.getY(), 0);
		disk2.draw(0f, radius, 50, 1);
		GL11.glColor3ub((byte)(color.getRed()*90/100),(byte)(color.getGreen()*90/100),(byte)(color.getBlue()*90/100));
		disk1.draw(radius-5, radius, 50, 1);
		GL11.glTranslated(-center.getX(), -center.getY(), 0);

	}
	public String getCharac(){
		return center.getX()+" "+center.getY();
	}

}
