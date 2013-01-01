package map2;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadableColor;

public class ShapeUnit extends Unit {

	public ShapeUnit(Point position, ReadableColor color) {
		super(position, color);

	}

	@Override
	public void paint() {
		
		glBegin(GL11.GL_TRIANGLES);
		glColor3ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue()); // face marron
		glVertex3d(super.getX(),super.getY()-Unit.size/2, 0);
		glVertex3d(super.getX()-Unit.size/2, super.getY()+Unit.size/2, 0);
		glVertex3d(super.getX()+Unit.size/2, super.getY()+Unit.size/2, 0);

		GL11.glEnd();
	}

}
