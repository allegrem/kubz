package views.staticViews;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Point;
import utilities.RandomPerso;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;


import OpenGL.Textures;
/**
 * Le background
 * 
 * @author paul
 *
 */
public class BackgroundView implements DisplayableFather{
	private int width;
	private int length;
	private long time;
	private int nbre=10;
	private Color color1=new Color(0,0,0);
	private Color color2=new Color(0,0,0);
	
	/*
	 * Les couleurs de depart du background
	 * (a l'ouverture de la fenetre)
	 */
	private double h1 ;
	private double h2 ;
	
	/*
	 * Sens de variation des differentes
	 * composantes des couleurs
	 */
	private int sh1=1;
	private int sh2=-1;

	
	/**
	 * Nouveau background
	 * @param width Largeur
	 * @param length Longueur
	 */
	public BackgroundView(int width, int length,long time) {
		this.width=width;
		this.length=length;
		this.time=time;
		h1=time/2;
		h2=time/2;
		color1.fromHSB((float)(h1/time),1f,1f);
		color2.fromHSB((float)(h2/time),1f,1f);
		
	}

	/**
	 * Changement de la couleur du background
	 * de facon aleatoire et avec un degrade
	 */
	public void change(){
	
		int alea=RandomPerso.entier(5);
			if(h1+sh1*alea<0 || h1+sh1*alea>time){
			sh1*=-1;
			}
			h1 +=sh1*alea;	
	
		alea=RandomPerso.entier(5);
		if(h2+sh2*alea<0 || h1+sh2*alea>time){
			sh2*=-1;
			}
			h2 +=sh2*alea;	

		color1.fromHSB((float)(h1/time),1f,1f);
		color2.fromHSB((float)(h2/time),1f,1f);
		
		
	}
	
	@Override
	public void paint() {	
		
		 glEnable(GL11.GL_TEXTURE_2D);
		 GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		 GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		 
		 if (Textures.textureSea==null)
		 	Textures.initTexturePath();   
		 GL11.glColor3f(1.0f,1.0f,1.0f);
		 Textures.textureSea.bind();
		 
		
		
		glBegin(GL_QUADS);
		GL11.glNormal3f(0,0, 1.0f);
		GL11.glTexCoord2f(0,0);
		
		glVertex3d(-1000, -1000, -0.1);
		
		GL11.glTexCoord2f(nbre,0);
		glVertex3d(width+1000, -1000, -0.1);
	
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(width+1000, length+1000, -0.1);
		
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(-1000,length+1000, -0.1);
		
		/********************************/
		
		GL11.glNormal3f(0,1.0f, 0);
		
		GL11.glTexCoord2f(0,0);
		glVertex3d(-1000, -1000, 0);
		
		GL11.glTexCoord2f(nbre,0);
		glVertex3d(width+1000, -1000, 0);
		
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(width+1000, -1000, 1000);
		
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(-1000, -1000, 1000);
		
		
	
		/*********************************/
		
		GL11.glNormal3f(0,-1.0f, 0);
		
		GL11.glTexCoord2f(0,0);
		glVertex3d(-1000,length+1000, 0);
		
		GL11.glTexCoord2f(nbre,0);
		glVertex3d(width+1000, length+1000, 0);
		
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(width+1000, length+1000, 1000);
		
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(-1000,length+1000, 1000);
		
		/************************************/
		
		GL11.glNormal3f(1.0f,0, 0);
		
		GL11.glTexCoord2f(0,0);
		glVertex3d(-1000, -1000, 0);
		
		GL11.glTexCoord2f(nbre,0);
		glVertex3d(-1000,length+1000, 0);
		
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(-1000,length+1000, 1000);
		
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(-1000, -1000, 1000);
		
		/************************************/
		
		GL11.glNormal3f(-1.0f,0, 0);
		
		GL11.glTexCoord2f(0,0);
		glVertex3d(width+1000, -1000, 0);
		
		GL11.glTexCoord2f(nbre,0);
		glVertex3d(width+1000, length+1000, 0);
		
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(width+1000, length+1000, 1000);
		
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(width+1000, -1000, 1000);
		
		
	
		GL11.glEnd();
		

		change();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		glBegin(GL_QUADS);
		GL11.glNormal3f(0,0, 1.0f);
		GL11.glColor3ub((byte)color1.getRed(),(byte) color1.getGreen() ,(byte) color1.getBlue());
		//GL11.glTexCoord2f(0,0);
		glVertex3d(0, 0, 0);
		
		GL11.glColor3ub((byte) ((color1.getRed()+color2.getRed())/2),(byte) color2.getGreen() , (byte) ((color1.getBlue()+color2.getBlue())/2));
		//GL11.glTexCoord2f(nbre,0);
		glVertex3d(width, 0, 0);
	
		GL11.glColor3ub((byte)color2.getRed(), (byte)color2.getGreen() , (byte) color2.getBlue());
		//GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(width, length, 0);
		
		GL11.glColor3ub((byte) ((color1.getRed()+color2.getRed())/2),(byte)color1.getGreen() ,(byte) ((color1.getBlue()+color2.getBlue())/2));
		//GL11.glTexCoord2f(0,nbre);
		glVertex3d(0,length, 0);
	
		GL11.glEnd();
		
	

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
	public boolean isInZone(Point mousePoint) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public void setColor(ReadableColor color) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public String getCharac() {
		
		return "Background";
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized ArrayList<DisplayableChild> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void addChild(DisplayableChild object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void removeChild(DisplayableChild child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean collisionCanOccure(Point point, float taille) {
		// TODO Auto-generated method stub
		return false;
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

	




}
