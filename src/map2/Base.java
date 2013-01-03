/**
 * Classe correspondant a la Base d'un joueur, repr�sent�e par un cercle.
 * @author valeh
 */
package map2;



import org.lwjgl.opengl.GL11;

public class Base {
	
	private static float radius = 15.0f;   //On code le rayon des bases "en dur".
	private Point center;
	
	public Base(Point center){
		this.center = center;
	}
	
	public void paint(){
		
		GL11.glPushMatrix();
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
		GL11.glPopMatrix();
	}

}
