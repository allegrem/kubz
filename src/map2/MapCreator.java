package map2;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.glu.GLU;

import utilities.MyFloatBuffer;
import utilities.Point;
import utilities.RandomPerso;
import views.BackgroundView;
import views.BaseView;
import views.CircleMonsterView;
import views.Displayable;
import views.DisplayableFather;
import views.MonsterView;
import views.ShapeMonsterView;
import views.SquareMonsterView;
import views.WallView;

import OpenGL.GLDisplay;

/**
 * Sert a creer une nouvelle map
 * 
 * @author paul&valeh
 * 
 */
public class MapCreator {
	/*
	 * Les dimensions de la fenêtre
	 */
	public static final int display_width = 950;
	public static final int display_height = 700;
	public static final int create_mode = 0;
	public static final int read_mode = 1;
	
	/*
	 * Les fichiers dans lesquels seront 
	 * effectuees les sauvegardes
	 */
	public static String bFileName = "Maps/bFile.txt";
	public static String mFileName = "Maps/mFile.txt";
	public static String wFileName = "Maps/WFile.txt";
	
	/*
	 * Creation du saver de map
	 */
	private MapSaver mapSaver = new MapSaver(bFileName,mFileName,wFileName); 
	
	/*
	 * Position et direction de la camera
	 */
	private float eyeX =0,eyeY = 0,eyeZ=50; 
	private float atX=(float)(display_width/2),atY=(float)(display_height/2),atZ=0;

	/*
	 * La position de la souris dans la fenêtre
	 */
	private int mouseX;
	private int mouseY;

/*
 * Angle de l'eclairage par rapport
 * a l'axe z
 */
	private int angle = 0;
	
	/*
	 * Les actions correspondant à la souris et au clavier
	 */
	private boolean rightClicked = true;
	private boolean leftClicked = true;
	private boolean scrollPressed = true;
	private boolean tabClicked = true;
	private boolean aClicked = true;
	private boolean rClicked = true;
	private boolean lClicked = true;
	private boolean rightKey = true;
	private boolean leftKey = true;
	private boolean upKey = true;
	private boolean downKey = true;

	/*
	 * map et module d'affichage créés
	 */
	private Map map  = new Map(display_width, display_height);
	private GLDisplay affichage;

	
	/*
	 * Choix du mode d'affichage: 3D ou non
	 */
	public static boolean MODE3D = false;

	/*
	 * Mode avec déplacement aléatoire des murs
	 */
	private boolean wAlea = false;

	/*
	 * Mode avec rotation de la carte de jeu
	 */
	private boolean rotate = false;

	/*
	 * Active la lumière spot
	 */
	private boolean light = false;

