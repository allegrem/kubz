package views.monsters;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;

import org.lwjgl.util.ReadableColor;

import utilities.Maths;
import utilities.Point;
import utilities.Vector;

/**
 * Une unite en forme de carre
 * 
 * @author paul
 *
 */
public class SquareMonsterView extends MonsterView {
	private long direction=0;
	
	/**
	 * Nouveau monstre carre
	 * @param position Sa position (centre)
	 * @param color Sa couleur
	 */
	public SquareMonsterView(Point position, ReadableColor color) {
		super(position, color);
		
	}

	@Override
	public void paint() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable (GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);    
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glTranslated(getX(),getY(),0);
		GL11.glRotated((int)Math.round(getAngle()),0,0,1);
		
		glBegin(GL_QUADS);
		
		GL11.glNormal3f(0, 0, -1.0f);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(-MonsterView.size/2, -MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(+MonsterView.size/2, -MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(+MonsterView.size/2, +MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(-MonsterView.size/2, +MonsterView.size/2, 0);
		
		GL11.glNormal3f(-1.0f, 0, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(-MonsterView.size/2, -MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(-MonsterView.size/2, -MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(-MonsterView.size/2, +MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(-MonsterView.size/2, +MonsterView.size/2, 0);
		
		
		GL11.glNormal3f(0, 1.0f, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(-MonsterView.size/2, +MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(-MonsterView.size/2, +MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(+MonsterView.size/2, +MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(+MonsterView.size/2, +MonsterView.size/2, 0);
		
		GL11.glNormal3f(1.0f, 0, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(+MonsterView.size/2, +MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(+MonsterView.size/2, +MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(+MonsterView.size/2, -MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(+MonsterView.size/2, -MonsterView.size/2, MonsterView.height);
		
		GL11.glNormal3f(0, -1.0f, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(+MonsterView.size/2, -MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(+MonsterView.size/2, -MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(-MonsterView.size/2, -MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(-MonsterView.size/2, -MonsterView.size/2, 0);

		GL11.glNormal3f(0, 0, 1.0f);
		GL11.glColor4ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue(),(byte)255);
		glVertex3d(-MonsterView.size/2, -MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(+MonsterView.size/2, -MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(+MonsterView.size/2, +MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(-MonsterView.size/2, +MonsterView.size/2, MonsterView.height);
		
		GL11.glEnd();
		
		GL11.glLoadIdentity();
		GL11.glPopMatrix();
		
		GL11.glDisable (GL11.GL_BLEND); 
		GL11.glDisable(GL11.GL_ALPHA_TEST);  
		
		paintChildren();


	}
	
	@Override
	public String getCharac() {
		return "S"+super.getCharac();
	}

	@Override
	public boolean collisionCanOccure(Point point, float taille) {
		double dist=size*Math.sqrt(2)/2;
		Vector vect= Maths.makeVector(point.getX(), point.getY(), 0, getX(), getY(), 0);
		if(dist+taille>=vect.norme())
			return true;
		return false;
	}



}
