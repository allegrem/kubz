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

import gameEngine.GameEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import objLoader.ObjDisplay;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.GLU;

import synthesis.Sound;
import utilities.Maths;
import utilities.MyBuffer;
import utilities.Point;
import utilities.Vector;
import views.informationViews.AudioRender;
import views.interfaces.DisplayableFather;
import map.Map;

/**
 * Instructions OpenGL:
 * Sert a charger la librairie
 * et a gerer l'affichage
 * @author paul
 * 
 */
public class GLDisplay extends Thread{
	
	
	/*Reglages de l'affichage*/

	private float decalageX=210; //Decalage de l'affichage a l'horizontale
	private float decalageY=0; //Decalage de l'affichage a la verticale
	private float multX=1.0f; //Facteur multiplicatif pour etendre l'affichage selon x
	private float multY=0.85f; //Facteur multiplicatif pour etendre l'affichage selon x
	private float sens=1; //sens=-1 -> image renversee
	private float parallelisme=0.11f; // Correction du paralellisme
	private float invParal=1; //Si -1, parallelisme inverse dans l'autre sens
	

	
	

	private GameEngine gameEngine;
	private int display_width=700;
	private int display_height=500; 
	private int mapDisplay_width=0;
	private int mapDisplay_height=0;
	private boolean do_run=true;
	private Map map;
	private Sound sound;
	private int frequency=50;
	private  boolean initialized=false;
	private Text texte;
	private final int nFrames=20;// Nbre de frames pour le motion-blur
	private int i=0;
	
	/*
	 * Parametres de la projection
	 */
	private float camx=0.0f;
	private float camy=0.0f;
	private float camz=500.0f;
	private float camDx=50;
	private float camDy=50;
	private float camDz=0.0f;
	private static boolean mode3D=false;
	private boolean modeChanged=false;
	private long time=0;
	private long started=0;
	
	
	/*
	 * Parametres de l'eclairage
	 */
	private float lightx=-20.0f;
	private float lighty=-20.0f;
	private float lightz=20.0f;
	private float lightDx;
	private float lightDy;
	private float lightDz=0.0f;
	private float lightsx=-20.0f;
	private float lightsy=-20.0f;
	private float lightsz=20.0f;
	private float lightsDx;
	private float lightsDy;
	private float lightsDz=0.0f;
	private Lighting lighting=new Lighting(this);

	
	/*
	 * 
	 * gestion du texte
	 * 
	 */
	
	private boolean printMessage=false;
	private String message =" ";
	private int mx=0;
	private int my=0;
	private ReadableColor mColor=null;
	private int shaderProgram;
	private int vertexShader;		
	private int fragmentShader;

	
	/**
	 * Lancement de l'affichage
	 * 
	 * 
	 */
	public GLDisplay(GameEngine gameEngine){
		this.gameEngine=gameEngine;
		KeyboardManager.setGameEngine(gameEngine);
	}

