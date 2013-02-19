package views.informationViews;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

public class MobileBase implements DisplayableChild{
	private DisplayableFather father;
	private Disk disk;

	public MobileBase(DisplayableFather father){
		this.father=father;
		disk=new Disk();
	}
	
	@Override
	public void paint() {
		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glTranslated(father.getX(),father.getY(),0);
		GL11.glColor4f(Color.red.getRed(), Color.red.getGreen(), Color.red.getRed(),120f);
		disk.draw(0,30,50,1);
		GL11.glLoadIdentity();
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
		return "MobileBase";
	}

	@Override
	public void setFather(DisplayableFather father) {
	this.father=father;
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean collisionCanOccure(Point point, float f) {
		// TODO Auto-generated method stub
		return false;
	}

}
