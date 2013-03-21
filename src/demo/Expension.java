package demo;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

/**
 * Un cercle part du centre de l'ecran et s'aggrandit jusqu'a colorier tout
 * le background
 * @author berthier
 *
 */
public class Expension implements DisplayableChild{
	
	private Point center;
	private int size;
	private double radius;
	private int bColor=255;
	private int color=0;
	private int color2=235;
	private int sens=1;
	private int v=1;
	private double r=0;
	private Disk disk=new Disk();
	private boolean start=false;

	public Expension(Point center,int size){
		this.center=center;
		this.size=size;
		computeRadius();
	}

	private void computeRadius() {
		radius = Math.hypot(Math.max(center.getX(),size-center.getX()), Math.max(center.getY(),size-center.getY()));
		
	}

	@Override
	public void paint() {
		/*if(color>=10 && bColor>=10){
			if(r+v*sens>radius-10){
				sens=-1;
				bColor=color-10;
				r=radius-10;
				color2=color+10;
			}else if(r+v*sens<0){
				sens=+1;
				color=bColor-10;
				color2=color-10;
				r=0;
			}else{
				r+=v*sens;
			}
		}else{
			color=0;
			color2=0;
			bColor=0;
		}*/
		
		paintBackground();
		
		if(start){
			if(r+v<=radius){
				r+=v;
			}else{
				r=radius;
				bColor=0;
				start=false;
			}
		
		
		
		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glTranslated(center.getX(), center.getY(), 0.2);
		
		GL11.glColor3ub((byte)255, (byte)color, (byte)color);
		disk.draw(0, (float)r, 100, 10);
		//GL11.glColor3ub((byte)255, (byte)color2, (byte)color2);
		//disk.draw((float)r, (float)(r+10), 100, 10);
		GL11.glPopMatrix();
		}
	}
	
	public void start(){
		start=true;
	}

	public void paintBackground(){
		GL11.glColor3ub((byte)255, (byte)bColor, (byte)bColor);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(0, 0, 0);
		GL11.glVertex3d(size, 0, 0);
		GL11.glVertex3d(size,size, 0);
		GL11.glVertex3d(0, size, 0);	
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
		// TODO Auto-generated method stub
		return null;
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
