/**
 * Unité Monstre de type cercle.
 * @author valeh
 */

package map2;


import static org.lwjgl.opengl.GL11.glColor3ub;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;

public class CircleUnit extends Unit {

	public CircleUnit(Point position, ReadableColor color,Map map) {
		super(position, color,map);

	}

	@Override
	public void paint() {
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glColor3f(1.0f,0.0f,0.0f); 
		
		double xCenter = super.getX();
		double yCenter = super.getY();
		
		GL11.glVertex3d(xCenter, yCenter, 0.0);
		GL11.glEnd();

	}

}
