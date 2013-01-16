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

import utilities.Point;

/**
 * Une unité en forme de carré
 * 
 * @author paul
 *
 */
public class SquareMonsterView extends MonsterView {

	public SquareMonsterView(Point position, ReadableColor color,Map map) {
		super(position, color,map);
		this.addChild(new AttackCone(30,0,150));
	}

	@Override
	public void paint() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glColor3ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue()); 
		
		GL11.glNormal3f(0, 0, -1.0f);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		GL11.glNormal3f(-1.0f, 0, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		
		GL11.glNormal3f(0, 1.0f, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		GL11.glNormal3f(1.0f, 0, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		
		GL11.glNormal3f(0, -1.0f, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);

		GL11.glNormal3f(0, 0, 1.0f);
		glColor3ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue());
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glColor3ub((byte) (actualColor.getRed()+50), (byte) (actualColor.getGreen()+50) , (byte) (actualColor.getBlue()+50));
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glColor3ub((byte) 255, (byte) 255, (byte) 255);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glColor3ub((byte) (actualColor.getRed()+50), (byte) (actualColor.getGreen()+50) , (byte) (actualColor.getBlue()+50));
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		
		GL11.glEnd();
		paintChildren();
	}
	
	@Override
	public String getType() {
		return "S";
	}



}
