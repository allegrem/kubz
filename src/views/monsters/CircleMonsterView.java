
package views.monsters;

import map2.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;

import utilities.Lines;
import utilities.Maths;
import utilities.Point;
import utilities.Vector;
import views.attacks.SinusoidalAttackView;

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
		this.addChild(new SinusoidalAttackView(45,0,150));
	}

	@Override
	public void paint() {

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte)actualColor.getRed(),(byte)actualColor.getGreen(),(byte)actualColor.getBlue());
		GL11.glTranslated(getX(), getY(), 0);
		cylinder.draw((float)(size/2),0,height, 50, 1);
		GL11.glTranslated(-getX(), -getY(), 0);
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
		return "C";
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
