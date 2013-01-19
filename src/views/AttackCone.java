package views;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import map2.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.PartialDisk;

import utilities.Point;
import utilities.Vector;

public class AttackCone implements DisplayableChild {
	private DisplayableFather father;
	private double angle;
	private double direction;
	private int power;
	private int start=0;
	private ReadableColor color=Color.GREY;
	private long pause=30;
	private long startingTime=0;
	
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
		boolean reflected =false;
		double beta=0;
		double x=0;
		double y=0;
		int fin=power-5;
		GL11.glEnable (GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);    
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for(float i=start;i<=(power-5);i+=10){
		alpha=Math.round((power-i)/power*255);
		
		if(!reflected){
		collision : for (DisplayableFather object: Map.getMap().getObjects()){
			if (object !=father && object.collisionCanOccure(new Point(father.getX(),father.getY()),i+5)){
				beta=direction-angle/2;
			while(beta<=angle ){	
				y=father.getY()+(i+5)*Math.cos(Math.PI/180*beta);
				x=father.getX()+(i+5)*Math.sin(Math.PI/180*beta);
				if (object.isInZone(new Point(x,y))){
					reflected=true;
					fin=Math.round(i-5);
					break collision;
				}
				beta+=10;	
			}
			}
		}
		}
		}
		
		for(float i=start;i<=fin;i+=10){
			alpha=Math.round((fin-i)/fin*255);
		GL11.glColor4ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue(),(byte)alpha);
		GL11.glTranslated(father.getX(), father.getY(),MonsterView.height/2 );
		new PartialDisk().draw((float) i,(float) (i+5), 50,1,(float)(direction+-angle/2),(float) angle);
		GL11.glTranslated(-father.getX(), -father.getY(),-MonsterView.height/2 );
		}
		

		if(System.currentTimeMillis()-startingTime>pause){
		start++;
		start %=10;
		startingTime=System.currentTimeMillis();
		}
		GL11.glDisable (GL11.GL_BLEND); 
		GL11.glDisable(GL11.GL_ALPHA_TEST);  
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
	this.color=color;
		
	}

	@Override
	public String getCharac() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
