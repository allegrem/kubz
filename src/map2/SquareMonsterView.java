package map2;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import OpenGL.Displayable;
/**
 * Une unité en forme de carré
 * 
 * @author paul
 *
 */
public class SquareMonsterView extends MonsterView {

	public SquareMonsterView(Point position, ReadableColor color,Map map) {
		super(position, color,map);
	}

	@Override
	public void paint() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glColor3ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue()); // face marron
		
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, 0);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, 0);

		glVertex3d(super.getX()-MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()-MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()+MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		glVertex3d(super.getX()-MonsterView.size/2, super.getY()+MonsterView.size/2, MonsterView.height);
		
		GL11.glEnd();
	}
	
	@Override
	public String getType() {
		return "S";
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

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		
	}


}
