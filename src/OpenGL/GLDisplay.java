package OpenGL;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
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

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import player.Player;

import utilities.MyBuffer;
import views.staticViews.BackgroundView;

import map2.Map;

/**
 * Instructions OpenGL:
 * Sert a charger la librairie
 * et a gerer l'affichage
 * @author paul
 * 
 */
public class GLDisplay extends Thread{


	private static final MyBuffer MyFloatBuffer = null;
	private int display_width=700;
	private int display_height=500; 
	private int mapDisplay_width=0;
	private int mapDisplay_height=0;
	private boolean do_run=true;
	private Map map;
	private Sound sound
	//private Sound sound;
	private int frequency=50;
	private  boolean initialized=false;
	private AudioRender audioRender;
	
	/**
	 * Parametres de la projection
	 */
	private float camx=-20.0f;
	private float camy=-20.0f;
	private float camz=20.0f;
	private float camDx;
	private float camDy;
	private float camDz=0.0f;
	private boolean mode3D=false;
	private boolean modeChanged=false;
	
	/*
	 * Parametres de l'eclairage
	 */
	private float lightx=-20.0f;
	private float lighty=-20.0f;
	private float lightz=20.0f;
	private float lightDx;
	private float lightDy;
	private float lightDz=0.0f;
	private Lighting lighting=new Lighting(this);
	
	
	/**
	 * Lancement de l'affichage
	 * 
	 * 
	 */
	public GLDisplay(){
		
	}

	/**
	 *Methode tournant en continu pendant l'execution
	 *du thread 
	 */
	@Override
	public void run(){
		//audioRender=new AudioRender(this,sound);
		initialize();
		mapDisplay_height=(int)(80.0/100.0*display_height);
		mapDisplay_width=display_height;
		
		lightDx=(float)(display_width/2.0);
		lightDy=(float)(display_height/2.0);
		camDx=(float)(display_width/2.0);
		camDy=(float)(display_height/2.0);
		initialized=true;
		while(do_run){
			
		if (Display.isCloseRequested()||KeyboardManager.quit)
				do_run = false; // On arrete le programme
		clear(); //On nettoie la fenetre
		KeyboardManager.checkKeyboard();
		
		glMatrixMode(GL_MODELVIEW);
		setLightPosition();
		
		if(modeChanged)
			changeViewMode();
		
		if(mode3D)
			setCameraDiection();
		
		mainRender(); //On actualise la fenetre avec le nouveau rendu
		//audioRender.renderAudioView();
		//audioRender.renderSpectrumView();
		update(); //On actualise la fenetre avec le nouveau rendu
		Display.sync(frequency); //On synchronise l'affichage sur le bon FPS
	
		
		
		}
		
		close();//Fermeture de la fenêtre
		
	}
	
	private void infosRender() {
		
		
	}

	/**
	 * Initialisation de l'OpenGL
	 */
	private void initialize() {
		initDisplay();
		initGL();
		lighting.enable();
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
	public void mainRender(){
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
			DisplayMode mode = Display.getDesktopDisplayMode();
			display_width = mode.getWidth();
            display_height = mode.getHeight();
            frequency = mode.getFrequency();
			// Creation d'une fenetre permettant de dessiner avec OpenGL
			Display.setDisplayModeAndFullscreen(mode);
			Display.setTitle("Kubz");
			ByteBuffer[] list = new ByteBuffer[2];
			list[0] = MyBuffer.convertImageData(ImageIO.read(new File("Icone/Kubz32.jpeg")));
			list[1] = MyBuffer.convertImageData(ImageIO.read(new File("Icone/Kubz16.gif")));
			Display.setIcon(list);
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
		GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
		GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT,GL11.GL_NICEST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL11.GL_COLOR_MATERIAL);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
	}

	
	/**
	 * Met a jour l'emplacement et la direction de l'eclairage
	 */
	public void setLightPosition(){
		lighting.setLightDirection(lightDx, lightDy, lightDz);
		lighting.placeLighting(lightx, lighty, lightz);
	}
	
	/**
	 * Definie la position de l'eclairage
	 */
	public void setLightPlace(float x, float y, float z){
		lightx=x;
		lighty=y;
		lightz=z;
	}

	/**
	 * Definie la direction de l'eclairage
	 */
	public void setLightDirection(float x, float y, float z){
		lightDx=x;
		lightDy=y;
		lightDz=z;
	}
	
	/**
	 * Passage en vue 3DmapD
	 */
	public void mode3D() {
		mode3D=true;
		modeChanged=true;
		
		}
	
	/**
	 * Passage en vue 2D
	 */
	public void mode2D(){
		mode3D=false;
		modeChanged=true;
		
	}
	
	/**
	 * Passage du mode 2D au mode 3D et inversement
	 */
	private void changeViewMode(){
		if (mode3D){
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			GLU.gluPerspective(45.0f, -display_width / display_height, 1.0f,10000.0f);
		}else{
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, display_width, display_height, 0, -1000, 1000);
		}
		
		modeChanged=false;
	}
		
	private void setCameraDiection(){
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		//positionnement de la camera
		GLU.gluLookAt(camx, camy,camz, camDx,camDy, camDz, 0, 0	, 1);
	}
	
	public void setCamPlace(float x, float y, float z){
		camx=x;
		camy=y;
		camz=z;
	}

	/**
	 * Definie la direction de l'eclairage
	 */
	public void setCamDirection(float x, float y, float z){
		camDx=x;
		camDy=y;
		camDz=z;
	}
	

	public void setMap(Map map) {
		this.map = map;
	}

	public int getDisplay_width() {
		return mapDisplay_width;
	}

	public int getDisplay_height() {
		return mapDisplay_height;
	}

	public boolean initialized() {
		
		return initialized;
	}
	
	public void setSound(Sound sound){
		this.sound=sound;
		audioRender.setSound(sound);
	}
	
}

	

