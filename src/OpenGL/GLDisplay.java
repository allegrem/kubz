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
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import map2.Map;

/**
 * Instructions OpenGL:
 * Sert a charger la librairie
 * et a gerer l'affichage
 * @author paul
 * 
 */
public class GLDisplay extends Thread{

	
	private final int display_width;
	private final int display_height; 
	public static float ratio; // display_width/display_height
	private boolean do_run=true;
	private Map map;
	
	/**
	 * Lancement de l'affichage
	 * 
	 * 
	 */
	public GLDisplay(int display_width,int display_height,Map map){
		this.display_width=display_width;
		this.display_height=display_height;
		this.map=map;
		ratio = display_width/display_height;
	}

	/**
	 *Methode tournant en continu pendant l'execution
	 *du thread 
	 */
	@Override
	public void run(){
		initialize();
		while(do_run){
			
		if (Display.isCloseRequested())
				do_run = false; // On arrete le programme
		clear(); //On nettoie la fenetre
		render(); //Rendu de la map
		update(); //On actualise la fenetre avec le nouveau rendu
		Display.sync(120); //On synchronise l'affichage sur le bon FPS
		
		}
		
		close();//Fermeture de la fenêtre
		
	}
	
	/**
	 * Initialisation de l'OpenGL
	 */
	private void initialize() {
		initDisplay();
		initGL();
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Rendu de la map
	 */
	public void render(){
		map.paint();
		
	}
	
	/**
	 * Nettoyage de l'affichage
	 */
	public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // On vide le buffer
	}
	
	/**
	 * Mise a jour de l'affichage
	 */
	public void update() {

			Display.update(); // On actualise la fenêtre pour afficher les nouveaux rendus
		}
	
	/**
	 * Fermeture de la fenetre
	 */
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
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, display_width, display_height, 0, -1000, 1000);

	}

	/**
	 * 
	 * Initialisation de la librairie
	 */
	private void initGL() {
		/* Diverses options OpenGL */
		glShadeModel(GL_SMOOTH);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL11.GL_COLOR_MATERIAL);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
	}

	
}
