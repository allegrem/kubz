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

import utilities.BackgroundView;
import utilities.Point;
import utilities.RandomPerso;
import views.BaseView;
import views.CircleMonsterView;
import views.Displayable;
import views.MonsterView;
import views.ShapeMonsterView;
import views.SquareMonsterView;
import views.WallView;

import OpenGL.GLDisplay;

/**
 * Sert à créer une nouvelle map
 * 
 * @author paul&valeh
 * 
 */
public class MapCreator {
	/*
	 * Les dimensions de la fenêtre
	 */
	public static final int display_width = 640;
	public static final int display_height = 480;
	public static final int create_mode = 0;
	public static final int read_mode = 1;
	
	
	public static String bFileName = "bFile.txt";
	public static String mFileName = "mFile.txt";
	public static String wFileName = "WFile.txt";
	
	private MapSaver mapSaver = new MapSaver(bFileName,mFileName,wFileName); 
	
	private float eyeX =0,eyeY = 0,eyeZ=50; 
	private float atX=(float)(display_width/2),atY=(float)(display_height/2),atZ=0;

	/*
	 * La position de la souris dans la fenêtre
	 */
	private int mouseX;
	private int mouseY;
	/*
	 * La position de l'éclairage
	 */
	private int LightPos[] = { 0,0, 20, 1 };
	private float Light1Dir[] = { display_width / 2, display_height / 2, 0.0f,
			0.0f };
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

	public MapCreator(int mode) {

		/*
		 * Création du module d'affichage et de la map
		 */
		if (mode == 0){
		
		affichage = new GLDisplay(display_width, display_height,map,this); //on initilaise avec un map par defaut et
																		   //pour le MapReader on remplace le par d�faut				
		RandomPerso.initialize();
		map.add(new BackgroundView(display_width, display_height));
		affichage.start();

		/*
		 * Initilaisation du générateur de nombres aléatoires
		 */
	
		while (affichage.isAlive()) {
			System.out.println("coucou");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
		}
		mapSaver.saveToFile(map); //save Map
	}
		else {
			affichage = new GLDisplay(display_width, display_height,map,this); 			
			RandomPerso.initialize();
			map.add(new BackgroundView(display_width, display_height));
			MapReader mapReader = new MapReader(bFileName,mFileName,wFileName);
			try {
				map = mapReader.read(map);
			}catch(Exception e){e.printStackTrace();}
			affichage.start();

			/*
			* Initilaisation du générateur de nombres aléatoires
			*/

			while (affichage.isAlive()) {
			System.out.println("coucou");

			try {
				Thread.sleep(1000);
				} catch (InterruptedException e) {}
			
			}
			mapSaver.saveToFile(map); //save Map

	}
		
}
	
	
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
		 * Si on effectue un click droit à la souris, on supprime le dernier
		 * objet créé du type sélectionné par le clavier: U pour Unit, W pour
		 * Wall ou B pour Base
		 */
		if (Mouse.isButtonDown(1) && rightClicked) {
			map.removeLast();
			rightClicked = false;
		
		}

		/*
		 * Si on clique avec la molette du milieu sur une base ou une unité déjà
		 * créée, permet de changer sa couleur
		 */
		if (Mouse.isButtonDown(2) && scrollPressed) {
			Point mousePoint = new Point(mouseX, mouseY);
			for (Displayable object : map.getObjects()) {
				if (object.isInZone(mousePoint)) {
					java.awt.Color color = JColorChooser.showDialog(null,
							"Object color choose", null);
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
				map.add(new SquareMonsterView(new Point(mouseX, mouseY), Color.BLUE,
						map));
				
			}

			else if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
				map.add(new ShapeMonsterView(new Point(mouseX, mouseY), Color.GREEN,
						map));
		
			} else if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				map.add(new CircleMonsterView(new Point(mouseX, mouseY),
						Color.YELLOW, map));
				
			} else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
				Displayable base;
				base=map.add(new BaseView(new Point(mouseX, mouseY), Color.PURPLE,BaseView.HAUT));
				while (Mouse.isButtonDown(0)) {
					mouseX = Mouse.getX();
					mouseY = display_height - Mouse.getY();
					map.remove(base);
					if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
						base=map.add(new BaseView(new Point(mouseX, 0), Color.PURPLE,BaseView.HAUT));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
						base=map.add(new BaseView(new Point(mouseX, display_height),
								Color.PURPLE,BaseView.BAS));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
						base=map.add(new BaseView(new Point(display_width, mouseY),
								Color.PURPLE,BaseView.DROITE));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
						base=map.add(new BaseView(new Point(0, mouseY), Color.PURPLE,BaseView.GAUCHE));
					} else {
						base=map.add(new BaseView(new Point(mouseX, mouseY),
								Color.PURPLE,BaseView.HAUT));
					}
					affichage.clear();
					render();
					affichage.update();
					Display.sync(120);
					Mouse.poll();
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				Displayable keyboard;
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

	//ici
	 
	
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
			glOrtho(0, display_width, display_height, 0, -100, 0);
		
		
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

	public  void rotate() {
		/*
		 * Sert à faire tourner la carte sur elle-même 
		 */
		GL11.glTranslatef(display_width / 2, display_height / 2, 0);
		GL11.glRotated(0.1, 0, 0, 1);
		GL11.glTranslatef(-display_width / 2, -display_height / 2, 0);
	}

	public  void wAlea() {
		/*
		 * Sert à faire bouger les murs créés aléatoirement
		 */
		for (Displayable object : map.getObjects()) {
			if(object instanceof WallView )
			((WallView) object).aleaMove();
		}
	}

	private  void eclairage() {
		if (light) {

			/*
			 * Réglages de l'éclairage
			 */
			glEnable(GL11.GL_LIGHTING);
			glEnable(GL11.GL_LIGHT0);
			glMatrixMode(GL_MODELVIEW);
			GL11.glTranslated(display_width / 2, display_height / 2, 0);
			GL11.glRotated(angle, 0, 0, 1);
			GL11.glTranslated(-display_width / 2, -display_height / 2, 0);
			ByteBuffer temp1 = ByteBuffer.allocateDirect(16);
			temp1.order(ByteOrder.nativeOrder());
			GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, (IntBuffer) temp1
					.asIntBuffer().put(LightPos).flip());
			GL11.glLighti(GL11.GL_LIGHT0, GL11.GL_SPOT_CUTOFF, 120);
			ByteBuffer temp2 = ByteBuffer.allocateDirect(16);
			temp2.order(ByteOrder.nativeOrder());
			GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPOT_DIRECTION,
					(FloatBuffer) temp2.asFloatBuffer().put(Light1Dir).flip());
			GL11.glTranslated(display_width / 2, display_height / 2, 0);
			GL11.glRotated(-angle, 0, 0, 1);
			GL11.glTranslated(-display_width / 2, -display_height / 2, 0);

			//angle++;
		} else {
			 GL11.glDisable(GL11.GL_LIGHTING);
			 GL11.glDisable(GL11.GL_LIGHT0);
		}
	}

}
