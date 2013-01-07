/**
 * Classe correspondant a la Base d'un joueur, repr�sent�e par un cercle.
 * @author valeh
 */
package map2;



import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.PartialDisk;

import OpenGL.Displayable;

public class Base implements Displayable{
	private PartialDisk disk1 =new PartialDisk();
	private PartialDisk disk2=new PartialDisk();
	
	public static final int HAUT=3;
	public static final int BAS=1;
	public static final int GAUCHE=0;
	public static final int DROITE=2;

	private static float radius = 80.0f;   //On code le rayon des bases "en dur".

	

	private Point center;
	private ReadableColor color;
	private int sens;
	
	public Base(Point center,ReadableColor color,int sens){
		this.center = center;
		this.color=color;
		this.sens=sens;
	}
	
	public void paint(){	

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());		
		GL11.glTranslated(center.getX(), center.getY(), 0.1);
		disk2.draw(0f, radius, 50, 1,sens*90,180);
		GL11.glColor3ub((byte)(color.getRed()*90/100),(byte)(color.getGreen()*90/100),(byte)(color.getBlue()*90/100));
		disk1.draw(radius-5, radius, 50, 1,sens*90,180);
		GL11.glTranslated(-center.getX(), -center.getY(), -0.1);

	}
	
	public boolean isInZone(Point p){
		double pX = p.getX();
		double pY = MapCreator.display_height-p.getY();
		/*
		 * On calcule la distance du centre au point
		 */
		double d=Math.hypot(center.getX()-pX,center.getY()-pY);
		
		if(d<=radius)
			return true;
		return false;
	}
	public ReadableColor getColor(){
		return color;
	}
	public void setColor(ReadableColor color){
		this.color = color;
	}
	public String getCharac(){
		ReadableColor color = this.getColor();
		return center.getX()+" "+center.getY()+
				"  "+color.getRed()+" "+color.getGreen()+" "+color.getBlue();
		
	}
	

}
