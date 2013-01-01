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

import map2.Path;
import map2.Wall;

import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;

import Map.Map;

/**
 * Rendu numero1
 * 
 * @author paul
 * 
 */
public class Render1 {
	private int display_width;
	private int display_height;
	private Map map;
	private int l;
	private int h;

	/**
	 * Effectue le rendu numero 1
	 * 
	 * @param display_width
	 *            Largeur de la fenetre
	 * @param display_height
	 *            Hauteur de la fenetre
	 */
	public Render1(int display_width, int display_height) {
		this.display_width = display_width;
		this.display_height = display_height;

		try {
			map = new Map("map.txt");
			l = map.getWidth();
			h = map.getHeight();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

	}

	/**
	 * 
	 * Effectue le rendu 1
	 * 
	 */
	public void render() {
		glViewport(0, 0, display_width, display_height); // On définie la place
															// que prendra le
															// rendu dans la
															// fenêtre
		/* Matrice de modele (e.g. positionnement initial de la "camera" ) */
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		// GLU.gluLookAt((l-1)/2+1, (float) ((h-1.0001)/2+1), 8.5f, (l-1)/2+1,
		// (h-1)/2+1, 0, 0, 0, 1); // Positionnement de la caméra
		/* Rotation du cube */

		glBegin(GL_QUADS); // Définition du type de liaison entre les points
		for (int i = 0; i < l; i++) {
			/*
			 * for (int j = 0; j < h; j++) { map.returnCase(i, j).paint(); }
			 */
			new Path(100,100).paint();
			new Wall(new Point(10, 10), new Point(50, 50), 5).paint();
		}
		glEnd();
	}

	public double getl() {

		return l;
	}

	public double geth() {

		return h;
	}
}
