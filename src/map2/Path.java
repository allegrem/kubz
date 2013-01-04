package map2;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import OpenGL.Textures;
/**
 * Le sol (chemin)
 * 
 * @author paul
 *
 */
public class Path {
	private int width;
	private int length;
	//private final ReadableColor color=new Color(200,150,50);
	private int nbre=7;

	public Path(int width, int length) {
		this.width=width;
		this.length=length;
	}

		


	public void paint() {
		glEnable(GL11.GL_TEXTURE_2D);
		if (Textures.texturePath==null)
			Textures.initTexturePath();
		GL11.glColor3f(1.0f,1.0f,1.0f);
		Color.white.bind();
		Textures.texturePath.bind();
		
		//glColor3ub((byte) color.getRed(), (byte) color.getGreen() , (byte) color.getBlue()); // face marron
		glBegin(GL_QUADS);
		
		GL11.glTexCoord2f(0,0);
		glVertex3d(0, 0, 0);
		GL11.glTexCoord2f(nbre,0);
		glVertex3d(width, 0, 0);
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(width, length, 0);
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(0,length, 0);
	
		GL11.glEnd();
		
	
		

	}





}