	private void loadShaders(){
		   shaderProgram = GL20.glCreateProgram();
	       vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
	       fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	        StringBuilder vertexShaderSource = new StringBuilder();
	        StringBuilder fragmentShaderSource = new StringBuilder();
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader("shaders/shader.vert"));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                vertexShaderSource.append(line).append('\n');
	            }
	        } catch (IOException e) {
	            System.err.println("Vertex shader wasn't loaded properly.");
	            e.printStackTrace();
	            System.exit(1);
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        BufferedReader reader2 = null;
	        try {
	            reader2 = new BufferedReader(new FileReader("shaders/shader.frag"));
	            String line;
	            while ((line = reader2.readLine()) != null) {
	                fragmentShaderSource.append(line).append('\n');
	            }
	        } catch (IOException e) {
	            System.err.println("Fragment shader wasn't loaded properly.");
	            System.exit(1);
	        } finally {
	            if (reader2 != null) {
	                try {
	                    reader2.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        GL20.glShaderSource(vertexShader, vertexShaderSource);
	        GL20.glCompileShader(vertexShader);
	        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
	            System.err.println("Vertex shader wasn't able to be compiled correctly.");
	        }
	        GL20.glShaderSource(fragmentShader, fragmentShaderSource);
	        GL20.glCompileShader(fragmentShader);
	        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
	            System.err.println("Fragment shader wasn't able to be compiled correctly.");
	        }
	        GL20.glAttachShader(shaderProgram, vertexShader);
	        GL20.glAttachShader(shaderProgram, fragmentShader);
	        GL20.glLinkProgram(shaderProgram);
	        GL20.glValidateProgram(shaderProgram);
	        GL20.glUseProgram(shaderProgram);
	    	int lH=GL20.glGetUniformLocation(shaderProgram, "display_height");
	    	int lW=GL20.glGetUniformLocation(shaderProgram, "display_width");
			int lA=GL20.glGetUniformLocation(shaderProgram, "alpha");
			int lM=GL20.glGetUniformLocation(shaderProgram, "midle");
			int lS=GL20.glGetUniformLocation(shaderProgram, "sens");
			int lI=GL20.glGetUniformLocation(shaderProgram, "inv");
			int lX=GL20.glGetUniformLocation(shaderProgram, "decalageX");
			int lY=GL20.glGetUniformLocation(shaderProgram, "decalageY");
			int lMuX=GL20.glGetUniformLocation(shaderProgram, "multX");
			int lMuY=GL20.glGetUniformLocation(shaderProgram, "multY");
			GL20.glUniform1i(lH,display_height);
			GL20.glUniform1i(lW,display_width);
			GL20.glUniform1f(lA,parallelisme);
			GL20.glUniform1f(lM,(float)(mapDisplay_width/2.0));
			GL20.glUniform1f(lS,sens);
			GL20.glUniform1f(lI,invParal);
			GL20.glUniform1f(lX,decalageX);
			GL20.glUniform1f(lY,decalageY);
			GL20.glUniform1f(lMuX,multX);
			GL20.glUniform1f(lMuY,multY);
	}
	
	/**
	 *Methode tournant en continu pendant l'execution
	 *du thread 
	 */
	@Override
	public void run(){
		initialize();
		mapDisplay_height=(int)(display_height);
		mapDisplay_width=display_height;
		lightDx=(float)(display_width/2.0);
		lightDy=(float)(display_height/2.0);
		camDx=(float)(display_width/2.0);
		camDy=(float)(display_height/2.0);
		setCameraDirection();
		texte=new Text();
		initialized=true;
		loadShaders();
		while(do_run){
			
		if (Display.isCloseRequested()||KeyboardManager.quit)
				do_run = false; // On arrete le programme
		clear(); //On nettoie la fenetre
		KeyboardManager.checkKeyboard();
		glMatrixMode(GL_MODELVIEW);
		GL11.glTranslatef((float)(1000),0f,0f);
		setLightPosition();
		if(modeChanged)
			changeViewMode();
		if(mode3D){
			setCameraDirection();
			if(time>0 && System.currentTimeMillis()-started>=time){
				mode2D();
				resetLight();
			}
		}
		
		mainRender(); //On actualise la fenetre avec le nouveau rendu
		glPrint();
		update(); //On actualise la fenetre avec le nouveau rendu
		Display.sync(frequency); //On synchronise l'affichage sur le bon FPS
	
		
		
		}
		GL20.glUseProgram(0);
		ObjDisplay.cleanUp();
		close();//Fermeture de la fenêtre
		
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
	    System.out.println("OpenGL version: "+GL11.glGetString(GL11.GL_VERSION));
		System.out.println("GLSL version: "+GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
		
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
		changeViewMode();

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
		glMatrixMode(GL_MODELVIEW);
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
	 * Replalce la lumière à sa position sauvegardée
	 */
	private void resetLight() {
		lightx=lightsx;
		lighty=lightsy;
		lightz=lightsz;
		lightDx=lightsDx;
		lightDy=lightsDy;
		lightDz=lightsDz;
		
	}

	
	/**
	 * Passage en vue 3DmapD
	 */
	public void mode3D(long time) {
		mode3D=true;
		modeChanged=true;
		this.time=time;
		this.started=System.currentTimeMillis();
		
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
			glMatrixMode(GL_MODELVIEW);
		}else{
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, display_width, display_height, 0, -1000, 1000);
			glMatrixMode(GL_MODELVIEW);
		}
		
		modeChanged=false;
	}
		/**
		 * Sert a placer la camera aux coordonnees definies
		 */
	private void setCameraDirection(){
		glMatrixMode(GL_MODELVIEW);
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

	public int getmapDisplay_width() {
		return mapDisplay_width;
	}

	public int getmapDisplay_height() {
		return mapDisplay_height;
	}
	
	public int getDisplay_width() {
		return display_width;
	}

	public int getDisplay_height() {
		return display_height;
	}

	/**
	 * 
	 * @return Si l'initialisation de l'OpenGL est terminee
	 */
	public boolean initialized() {
		
		return initialized;
	}
	
	/**
	 * Pace en mode 3D "automatique": La camera est placee derriere l'attaquant et regarde la cible.
	 * Idem pour l'eclairage
	 * @param attacking
	 * @param attacked
	 * @param time
	 */
	public void auto3D(DisplayableFather attacking,DisplayableFather attacked,int time){
		lightsx=lightx;
		lightsy=lighty;
		lightsz=lightz;
		lightsDx=lightDx;
		lightsDy=lightDy;
		lightsDz=lightDz;
		setCamDirection((float)attacked.getX(), (float)attacked.getY(), 0);
		setLightDirection((float)attacked.getX(), (float)attacked.getY(), 0);
		Vector vect =Maths.makeVector(attacked.getX(),attacked.getY(),0,attacking.getX(),attacking.getY(),0);
		double norme=vect.norme();
		Maths.normalize(vect);
		setCamPlace((float) (attacking.getX()+(norme/3+50)*vect.getX()), (float) (attacking.getY()+(norme/3+50)*vect.getY()), 400);
		setLightPlace((float) (attacking.getX()+(norme/3+50)*vect.getX()), (float) (attacking.getY()+(norme/3+50)*vect.getY()), 400);
		mode3D=true;
		modeChanged=true;
		this.time=time;
		this.started=System.currentTimeMillis();
	}
	
	public void auto3D(DisplayableFather attacking, double direction, int power, int time) {
		lightsx=lightx;
		lightsy=lighty;
		lightsz=lightz;
		lightsDx=lightDx;
		lightsDy=lightDy;
		lightsDz=lightDz;
		Point attacked=new Point(attacking.getX()-2*power*Math.sin(Math.PI/180*direction),attacking.getY()+2*power*Math.cos(Math.PI/180*direction));
		setCamDirection((float)attacked.getX(), (float)attacked.getY(), 0);
		setLightDirection((float)attacked.getX(), (float)attacked.getY(), 0);
		Vector vect =Maths.makeVector(attacked.getX(),attacked.getY(),0,attacking.getX(),attacking.getY(),0);
		double norme=vect.norme();
		Maths.normalize(vect);
		setCamPlace((float) (attacking.getX()+(norme/3+200)*vect.getX()), (float) (attacking.getY()+(norme/3+200)*vect.getY()), 400);
		setLightPlace((float) (attacking.getX()+(norme/3+200)*vect.getX()), (float) (attacking.getY()+(norme/3+200)*vect.getY()), 400);
		mode3D=true;
		modeChanged=true;
		this.time=time;
		this.started=System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return Si on est en mode 3D
	 */
	public static boolean getMode3D(){
		return mode3D;
	}
	
	public Text getText(){
		return texte;
	}

	public void print(int x, int y, ReadableColor color, String message) {
		printMessage=true;
		mx=x;
		my=y;
		mColor=color;
		this.message=message;
		
	}
	
	public void eraseMessage(){
		printMessage=false;
		
	}
	
	private void glPrint(){
		if(printMessage)
		texte.glPrint(mx, my, mColor, message);
	}
	
	
}

	

