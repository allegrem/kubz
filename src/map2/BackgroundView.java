package map2;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.Color;

import OpenGL.Displayable;
import OpenGL.Textures;
/**
 * Le sol (chemin)
 * 
 * @author paul
 *
 */
public class BackgroundView implements Displayable{
	private int width;
	private int length;
	//private final ReadableColor color= new Color(200,150,50);
	private int nbre=7;

	public BackgroundView(int width, int length) {
		this.width=width;
		this.length=length;
	}


	public void paint() {
		/*glEnable(GL11.GL_TEXTURE_2D);
		if (Textures.texturePath==null)
			Textures.initTexturePath();   
		GL11.glColor3f(1.0f,1.0f,1.0f);
		Color.white.bind();
		Textures.texturePath.bind();*/
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		 // face marron
		glBegin(GL_QUADS);
		
		GL11.glColor3ub((byte) 200, (byte) 233 , (byte) 256);
		GL11.glTexCoord2f(0,0);
		glVertex3d(0, 0, 0);
		
		GL11.glColor3ub((byte) 200, (byte) 200 , (byte) 256);
		GL11.glTexCoord2f(nbre,0);
		glVertex3d(width, 0, 0);
		
		GL11.glColor3ub((byte) 200, (byte) 233 , (byte) 300);
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(width, length, 0);
		
		GL11.glColor3ub((byte) 200, (byte) 133 , (byte) 256);
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(0,length, 0);
	
		GL11.glEnd();
		
	
		

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
	public boolean isInZone(Point mousePoint) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public void setColor(org.lwjgl.util.Color color) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public String getCharac() {
		// TODO Auto-generated method stub
		return null;
	}





}
