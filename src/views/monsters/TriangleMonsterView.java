package views.monsters;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;

import org.lwjgl.util.ReadableColor;
import utilities.Maths;
import utilities.Point;
import utilities.Vector;

/**
 * Une unite en forme de triangle
 * 
 * @author paul
 *
 */
public class TriangleMonsterView extends MonsterView {
	

/**
 * Noueau monstre triangle
 * @param position Sa position (centre)
 * @param color Sa couleur
 */
	public TriangleMonsterView(Point position, ReadableColor color) {
		super(position, color);
	}

	@Override
	public void paint() {
		actualizePosition();
		glColor3ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue()); 
		
		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glTranslated(getX(),getY(),0);
		GL11.glRotated((int)Math.round(getAngle()),0,0,1);
		
		glBegin(GL11.GL_TRIANGLES);
		
		GL11.glNormal3f(0, 0, -1.0f);
		glVertex3d(0,0-MonsterView.size/2, 0);
		glVertex3d(0-MonsterView.size/2, 0+MonsterView.size/2, 0);
		glVertex3d(0+MonsterView.size/2, 0+MonsterView.size/2, 0);
		
		
		Vector vect1= Maths.makeNormalizedVector(0-MonsterView.size/2, 0+MonsterView.size/2, 0.0f,
				0, 0, (float)height);
		Vector vect2= Maths.makeNormalizedVector(0-MonsterView.size/2, 0+MonsterView.size/2, 0.0f,
				0+MonsterView.size/2, 0+MonsterView.size/2,0.0f);
		Vector normal=Maths.vect(vect1,vect2);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ()));
		glVertex3d(0,0,height);
		glVertex3d(0-MonsterView.size/2, 0+MonsterView.size/2, 0);
		glVertex3d(0+MonsterView.size/2, 0+MonsterView.size/2, 0);
		
		
		vect1= Maths.makeNormalizedVector(0-MonsterView.size/2, 0+MonsterView.size/2, 0.0f,
				0, 0, (float)height);
		vect2= Maths.makeNormalizedVector(0-MonsterView.size/2, 0+MonsterView.size/2, 0.0f,
				0, 0-MonsterView.size/2,0.0f);
		normal=Maths.vect(vect2,vect1);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ()));
		glVertex3d(0,0,height);
		glVertex3d(0-MonsterView.size/2, 0+MonsterView.size/2, 0);
		glVertex3d(0, 0-MonsterView.size/2, 0);
		
		
		vect1= Maths.makeNormalizedVector(0+MonsterView.size/2, 0+MonsterView.size/2, 0.0f,
				0, 0, (float)height);
		vect2= Maths.makeNormalizedVector(0+MonsterView.size/2, 0+MonsterView.size/2, 0.0f,
				0, 0-MonsterView.size/2,0.0f);
		normal=Maths.vect(vect1,vect2);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ()));
		glVertex3d(0,0,height);
		glVertex3d(0+MonsterView.size/2, 0+MonsterView.size/2, 0);
		glVertex3d(0, 0-MonsterView.size/2, 0);
		GL11.glEnd();
		
		GL11.glLoadIdentity();
		GL11.glPopMatrix();
		
		paintChildren();
	}

	@Override
	public String getCharac() {
		return "T"+super.getCharac();
	}

	@Override
	public boolean collisionCanOccure(Point point, float taille) {
		double dist=size*Math.sqrt(2)/2;
		Vector vect= Maths.makeVector(point.getX(), point.getY(), 0, getX(), getY(), 0);
		if(dist+taille>=vect.norme())
			return true;
		return false;
	}

	@Override
	public void setUnTracked(boolean b) {
		// TODO Auto-generated method stub
		
	}


	//There's a need to have normals (/!\ normalized vectors) for each side to let OpenGL know which direction each side is facing and 
	//knowing the position of the light, deduce their relative positions (light and face) and render the right 
	//effect of light on the surface


}
