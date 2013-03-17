
package views.staticViews;

import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.PartialDisk;

import OpenGL.GLDisplay;

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
	private boolean rectangular=true;
	
	public static final int HAUT=3;
	public static final int BAS=1;
	public static final int GAUCHE=0;
	public static final int DROITE=2;

	private static final float radius = 80.0f;   //On code le rayon des bases "en dur".

	private Point center;
	private ReadableColor color;
	private int sens;
	private Point size;
	
	/**
	 * Nouvelle base circulaire
	 * @param center Centre de la base
	 * @param color Couleur de la abse
	 * @param sens Emplacement de la base (Haut, Bas, Gauche ou Droite)
	 */
	public BaseView(Point center,ReadableColor color,int sens){
		this.center = center;
		this.color=color;
		this.sens=sens;
		rectangular=false;
	}
	
	/**
	 * Base "rectangulaire"
	 * 
	 * @param center
	 * @param size
	 * @param color
	 */
	public BaseView(Point center,Point size,ReadableColor color) {
		rectangular=true;
		this.center = center;
		this.color=color;
		this.size=size;
		rectangular=true;
	}

	@Override
	public void paint(){	
		GL11.glDisable(GL11.GL_TEXTURE_2D);
				
		if(rectangular){
			if(!GLDisplay.getMode3D()){
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3ub((byte)(color.getRed()*80/100),(byte)(color.getGreen()*80/100),(byte)(color.getBlue()*80/100));
			
			glVertex3d(center.getX()-size.getX()/2,center.getY()-size.getY()/2, 0);
			glVertex3d(center.getX()+size.getX()/2, center.getY()-size.getY()/2, 0);
			glVertex3d(center.getX()+size.getX()/2,center.getY()+size.getY()/2, 0);
			glVertex3d(center.getX()-size.getX()/2,center.getY()+size.getY()/2, 0);
			
			GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());
			
			glVertex3d(center.getX()-size.getX()/2+5,center.getY()-size.getY()/2+5, 0);
			glVertex3d(center.getX()+size.getX()/2-5, center.getY()-size.getY()/2+5, 0);
			glVertex3d(center.getX()+size.getX()/2-5,center.getY()+size.getY()/2+-5, 0);
			glVertex3d(center.getX()-size.getX()/2+5,center.getY()+size.getY()/2-5, 0);
			
			GL11.glEnd();
			}
		}else{
		GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());		
		GL11.glTranslated(center.getX(), center.getY(), 0.1);
		disk2.draw(0f, radius, 50, 1,sens*90,180);
		GL11.glColor3ub((byte)(color.getRed()*90/100),(byte)(color.getGreen()*90/100),(byte)(color.getBlue()*90/100));
		disk1.draw(radius-5, radius, 50, 1,sens*90,180);
		GL11.glTranslated(-center.getX(), -center.getY(), -0.1);
		}
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
	public synchronized  ArrayList<DisplayableChild> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public synchronized void addChild(DisplayableChild object) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public synchronized  void removeChild(DisplayableChild child) {
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

	@Override
	public double getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}


	public Point getCenter() {
		return center;
	}

	public Point getBaseSize(){
		return size;
	}

	@Override
	public void setUnTracked(boolean b) {
		// TODO Auto-generated method stub
		
	}
	

	
	

}
