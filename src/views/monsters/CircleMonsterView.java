
package views.monsters;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Cylinder;
import utilities.Maths;
import utilities.Point;
import utilities.Vector;

/**
 * Unite Monstre de type cercle.
 * @author valeh
 */
public class CircleMonsterView extends MonsterView {
	private Cylinder cylinder = new Cylinder();

/**
 * Nouveau monstre de type cercle
 * @param position Le centre du monstre
 * @param color La couleur du monstre
 */
	public CircleMonsterView(Point position,ReadableColor color) {
		super(position,color);
	}

	@Override
	public void paint() {
		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glTranslated(getX(),getY(),0);
		GL11.glRotated((int)Math.round(getAngle()),0,0,1);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte)actualColor.getRed(),(byte)actualColor.getGreen(),(byte)actualColor.getBlue());
		
		cylinder.draw((float)(size/2),0,(float)height, 50, 1);
		
		GL11.glLoadIdentity();
		GL11.glPopMatrix();
		
		paintChildren();
	
	}
	
	@Override
	public boolean isInZone(Point p){
		double pX = p.getX();
		double pY = p.getY();
		/*
		 * On calcule la distance du centre au point
		 */
		double d=Math.hypot(getX()-pX,getY()-pY);
		if(d<=size/2)
			return true;
		return false;
	}
	

	@Override
	public String getCharac() {
		return "C"+super.getCharac();
	}

	@Override
	public boolean collisionCanOccure(Point point, float taille) {
		double dist=size/2;
		Vector vect= Maths.makeVector(point.getX(), point.getY(), 0, getX(), getY(), 0);
		if(dist+taille>=vect.norme())
			return true;
		return false;
	}



}
