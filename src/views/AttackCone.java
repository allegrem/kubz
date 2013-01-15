package views;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import utilities.Point;

public class AttackCone implements DisplayableChild {
	private DisplayableFather father;
	private double angle;
	private double direction;
	private double power;
	
	public AttackCone(double angle, double direction, double power){
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
		angle*=2*Math.PI/360;
		direction*=2*Math.PI/360;
		glBegin(GL11.GL_TRIANGLES);
		glColor3ub((byte)Color.RED.getRed(),(byte)Color.RED.getGreen(),(byte)Color.RED.getBlue());
		glVertex3d(father.getX(),father.getY(),MonsterView.height/2);
		glVertex3d(power*Math.sin(direction+angle)+father.getX(),power*Math.cos(direction+angle)+father.getY(),MonsterView.height/2);
		glVertex3d(power*Math.sin(-(angle-direction))+father.getX(),power*Math.cos(-(angle-direction))+father.getY(),MonsterView.height/2);
		GL11.glEnd();

	}

	public void setDirection( long direction){
		this.direction=direction;
	}
	
	public void setAngle(double Angle){
		this.angle=angle;
	}
	
	public void setPower(double power){
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
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCharac() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