	/**
	 * Initialisation du createur de map
	 * @param mode Lecture ou creation de map
	 */
	public MapCreator(int mode) {

		/*
		 * Creation du module d'affichage et de la map
		 */
		if (mode == 0){
		/*	
		*On initialise avec un map par defaut
		*/	
		affichage = new GLDisplay(display_width, display_height,map,this); 			
		RandomPerso.initialize();//Initilaisation du générateur de nombres aléatoires
		BackgroundView background = new BackgroundView(display_width, display_height); 
		map.add(background);
		affichage.start();
	
		while (affichage.isAlive()) {
			/*
			 * On modifie le background toutes les 100 ms
			 */
			background.change();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
			}
		}
		mapSaver.saveToFile(map); //save Map
	}
		else {
			affichage = new GLDisplay(display_width, display_height,map,this); 			
			RandomPerso.initialize();//Initilaisation du générateur de nombres aléatoires
			map.add(new BackgroundView(display_width, display_height));
			MapReader mapReader = new MapReader(bFileName,mFileName,wFileName);
			try {
				/*
				 * Lecture de la map precedemment enregistree
				 */
				map = mapReader.read(map);
			}catch(Exception e){e.printStackTrace();}
			
			affichage.start();
			
			while (affichage.isAlive()) {
			try {
				/*
				 * On fait une pause pour ne pas
				 * monopoliser le processeur avec une
				 * boucle vide
				 */
				Thread.sleep(1000);
				} catch (InterruptedException e) {}
			
			}
			mapSaver.saveToFile(map); //save Map

	}
		
}
	
	/**
	 * Recuperation et traitement des actions
	 * de l'utilisateur
	 */
	public  void compute(){
		
		/*
		 * On regarde si l'utilisateur a fait une action
		 */
		checkInput();

		/*
		 * Si mode aléatoire activé, on fait bouger les murs
		 */
		if (wAlea)
			wAlea();

		/*
		 * Si mode avec rotation de la carte activé, on la fait bouger
		 */
		if (rotate)
			rotate();

		eclairage();
	}


	/*
	 * VCérification des actions de l'utilisateur
	 */
	public  void checkInput() {

		/*
		 * Enregistrement des coordonnées de la souris
		 */
		mouseX = Mouse.getX();
		mouseY = display_height - Mouse.getY();

		/*
		 * Traitement des clicks de la souris
		 */
		if (!Mouse.isButtonDown(0))
			leftClicked = true;

		if (!Mouse.isButtonDown(1))
			rightClicked = true;

		if (!Mouse.isButtonDown(2))
			scrollPressed = true;

		if (!Keyboard.isKeyDown(Keyboard.KEY_TAB))
			tabClicked = true;

		if (!Keyboard.isKeyDown(Keyboard.KEY_R))
			rClicked = true;

		if (!Keyboard.isKeyDown(Keyboard.KEY_A))
			aClicked = true;

		if (!Keyboard.isKeyDown(Keyboard.KEY_L))
			lClicked = true;
		
		if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			rightKey = true;
		if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			leftKey = true;
		if (!Keyboard.isKeyDown(Keyboard.KEY_UP))
			upKey = true;
		if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			downKey =  true;

		/*
		 * Changement de MODE3D
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && tabClicked) {
			MODE3D = !MODE3D;
			changementMode3D();
			tabClicked = false;
		}
		
		/*
		 * Deplacement de la camera en
		 * mode 3D
		 */
		if (MODE3D && Keyboard.isKeyDown(Keyboard.KEY_LEFT) && leftKey) {
			eyeX -= 5;
			changementMode3D();
		}
		if (MODE3D && Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && rightKey) {
			eyeX += 5;
			changementMode3D();
		}
		if (MODE3D && Keyboard.isKeyDown(Keyboard.KEY_DOWN) && downKey) {
			eyeY -= 5;
			changementMode3D();
		}
		if (MODE3D && Keyboard.isKeyDown(Keyboard.KEY_UP) && upKey) {
			eyeY += 5;
			changementMode3D();
		}

		
		/*
		 * Rotation de la map
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_R) && rClicked) {
			rotate = !rotate;
			rClicked = false;
		}

		/*
		 * Mouvement aléatoire des murs
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_A) && aClicked) {
			wAlea = !wAlea;
			aClicked = false;
		}

		/*
		 * Eclairage
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_L) && lClicked) {
			light = !light;
			lClicked = false;
		}

		/*
		 * Si on effectue un click droit à la souris, on supprime le ou les objets sur lesquels on clique
		 */
		if (Mouse.isButtonDown(1) && rightClicked) {
			Point mousePoint1 = new Point(mouseX, mouseY);
			ArrayList<DisplayableFather> removables=new ArrayList<DisplayableFather>();
			for (DisplayableFather object : map.getObjects()) {
				if (object.isInZone(mousePoint1)) {
					removables.add(object);
				}
			}
			for(DisplayableFather object: removables){
				map.remove(object);
			}
			rightClicked = false;
		
		}

		/*
		 * Si on clique avec la molette du milieu sur une base ou une unité déjà
		 * créée, permet de changer sa couleur
		 */
		annuler : if (Mouse.isButtonDown(2) && scrollPressed) {
			Point mousePoint = new Point(mouseX, mouseY);
			for (Displayable object : map.getObjects()) {
				if (object.isInZone(mousePoint)) {
					java.awt.Color color = JColorChooser.showDialog(null,
							"Object color choose", null);
					if (color==null)
						break annuler;
					int r = color.getRed(), g = color.getGreen(), b = color
							.getBlue();
					object.setColor(new Color(r, g, b));
				}
				
			}
			scrollPressed = false;
		}

		/*
		 * Si on clique sur le bouton gauche de la souris, on ajoute un objet de
		 * type sélectionné au clavier: S square, C circle, T triangle, B base,
		 * W wall
		 */
		if (Mouse.isButtonDown(0) && leftClicked) {
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				map.add(new SquareMonsterView(new Point(mouseX, mouseY), Color.BLUE));
				
			}

			else if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
				map.add(new ShapeMonsterView(new Point(mouseX, mouseY), Color.GREEN));
		
			} else if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				map.add(new CircleMonsterView(new Point(mouseX, mouseY),
						Color.YELLOW));
				
			} else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
				DisplayableFather base;
				base=map.add(new BaseView(new Point(mouseX, mouseY), Color.LTGREY,BaseView.HAUT));

				while (Mouse.isButtonDown(0)) {
					mouseX = Mouse.getX();
					mouseY = display_height - Mouse.getY();
					map.remove(base);
					if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
						base=map.add(new BaseView(new Point(mouseX, 0), Color.LTGREY,BaseView.HAUT));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
						base=map.add(new BaseView(new Point(mouseX, display_height),
								Color.LTGREY,BaseView.BAS));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
						base=map.add(new BaseView(new Point(display_width, mouseY),
								Color.LTGREY,BaseView.DROITE));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
						base=map.add(new BaseView(new Point(0, mouseY), Color.LTGREY,BaseView.GAUCHE));
					} else {
						base=map.add(new BaseView(new Point(mouseX, mouseY),
								Color.LTGREY,BaseView.HAUT));
					}
					affichage.clear();
					render();
					affichage.update();
					Display.sync(120);
					Mouse.poll();
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				DisplayableFather keyboard;
				int imouseX = mouseX;
				int iy = mouseY;
				keyboard=map.add(new WallView(new Point(imouseX, iy), new Point(
						mouseX + 1, mouseY + 1), 15, WallView.NORMAL));
				while (Mouse.isButtonDown(0)) {
					mouseX = Mouse.getX();
					mouseY = display_height - Mouse.getY();
					map.remove(keyboard);
					if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
						keyboard=map.add(new WallView(new Point(imouseX, iy),
								new Point(mouseX, mouseY), 15, WallView.HORIZONTAL));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
						keyboard=map.add( new WallView(new Point(imouseX, iy),
								new Point(mouseX, mouseY), 15, WallView.VERTICAL));
					} else {
						keyboard=map.add(new WallView(new Point(imouseX, iy),
								new Point(mouseX, mouseY), 15, WallView.NORMAL));
					}
					affichage.clear();
					render();
					affichage.update();
					Display.sync(120);
					Mouse.poll();
				}
			}
			leftClicked = false;
		}

	}

	/**
	 * Effectue le rendu de la map
	 */
	public  void render() {
		map.paint();

	}

	/**
	 * Passage du mode 2D vers le mode 3D
	 * (cf MODE3D)
	 */
	public  void changementMode3D() {
		/*
		 * Matrice de projection (3D vers 2D): utilisation d'une projection
		 * perspective
		 */
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		if (MODE3D)
			GLU.gluPerspective(45.0f, display_width / display_height, 1.0f,
					10000.0f);
		else
			glOrtho(0, display_width, display_height, 0, -1000, 1000);
		
		
		/*
		 * Si on est en mode 3D, on initialise la 3D
		 */

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		if (MODE3D) {
			//positionnement de la camera
			GLU.gluLookAt(eyeX, eyeY,
					(float) eyeZ, atX,
					atY, atZ, 0, 0	, 1);
		}
		
	}
	
