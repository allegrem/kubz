package map2;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

public class Path {
	private int width;
	private int length;
	private final ReadableColor color=new Color(200,150,50);

	public Path(int width, int length) {
		this.width=width;
		this.length=length;
	}

	public void paint() {
		glBegin(GL_QUADS);
		glColor3ub((byte) color.getRed(), (byte) color.getGreen() , (byte) color.getBlue()); // face marron
		glVertex3d(0, 0, 0);
		glVertex3d(width, 0, 0);
		glVertex3d(width, length, 0);
		glVertex3d(0,length, 0);
		GL11.glEnd();
		

	}

}
