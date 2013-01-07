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

import OpenGL.GLDisplay;

/**
 * Sert à créer une nouvelle map
 * 
 * @author paul
 * 
 */
public class MapCreator extends Observable {
	/*
	 * Les dimensions de la fenêtre
	 */
	public static final int display_width = 640;
	public static final int display_height = 480;

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
	 * Les ArrayLists d'objets à afficher dans la map
	 */
	private ArrayList<Unit> units = new ArrayList<Unit>();
	private ArrayList<Base> bases = new ArrayList<Base>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();

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

	/*
	 * map et module d'affichage créés
	 */
	private Map map;
	private GLDisplay affichage;

	/*
	 * Booleen servant à quiter le programme lors de la fermeture de la fenêtre
	 */
	private boolean do_run = true;

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

	public MapCreator() {

		/*
		 * Création du module d'affichage et de la map
		 */
		affichage = new GLDisplay(display_width, display_height);
		map = new Map(walls, units, bases, display_width, display_height);
		addObserver(map);
		changementMode3D();

		/*
		 * Initilaisation du générateur de nombres aléatoires
		 */
		RandomPerso.initialize();

		while (do_run) {

			if (Display.isCloseRequested())
				do_run = false;

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

			/*
			 * On nettoie l'ancien affichage puis on rend et affiche le nouveau
			 */
			affichage.clear();

			/*
			 * On lance l'éclairage
			 */
			eclairage();

			render();
			affichage.update();

			/*
			 * Pause pour actualiser la fenêtre à 120 FPS
			 */
			Display.sync(120);

		}

		/*
		 * Fermeture de la fenêtre
		 */
		affichage.close();

		/*
		 * Enregistrement de la map dans un fichier
		 */
		saveToFile();

	}


