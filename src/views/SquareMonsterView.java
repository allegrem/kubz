package views;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import map2.Map;

import org.lwjgl.opengl.GL11;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import utilities.Maths;
import utilities.Point;
import utilities.Vector;

/**
 * Une unité en forme de carré
 * 
 * @author paul
 *
 */
public class SquareMonsterView extends MonsterView {
	private long direction=0;
	
	public SquareMonsterView(Point position, ReadableColor color) {
		super(position, color);
		this.addChild(new AttackCone(30,0,150));
	}

	@Override
	public void paint() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable (GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);    
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		glBegin(GL_QUADS);
		
		GL11.glNormal3f(0, 0, -1.0f);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		GL11.glNormal3f(-1.0f, 0, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		
		GL11.glNormal3f(0, 1.0f, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		GL11.glNormal3f(1.0f, 0, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		
		GL11.glNormal3f(0, -1.0f, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		GL11.glColor4ub((byte) (actualColor.getRed()*100/100), (byte) (actualColor.getGreen()*100/100) , (byte) (actualColor.getBlue()*100/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);

		GL11.glNormal3f(0, 0, 1.0f);
		GL11.glColor4ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue(),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*90/100), (byte) (actualColor.getGreen()*90/100) , (byte) (actualColor.getBlue()*90/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*75/100), (byte) (actualColor.getGreen()*75/100) , (byte) (actualColor.getBlue()*75/100),(byte)255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		GL11.glColor4ub((byte) (actualColor.getRed()*60/100), (byte) (actualColor.getGreen()*60/100) , (byte) (actualColor.getBlue()*60/100),(byte)255);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		
		GL11.glEnd();
		
		GL11.glDisable (GL11.GL_BLEND); 
		GL11.glDisable(GL11.GL_ALPHA_TEST);  
		
		paintChildren();

		direction++;
		for(DisplayableChild child:getChildren()){
			((AttackCone) (child)).setDirection(direction);
		}

	}
	
	@Override
	public String getType() {
		return "S";
	}

	@Override
	public boolean collisionCanOccure(Point point, float taille) {
		double dist=size*Math.sqrt(2)/2;
		Vector vect= Maths.makeVector(point.getX(), point.getY(), 0, getX(), getY(), 0);
		if(dist+taille>=vect.norme())
			return true;
		return false;
	}



}
