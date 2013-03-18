package demo;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Point;
import utilities.RandomPerso;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

public class Tuile  implements DisplayableChild  {
private int size;
private ReadableColor upColor;
private ReadableColor downColor;
private int angle=0;
private int x;
private int y;
private int speed=0;
private int pos=1;
private int rx=0;
private int ry=0;

public Tuile(int size,int x,int y){
	this.size = size;
	upColor=new Color(0,0,0);
	downColor=randomColor();
	this.x=x;
	this.y=y;
}

public void paint(){
	glMatrixMode(GL_MODELVIEW);
	GL11.glPushMatrix();
	GL11.glTranslatef(x+size/2, y+size/2, 0);
	GL11.glRotated(angle,rx, -ry, 0);
	GL11.glTranslated(-size/2, -size/2, 0);
	
	GL11.glBegin(GL11.GL_QUADS);
	
	GL11.glColor3ub((byte)upColor.getRed(), (byte)upColor.getGreen(), (byte)upColor.getBlue());
	GL11.glVertex3d(0, 0, 0.01);
	GL11.glVertex3d(size, 0, 0.01);
	GL11.glVertex3d(size,size, 0.01);
	GL11.glVertex3d(0, size, 0.01);	
	
	GL11.glColor3ub((byte)downColor.getRed(), (byte)downColor.getGreen(), (byte)downColor.getBlue());
	GL11.glVertex3d(0, 0, -0.01);
	GL11.glVertex3d(size, 0, -0.01);
	GL11.glVertex3d(size,size, -0.01);
	GL11.glVertex3d(0, size, -0.01);	
	
	GL11.glColor3ub((byte)0, (byte)0, (byte)0);
	GL11.glVertex3d(0, 0, -0.01);
	GL11.glVertex3d(0, size, -0.01);
	GL11.glVertex3d(0,size, 0.01);
	GL11.glVertex3d(0, 0, 0.01);	
	
	GL11.glVertex3d(0, size, -0.01);
	GL11.glVertex3d(size, size, -0.01);
	GL11.glVertex3d(size,size, 0.01);
	GL11.glVertex3d(0, size, 0.01);
	
	GL11.glVertex3d(size, size, -0.01);
	GL11.glVertex3d(size, 0, -0.01);
	GL11.glVertex3d(size,0, 0.01);
	GL11.glVertex3d(size, size, 0.01);
	
	GL11.glVertex3d(size, 0, -0.01);
	GL11.glVertex3d(0, 0, -0.01);
	GL11.glVertex3d(0,0, 0.01);
	GL11.glVertex3d(size, 0, 0.01);
	
	GL11.glEnd();
	GL11.glPopMatrix();
	angle+=speed;
	if(angle>=180*pos){
		if(pos==1){
			angle=180;
			pos=2;
			upColor=randomColor();
		}else{
			angle=0;
			pos=1;
			downColor=randomColor();
		}
		speed=0;
	}
	
}

public void rotate(int speed){
	
	if(this.speed==0){
		this.speed=speed;
	
	switch(RandomPerso.entier(4)){
	case 0:
		rx=1;
		ry=0;
		break;
	
	case 1:
		rx=0;
		ry=1;
		break;
	
	case 2:
		rx=1;
		ry=1;
		break;
		
	case 3:
		rx=-1;
		ry=1;
		break;
	}
	}
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

public ReadableColor randomColor(){
	int r=RandomPerso.entier(256);
	int g=RandomPerso.entier(256);
	int b=RandomPerso.entier(256);
	return new Color(r,g,b);
	
}


}