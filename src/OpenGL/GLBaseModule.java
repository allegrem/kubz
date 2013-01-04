package OpenGL;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;

import map2.MapCreator;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * Instructions OpenGL
 * 
 * @author paul
 * 
 */
public class GLBaseModule {

	private final int display_width;
	private final int display_height; 
	public static float ratio;
	
	/**
	 * Lancement de l'affichage
	 * 
	 * 
	 */
	public GLBaseModule(int display_width,int display_height){
		this.display_width=display_width;
		this.display_height=display_height;
		ratio = display_width/display_height;
		initDisplay();
		initGL();
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // On vide le buffer
	}
	
	public void update() {

			Display.update(); // On actualise la fenêtre pour afficher les							// nouveaux rendus
		}
	
	public void close(){
		Display.destroy();
		Keyboard.destroy();
	}


	/**
	 * Création de la fenêtre
	 * 
	 */
	private void initDisplay() {
		try {
			// Creation d'une fenetre permettant de dessiner avec OpenGL
			Display.setDisplayModeAndFullscreen(new DisplayMode(display_width,
					display_height));
			
			Display.create();
			Display.setVSyncEnabled(true);

		} catch (Exception e) {
			System.out.println("Error setting up display: " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * 
	 * Initialisation de la librairie
	 */
	private void initGL() {
		/*
		 * Matrice de projection (3D vers 2D): utilisation d'une projection
		 * perspective
		 */
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		if(MapCreator.MODE3D)
			GLU.gluPerspective(70.0f, display_width / display_height, 1.0f,10000.0f);
		else
			glOrtho(0, display_width, display_height, 0, 1, -1);
		/* Diverses options OpenGL */
		glShadeModel(GL_SMOOTH);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	
}
