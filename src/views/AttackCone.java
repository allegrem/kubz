package views;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.PartialDisk;

import utilities.Point;

public class AttackCone implements DisplayableChild {
	private DisplayableFather father;
	private double angle;
	private double direction;
	private int power;
	private int start=0;
	
	public AttackCone(double angle, double direction, int power){
		this.angle=angle;
		this.direction=direction;
		this.power=power;
	}
	
	@Override
	public void setFather( DisplayableFather father){
		this.father=father;
	}
	
	public void paint(){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int alpha;
		
		GL11.glEnable (GL11.GL_BLEND); 
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_ALPHA_TEST);    
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_ALWAYS, 1.0f);
		for(float i=start;i<=(power-5);i+=10){
		alpha=Math.round((power-i)/power*100);
		GL11.glColor4b((byte)Color.RED.getRed(),(byte)Color.RED.getGreen(),(byte)Color.RED.getBlue(),(byte)alpha);
		GL11.glTranslated(father.getX(), father.getY(),MonsterView.height/2 );
		new PartialDisk().draw((float) i,(float) (i+5), 50,1,(float)(direction-angle/2),(float) angle);
		GL11.glTranslated(-father.getX(), -father.getY(),-MonsterView.height/2 );
		}
		start++;
		start %=10;
		
		GL11.glDisable (GL11.GL_BLEND); 
		GL11.glDisable(GL11.GL_ALPHA_TEST);  
		GL11.glAlphaFunc(GL11.GL_EQUAL, 1.0f);
		GL11.glDepthMask(true); 
	}

	public void setDirection( long direction){
		this.direction=direction;
	}
	
	public void setAngle(double Angle){
		this.angle=angle;
	}
	
	public void setPower(int power){
		this.power=power;
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

	

}
