
package views.staticViews;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.PartialDisk;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

/**
 * Classe correspondant a la Base d'un joueur, represente par un demi-disque.
 * @author valeh
 */
public class BaseView implements DisplayableFather{
	private PartialDisk disk1 =new PartialDisk();
	private PartialDisk disk2=new PartialDisk();
	
	public static final int HAUT=3;
	public static final int BAS=1;
	public static final int GAUCHE=0;
	public static final int DROITE=2;

	private static final float radius = 80.0f;   //On code le rayon des bases "en dur".

	private Point center;
	private ReadableColor color;
	private int sens;
	
	/**
	 * Nouvelle base
	 * @param center Centre de la base
	 * @param color Couleur de la abse
	 * @param sens Emplacement de la base (Haut, Bas, Gauche ou Droite)
	 */
	public BaseView(Point center,ReadableColor color,int sens){
		this.center = center;
		this.color=color;
		this.sens=sens;
	}
	
	@Override
	public void paint(){	

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());		
		GL11.glTranslated(center.getX(), center.getY(), 0.1);
		disk2.draw(0f, radius, 50, 1,sens*90,180);
		GL11.glColor3ub((byte)(color.getRed()*90/100),(byte)(color.getGreen()*90/100),(byte)(color.getBlue()*90/100));
		disk1.draw(radius-5, radius, 50, 1,sens*90,180);
		GL11.glTranslated(-center.getX(), -center.getY(), -0.1);

	}
	
	@Override
	public boolean isInZone(Point p){
		double pX = p.getX();
		double pY = p.getY();
		/*
		 * On calcule la distance du centre au point
		 */
		double d=Math.hypot(center.getX()-pX,center.getY()-pY);
		
		if(d<=radius)
			return true;
		return false;
	}
	
	/**
	 * 
	 * @return La couleur de la base
	 */
	public ReadableColor getColor(){
		return color;
	}
	
	@Override
	public void setColor(ReadableColor color) {
		this.color = color;
		
	}

	@Override
	public String getCharac(){
		
		return center.getX()+" "+center.getY()+
				"  "+color.getRed()+" "+color.getGreen()+" "+color.getBlue()+" "+sens;
		
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


	@Override
	public double getX() {
	
		return center.getX();
	}


	@Override
	public double getY() {
		
		return center.getY();
	}


	@Override
	public ArrayList<DisplayableChild> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addChild(DisplayableChild object) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeChild(DisplayableChild child) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean collisionCanOccure(Point point, float taille) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCenter(Point center2) {
		// TODO Auto-generated method stub
		
	}




	

	
	

}
