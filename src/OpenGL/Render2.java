/**
 * Rendu des base.
 * @author valeh
 * 
 */


package OpenGL;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import map2.Base;
import map2.MapReader;

import org.lwjgl.opengl.GL11;

/**
 * Rendu numero2
 * 
 * @author valeh
 * 
 */
public class Render2 {
	private int display_width;
	private int display_height;
	private MapReader mapReader;
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
			mapReader = new MapReader("map2.txt");
			this.baseRender = mapReader.getBaseList();
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
		GL11.glScalef(1.0f, GLBaseModule.ratio, 1.0f);  //marche p� :'(  on a une ellipse,pas un cercle...
		GL11.glTranslatef(4.0f,1.0f,0.0f);  //juste un test
		
		for (Base base : baseRender)
			base.paint();
	}
		


}
