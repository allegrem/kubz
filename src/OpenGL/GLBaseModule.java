package OpenGL;

import java.io.FileInputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ARBFragmentShader;

/**
 * Instructions OpenGL
 * 
 * @author paul
 * 
 */
public class GLBaseModule {

	private boolean do_run = true; // runs until done is set to true
	private int display_width = 640;
	private int display_height = 480;
	private Render1 render1 = new Render1(display_width, display_height);

	/**
	 * Lancement de l'affichage
	 * 
	 * 
	 */
	public GLBaseModule() {
		initDisplay();
		initGL();
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (do_run) {
			if (Display.isCloseRequested())
				do_run = false;

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // On vide le
																// buffer avant
																// actualisation
																// de
																// l'affichage
			render1.render(); // On effectue le rendu de l'affichage 1
			Display.update(); // On actualise la fenêtre pour afficher les
								// nouveaux rendus
			Display.sync(120); // On fait une pose de façcon à ce que
								// l'affichage s'actualise à 120 FPS

		}
		Keyboard.destroy();
		Display.destroy();
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

			DisplayMode mode = Display.getDisplayMode();
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
		// GLU.gluPerspective(70.0f, display_width / display_height, 5f,100.0f);
		glOrtho(0, render1.getl(),render1.geth(), 0, 1, -1);
		/* Diverses options OpenGL */
		glShadeModel(GL_SMOOTH);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}

	public static void main(String[] args) {
		new GLBaseModule();
	}
}
