package OpenGL;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import map2.Base;
import map2.Map2;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import Map.Map;

/**
 * Rendu numero2
 * 
 * @author valeh
 * 
 */
public class Render2 {
	private int display_width;
	private int display_height;
	private Map2 map2;
	private ArrayList<Base> baseRender = new ArrayList<Base>();

	/**
	 * Effectue le rendu numero 2
	 * 
	 * @param display_width
	 *            Largeur de la fenetre
	 * @param display_height
	 *            Hauteur de la fenetre
	 */
	public Render2(int display_width, int display_height) {
		this.display_width = display_width;
		this.display_height = display_height;

		try {
			map2 = new Map2("map2.txt");
			this.baseRender = map2.getBaseList();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	
	}

	/**
	 * 
	 * Effectue le rendu 2
	 * 
	 */
	public void render2() {
		glViewport(0, 0, display_width, display_height); 
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GL11.glScalef(1.0f, GLBaseModule.ratio, 1.0f);
		GL11.glTranslatef(4.0f,1.0f,0.0f);
		for (Base base : baseRender)
			base.paint();
	}
		


}
