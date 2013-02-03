package views.informationViews;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

/**
 * Affichage de la vie des monstres
 * 
 * @author paul
 *
 */
public class LifeView implements DisplayableChild{
	private DisplayableFather father;
	private double life=100;
	private double width=6;
	private double x;
	private double y;
	private double height;
	private double size;
	
	public LifeView(DisplayableFather father){
		this.father=father;
		x=father.getX();
		y=father.getY();
		height=father.getHeight();
		size=80.0/100.0*father.getSize();
	}
	
	@Override
	public void paint() {
		x=father.getX();
		y=father.getY();
		height=father.getHeight();
		size=80.0/100.0*father.getSize();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		glMatrixMode(GL_MODELVIEW);
		GL11.glTranslated(x,y,0);
		GL11.glRotated(father.getAngle(),0,0,1);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3ub((byte)ReadableColor.GREY.getRed(),(byte)ReadableColor.GREY.getGreen(),(byte)ReadableColor.GREY.getBlue());
		GL11.glVertex3d(-size/2, -width/2, height);
		GL11.glVertex3d(-size/2, width/2, height);
		GL11.glVertex3d(+size/2, width/2, height);
		GL11.glVertex3d(+size/2, -width/2, height);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_QUADS);
		if(life<=33.0){
			GL11.glColor3ub((byte)ReadableColor.RED.getRed(),(byte)ReadableColor.RED.getGreen(),(byte)ReadableColor.RED.getBlue());
		}else if(life<=66.0){ 
			GL11.glColor3ub((byte)ReadableColor.ORANGE.getRed(),(byte)ReadableColor.ORANGE.getGreen(),(byte)ReadableColor.ORANGE.getBlue());
		}else{
			GL11.glColor3ub((byte)ReadableColor.GREEN.getRed(),(byte)ReadableColor.GREEN.getGreen(),(byte)ReadableColor.GREEN.getBlue());
		}
		GL11.glVertex3d(-size/2, -width/2, height);
		GL11.glVertex3d(-size/2, +width/2, height);
		GL11.glVertex3d(+size*(-1.0/2.0+life/100.0), +width/2, height);
		GL11.glVertex3d(+size*(-1.0/2.0+life/100.0), -width/2, height);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glColor3ub((byte)ReadableColor.BLACK.getRed(),(byte)ReadableColor.BLACK.getGreen(),(byte)ReadableColor.BLACK.getBlue());
		GL11.glVertex3d(-size/2, -width/2, height);
		GL11.glVertex3d(-size/2, +width/2, height);
		GL11.glVertex3d(+size/2, +width/2, height);
		GL11.glVertex3d(+size/2, -width/2, height);
		GL11.glVertex3d(-size/2, -width/2, height);
		GL11.glEnd();
		
		GL11.glRotated(-father.getAngle(),0,0,1);
		GL11.glTranslated(-x,-y,0);
		
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
		return "Life= "+life+"%";
	}

	@Override
	public void setFather(DisplayableFather father) {
		this.father=father;
		
	}

}
