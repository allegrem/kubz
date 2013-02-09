package views.staticViews;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.Color;

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
	private long startTime=0;
	private int nbre=10;
	
	/*
	 * Les couleurs de depart du background
	 * (a l'ouverture de la fenetre)
	 */
	private int red1 = 197,blue1 = 226, green1 = 197;
	private int red2 = 197,blue2 = 226, green2 = 197;
	
	/*
	 * Sens de variation des differentes
	 * composantes des couleurs
	 */
	private int sRed1=1;
	private int sGreen1=1;
	private int sBlue1=1;
	private int sRed2=-1;
	private int sGreen2=-1;
	private int sBlue2=-1;
	
	/**
	 * Nouveau background
	 * @param width Largeur
	 * @param length Longueur
	 */
	public BackgroundView(int width, int length,long time) {
		this.width=width;
		this.length=length;
		this.time=time;
		startTime=System.currentTimeMillis();

	}

	/**
	 * Changement de la couleur du background
	 * de facon aleatoire et avec un degrade
	 */
	public void change(){
		if(System.currentTimeMillis()-startTime>=time){
			
		
		startTime=System.currentTimeMillis();
		int alea=RandomPerso.entier(5);
		switch(RandomPerso.entier(3)){
		case 0:
			if(blue1+sBlue1*alea<100 || blue1+sBlue1*alea>255){
			sBlue1*=-1;
			}
			blue1 +=sBlue1*alea;	
			break;
		
		case 1:
			if(green1+sGreen1*alea<100 || green1+sGreen1*alea>255){
				sGreen1*=-1;
				}
				green1 +=sGreen1*alea;	
				break;
		
		case 2:
			if(red1+sRed1*alea<100 || red1+sRed1*alea>255){
				sRed1*=-1;
				}
				red1 +=sRed1*alea;	

				break;
		}
		
		alea=RandomPerso.entier(5);
		switch(RandomPerso.entier(3)){
		case 0:{
			if(blue2+sBlue2*alea<100 || blue2+sBlue2*alea>255){
			sBlue2*=-1;
			}
			blue2 +=sBlue2*alea;	
			break;
		}
		case 1:{
			if(green2+sGreen2*alea<100 || green2+sGreen2*alea>255){
				sGreen2*=-1;
				}
				green2 +=sGreen2*alea;	
				break;
		}
		case 2:{
			if(red2+sRed2*alea<100 || red2+sRed2*alea>255){
				sRed2*=-1;
				}
				red2 +=sRed2*alea;	

				break;
		}
		}
		}
	
		
	}
	
	@Override
	public void paint() {	
		
		 glEnable(GL11.GL_TEXTURE_2D);
		 GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		 GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		 
		 if (Textures.textureSea==null)
		 	Textures.initTexturePath();   
		 GL11.glColor3f(1.0f,1.0f,1.0f);
		 Color.gray.bind();
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
		GL11.glColor3ub((byte)red1,(byte) green1 ,(byte) blue1);
		//GL11.glTexCoord2f(0,0);
		glVertex3d(0, 0, 0);
		
		GL11.glColor3ub((byte) ((red1+red2)/2),(byte) green2 , (byte) ((blue1+blue2)/2));
		//GL11.glTexCoord2f(nbre,0);
		glVertex3d(width, 0, 0);
	
		GL11.glColor3ub((byte)red2, (byte)green2 , (byte) blue2);
		//GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(width, length, 0);
		
		GL11.glColor3ub((byte) ((red1+red2)/2),(byte)green1 ,(byte) ((blue1+blue2)/2));
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
