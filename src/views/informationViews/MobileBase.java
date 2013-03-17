package views.informationViews;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;

import utilities.Maths;
import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

public class MobileBase implements DisplayableChild{
	private DisplayableFather father;
	private Disk[] disks = new Disk[4];

	public MobileBase(DisplayableFather father){
		this.father=father;
		for (int i=0;i<4;i++)
			disks[i]=new Disk();
		
	}
	
	@Override
	public void paint() {
		glMatrixMode(GL_MODELVIEW);
			
		GL11.glPushMatrix();
		GL11.glTranslated(father.getX(),father.getY(),0);
		//GL11.glColor4f(Color.red.getRed(), Color.red.getGreen(), Color.red.getRed(),120f);
		GL11.glEnable(GL11.GL_BLEND);		
		GL11.glEnable(GL11.GL_ALPHA_TEST);    
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glColor4ub((byte)194,(byte)122,(byte)233,(byte)200);
		disks[0].draw(0,30,50,1);
		GL11.glColor4ub((byte)194,(byte)122,(byte)233,(byte)255);
		disks[1].draw(30,37,50,1);
		
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