/**
 * Rotation de la carte sur elle-meme
 */
	public  void rotate() {
		GL11.glTranslatef(display_width / 2, display_height / 2, 0);
		GL11.glRotated(0.1, 0, 0, 1);
		GL11.glTranslatef(-display_width / 2, -display_height / 2, 0);
	}

	/**
	 * Fait bouger les murs créés aléatoirement
	 */
	public  void wAlea() {
		for (Displayable object : map.getObjects()) {
			if(object instanceof WallView )
			((WallView) object).aleaMove();
		}
	}

	/**
	 * Gere l'eclairage
	 */
	public  void eclairage() {
		if (light) {

			/*
			 * Réglages de l'éclairage
			 */
			//reminder : diffuse,specular,ambient,emissive
			glEnable(GL11.GL_LIGHTING);
			glEnable(GL11.GL_LIGHT0);  //one source of light only
			GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);  //which face will reflect light+ambient(lOff) and diffuse(lOn) preferred to have same value
			GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT,MyFloatBuffer.newFloatBuffer4(0.8f,0.8f,0.8f,1.0f)); //Type de l'éclairage
			glMatrixMode(GL_MODELVIEW);
			GL11.glTranslated(display_width / 2, display_height / 2, 0);
			GL11.glRotated(Math.round(angle/10), 0, 0, 1);
			GL11.glTranslated(-display_width / 2, -display_height / 2, 0);
			GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION,MyFloatBuffer.newFloatBuffer4(-20.0f,-20.0f,20.0f,1.0f));//Position et type de l'éclairage
			GL11.glLight(GL11.GL_LIGHT0,GL11.GL_SPOT_DIRECTION,MyFloatBuffer.newFloatBuffer4(display_width/2,display_height/2,0,0));//Direction de l'éclairage
			GL11.glTranslated(display_width / 2, display_height / 2, 0);
			GL11.glRotated(-Math.round(angle/10), 0, 0, 1);
			GL11.glTranslated(-display_width / 2, -display_height / 2, 0);

			angle++; //fait tourner l'eclairage
		} else {
			 GL11.glDisable(GL11.GL_LIGHTING);
			 GL11.glDisable(GL11.GL_LIGHT0);
		}
	}

}
