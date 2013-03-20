package views.informationViews;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.PartialDisk;

import base.Base;

import player.parameter.Parameter;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

/**
 * Liens entre le centre de la base et les parametres
 * @author paul
 *
 */
public class Link implements DisplayableChild{
	private double radius=10;
	
	private Base base;
	private Parameter parameter1;
	private Parameter parameter2;
	private double angle1;
	private double angle2;
	private PartialDisk disk= new PartialDisk();

	public Link(Base base, Parameter parameter1, Parameter parameter2) {
		this.base = base;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		computeAngles();
	}
	
	/**
	 * Calcul des angles des parametres autour du centre de la base
	 */
	public void computeAngles()
	{
		double angleP1=Math.atan2((parameter1.getX()-base.getCenter().getX()),parameter1.getY()-base.getCenter().getY());
		double angleP2=Math.atan2((parameter2.getX()-base.getCenter().getX()),parameter2.getY()-base.getCenter().getY());
		angle1=Math.toDegrees(Math.min(angleP1,angleP2));
		angle2=Math.toDegrees(Math.abs(angleP2-angleP1));
		radius=0.33*Math.min(parameter1.getPos().distanceTo(base.getCenter()), parameter2.getPos().distanceTo(base.getCenter()));
		//System.out.println(parameter1.getPos().distanceTo(base.getCenter()));
	}

	@Override
	public void paint() {
	computeAngles();
	GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();

		GL11.glBegin(GL11.GL_LINES);
		
		GL11.glColor3ub((byte)100,(byte)100,(byte)200);
		glVertex3d(base.getCenter().getX(),base.getCenter().getY(), 0.2);
		glVertex3d(parameter1.getX(),parameter1.getY(), 0.2);
		
		glVertex3d(base.getCenter().getX(),base.getCenter().getY(), 0.2);
		glVertex3d(parameter2.getX(),parameter2.getY(), 0.2);
		
		GL11.glEnd();
		
		GL11.glTranslated(base.getCenter().getX(), base.getCenter().getY(), 0.2);
		//disk.draw(0f, (float)radius, 50, 1,(float)angle2,(float)angle1);
		
		
		GL11.glPopMatrix();
		
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
		return "Link";
	}

	@Override
	public boolean collisionCanOccure(Point point, float f) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFather(DisplayableFather father) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}


	
}
