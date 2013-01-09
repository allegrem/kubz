package views;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import map2.Map;
import map2.Point;

import org.lwjgl.opengl.GL11;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Cylinder;

import OpenGL.Displayable;
/**
 * Une unité en forme de triangle
 * 
 * @author paul
 *
 */
public class ShapeMonsterView extends MonsterView {
	


	public ShapeMonsterView(Point position, ReadableColor color,Map map) {
		super(position, color,map);
	}

	@Override
	public void paint() {
		glColor3ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue()); 
		glBegin(GL11.GL_TRIANGLES);
		
		glVertex3d(super.getX(),super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		glVertex3d(super.getX(),super.getY(),height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		glVertex3d(super.getX(),super.getY(),height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX(), super.getY()-MonsterView.size/2, 0);
		
		glVertex3d(super.getX(),super.getY(),height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX(), super.getY()-MonsterView.size/2, 0);
		
		GL11.glEnd();
	}

	@Override
	public String getType() {
		return "T";
	}

	@Override
	public Displayable getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(Displayable object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setIndex() {
		// TODO Auto-generated method stub
		
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

	

}
