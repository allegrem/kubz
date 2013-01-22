package views;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.Color;

import utilities.Point;
import utilities.RandomPerso;


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
	public BackgroundView(int width, int length) {
		this.width=width;
		this.length=length;

	}

	/**
	 * Changement de la couleur du background
	 * de facon aleatoire et avec un degrade
	 */
	public void change(){
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
	
	public void paint() {
		/*
		 * Sert dans le cas ou l'on utilise une texture pour le background
		 * 
		 * glEnable(GL11.GL_TEXTURE_2D);
		 *if (Textures.texturePath==null)
		 *	Textures.initTexturePath();   
		 *GL11.glColor3f(1.0f,1.0f,1.0f);
		 *Color.white.bind();
		 *Textures.texturePath.bind();
		 */
		
		
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

	




}