	/*
	 * VCérification des actions de l'utilisateur
	 */
	public void checkInput() {

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

		/*
		 * Changement de MODE3D
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && tabClicked) {
			MODE3D = !MODE3D;
			changementMode3D();
			tabClicked = false;
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

			if (Keyboard.isKeyDown(Keyboard.KEY_U) && units.size() > 0) {
				units.remove(units.size() - 1);
				setChangedU();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_W) && walls.size() > 0) {
				walls.remove(walls.size() - 1);
				setChangedW();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_B) && bases.size() > 0) {
				bases.remove(bases.size() - 1);
				setChangedB();
			}

			rightClicked = false;
			notifyObserversU(units);
			notifyObserversB(bases);
			notifyObserversW(walls);

		}

		/*
		 * Si on clique avec la molette du milieu sur une base ou une unité déjà
		 * créée, permet de changer sa couleur
		 */
		if (Mouse.isButtonDown(2) && scrollPressed) {

			int mouseX = Mouse.getX();
			int mouseY = Mouse.getY();
			/*
			 * Déjà fait plus haut mais avecmouseY=display_height-Mouse.getY();
			 * C'est pas ca qu'il faut ?
			 */
			Point mousePoint = new Point(mouseX, mouseY);
			for (Base base : bases) {
				if (base.isInZone(mousePoint)) {
					java.awt.Color color = JColorChooser.showDialog(null,
							"Base color choose", null);
					int r = color.getRed(), g = color.getGreen(), b = color
							.getBlue();
					base.setColor(new Color(r, g, b));
				}
			}

			for (Unit unit : units) {
				if (unit.isInZone(mousePoint)) {
					java.awt.Color color = JColorChooser.showDialog(null,
							"Unit color choose", null);
					int r = color.getRed(), g = color.getGreen(), b = color
							.getBlue();
					unit.setColor(new Color(r, g, b));
				}
			}
		}

		/*
		 * Si on clique sur le bouton gauche de la souris, on ajoute un objet de
		 * type sélectionné au clavier: S square, C circle, T triangle, B base,
		 * W wall
		 */
		if (Mouse.isButtonDown(0) && leftClicked) {
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				units.add(new SquareUnit(new Point(mouseX, mouseY), Color.BLUE,
						map));
				setChangedU();
			}

			else if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
				units.add(new ShapeUnit(new Point(mouseX, mouseY), Color.GREEN,
						map));
				setChangedU();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				units.add(new CircleUnit(new Point(mouseX, mouseY),
						Color.YELLOW, map));
				setChangedU();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
				int size = bases.size();
				bases.add(new Base(new Point(mouseX, mouseY), Color.PURPLE,Base.HAUT));
				while (Mouse.isButtonDown(0)) {
					mouseX = Mouse.getX();
					mouseY = display_height - Mouse.getY();
					bases.remove(size);
					if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
						bases.add(new Base(new Point(mouseX, 0), Color.PURPLE,Base.HAUT));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
						bases.add(new Base(new Point(mouseX, display_height),
								Color.PURPLE,Base.BAS));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
						bases.add(new Base(new Point(display_width, mouseY),
								Color.PURPLE,Base.DROITE));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
						bases.add(new Base(new Point(0, mouseY), Color.PURPLE,Base.GAUCHE));
					} else {
						bases.add(new Base(new Point(mouseX, mouseY),
								Color.PURPLE,Base.HAUT));
					}
					setChangedB();
					notifyObserversB(bases);
					affichage.clear();
					render();
					affichage.update();
					Display.sync(120);
					Mouse.poll();
				}
				setChangedB();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				int size = walls.size();
				int imouseX = mouseX;
				int iy = mouseY;
				walls.add(new Wall(new Point(imouseX, iy), new Point(
						mouseX + 1, mouseY + 1), 15, Wall.NORMAL));
				while (Mouse.isButtonDown(0)) {
					mouseX = Mouse.getX();
					mouseY = display_height - Mouse.getY();
					walls.remove(size);
					if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
						walls.add(size, new Wall(new Point(imouseX, iy),
								new Point(mouseX, mouseY), 15, Wall.HORIZONTAL));
					} else if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
						walls.add(size, new Wall(new Point(imouseX, iy),
								new Point(mouseX, mouseY), 15, Wall.VERTICAL));
					} else {
						walls.add(size, new Wall(new Point(imouseX, iy),
								new Point(mouseX, mouseY), 15, Wall.NORMAL));
					}
					setChangedW();
					notifyObserversW(walls);
					affichage.clear();
					render();
					affichage.update();
					Display.sync(120);
					Mouse.poll();
				}
				setChangedW();
			}

			notifyObserversU(units);
			notifyObserversB(bases);
			notifyObserversW(walls);
			leftClicked = false;
		}

	}

	/**
	 * Effectue le rendu de la map
	 */
	public void render() {
		map.paint();

	}

	/**
	 * Effectue l'enregistrement des murs dans un fichier
	 * 
	 * @param wFileName
	 *            fichier d'enregistrement
	 */
	private void saveWallsToFile(String wFileName) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(wFileName);
			pw.print(walls.size());
			pw.println();
			for (Wall wall : walls) {
				pw.print(wall.getCharac());
				pw.println();
			}
		} catch (Exception e) {
			System.out.println("ERROR");
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Effectue l'enregistrement des Bases dans un fichier
	 * 
	 * @param wFileName
	 *            fichier d'enregistrement
	 */
	private void saveBasesToFile(String bFileName) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(bFileName);
			pw.print(bases.size());
			pw.println();
			for (Base base : bases) {
				pw.print(base.getCharac());
				pw.println();
			}
		} catch (Exception e) {
			System.out.println("ERROR");
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Effectue l'enregistrement des Unités dans un fichier
	 * 
	 * @param wFileName
	 *            fichier d'enregistrement
	 */
	private void saveUnitsToFile(String bFileName) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(bFileName);
			for (Unit unit : units) {
				pw.print(unit.getCharac());
				pw.println();
			}
		} catch (Exception e) {
			System.out.println("ERROR");
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/*
	 * Sauvegarde toutes les Arraylists d'objets dans leur fichier respectif
	 */
	private void saveToFile() {
		saveWallsToFile("wFile.txt");
		saveBasesToFile("bFile.txt");
		saveUnitsToFile("uFile.txt");
	}

	public void changementMode3D() {
		/*
		 * Matrice de projection (3D vers 2D): utilisation d'une projection
		 * perspective
		 */
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		if (MODE3D)
			GLU.gluPerspective(70.0f, display_width / display_height, 1.0f,
					10000.0f);
		else
			glOrtho(0, display_width, display_height, 0, -100, 0);
		
		/*
		 * Si on est en mode 3D, on initialise la 3D
		 */

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		if (MODE3D) {

			/*
			 * Position de la caméra
			 */
			GLU.gluLookAt(0, 0,
					(float) 50, (float) display_width / 2,
					(float) display_height / 2, (float) 0, 0, 0, 1);

		}
	}

	public void rotate() {
		/*
		 * Sert à faire tourner la carte sur elle-même
		 */
		GL11.glTranslatef(display_width / 2, display_height / 2, 0);
		GL11.glRotated(0.1, 0, 0, 1);
		GL11.glTranslatef(-display_width / 2, -display_height / 2, 0);
	}

	public void wAlea() {
		/*
		 * Sert à faire bouger les murs créés aléatoirement
		 */
		for (Wall wall : walls) {
			wall.aleaMove();
			notifyObserversW(walls);
		}
	}

	private void eclairage() {
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
