package OpenGL;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glShadeModel;

import map2.MapCreator;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import map2.Map;

/**
 * Instructions OpenGL
 * 
 * @author paul
 * 
 */
public class GLDisplay extends Thread{

	
	private final int display_width;
	private final int display_height; 
	public static float ratio;
	private boolean do_run=true;
	private Map map;
	private MapCreator mapCreator;
	
	/**
	 * Lancement de l'affichage
	 * 
	 * 
	 */
	public GLDisplay(int display_width,int display_height,Map map,MapCreator mapCreator){
		this.display_width=display_width;
		this.display_height=display_height;
		this.map=map;
		this.mapCreator=mapCreator;
		ratio = display_width/display_height;
	}

	@Override
	public void run(){
		initialize();
		while(do_run){
			
		if (Display.isCloseRequested())
				do_run = false;
		
		//updateMouse();
		//updateKeyboard();
		clear();
		mapCreator.compute();
		render();
		update();
		Display.sync(120);
		
		}
		/*
		 * Fermeture de la fenêtre
		 */
		close();
		
	}
	
	private void initialize() {
		initDisplay();
		initGL();
		mapCreator.changementMode3D();
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void render(){
		map.paint();
		
	}
	
	public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // On vide le buffer
	}
	
	public void update() {

			Display.update(); // On actualise la fenêtre pour afficher les nouveaux rendus
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
