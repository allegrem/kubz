package map2;

import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.util.Color;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadableColor;

public class SquareUnit extends Unit {

	public SquareUnit(Point position, ReadableColor color) {
		super(position, color);
	}

	@Override
	public void paint() {
		glColor3ub((byte) actualColor.getRed(), (byte) actualColor.getGreen() , (byte) actualColor.getBlue()); // face marron
		glVertex3d(super.getX()-Unit.size/2, super.getY()-Unit.size/2, 0);
		glVertex3d(super.getX()+Unit.size/2, super.getY()-Unit.size/2, 0);
		glVertex3d(super.getX()+Unit.size/2, super.getY()+Unit.size/2, 0);
		glVertex3d(super.getX()-Unit.size/2, super.getY()+Unit.size/2, 0);

	}

}
