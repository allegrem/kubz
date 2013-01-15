package views;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import map2.Map;

import org.lwjgl.opengl.GL11;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Cylinder;

import utilities.Maths;
import utilities.Point;
import utilities.Vector;

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
		
		GL11.glNormal3f(0, 0, -1.0f);
		glVertex3d(super.getX(),super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		
		Vector vect1= Maths.makeVector(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0.0f,
				super.getX(), super.getY(), (float)height);
		Vector vect2= Maths.makeVector(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0.0f,
				super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2,0.0f);
		Vector normal=Maths.vect(vect1,vect2);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ()));
		glVertex3d(super.getX(),super.getY(),height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		
		vect1= Maths.makeVector(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0.0f,
				super.getX(), super.getY(), (float)height);
		vect2= Maths.makeVector(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0.0f,
				super.getX(), super.getY()-MonsterView.size/2,0.0f);
		normal=Maths.vect(vect2,vect1);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ()));
		glVertex3d(super.getX(),super.getY(),height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX(), super.getY()-MonsterView.size/2, 0);
		
		
		vect1= Maths.makeVector(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0.0f,
				super.getX(), super.getY(), (float)height);
		vect2= Maths.makeVector(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0.0f,
				super.getX(), super.getY()-MonsterView.size/2,0.0f);
		normal=Maths.vect(vect1,vect2);
		glVertex3d(super.getX(),super.getY(),height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX(), super.getY()-MonsterView.size/2, 0);
		
		GL11.glEnd();
		paintChildren();
	}

	@Override
	public String getType() {
		return "T";
	}

	

}
