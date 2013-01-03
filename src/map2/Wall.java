package map2;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import org.lwjgl.util.ReadableColor;

/**
 * Les murs de la map
 * 
 * @author paul
 * 
 */
public class Wall {
	public static final int NORMAL = 0;
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	private ReadableColor color = Color.GREY;
	private Point extremity1;
	private Point extremity2;
	private Point vect;
	private double rapport;
	private double norme;
	private double invNorme;
	private int thickness;
	private final int height = 20;
	private Point[] sommets = new Point[4];

	/**
	 * 
	 * 
	 * @param point
	 *            Extrémité du mur
	 * @param point2
	 *            Autre extrémité du mur
	 * @param thickness
	 *            Epaisseur du mur
	 */

	public Wall(Point point, Point point2, int thickness, int type) {
		this.extremity1 = point;
		this.extremity2 = point2;
		this.thickness=thickness;
	
		switch (type) {
		case NORMAL: {
			initializeSommets();
			computeSommets();
			break;
		}
		case HORIZONTAL: {
				computeHorizontal();	
			break;
		}
		case VERTICAL: {
			computeVertical();	
		break;
	}

		}

	}

	private void computeHorizontal() {
		extremity2.setY(extremity1.getY());
		initializeSommets();
		computeSommets();
		
	}

	private void computeVertical() {
		extremity2.setX(extremity1.getX());
		initializeSommets();
		computeSommets();
	}
 
	/**
	 * Initialise le tableau de sommets
	 * 
	 */
	private void initializeSommets() {
		for (int i = 0; i < 2; i++) {
			sommets[i] = new Point(extremity1.getX() , extremity1.getY()  );
		}
		for (int i = 2; i < 4; i++) {
			sommets[i] = new Point(extremity2.getX()  , extremity2.getY()  );
		}
	}

	/**
	 * 
	 * 
	 * @param extremity1
	 *            Extrémité du mur
	 * @param extremity2
	 *            Autre extrémité du mur
	 * @param thickness
	 *            Epaisseur du mur
	 */
	public void ChangeWall(Point extremity1, Point extremity2, int thickness) {
		this.extremity1 = extremity1;
		this.extremity2 = extremity2;
		this.thickness = thickness;
		computeSommets();

	}

	/**
	 * Calcul la position des 4 sommets de la base du mur en fonction de ses
	 * extrémités et de son épaisseur
	 * 
	 */
	private void computeSommets() {
		
			vect = new Point(extremity2.getX() - extremity1.getX(),
					extremity2.getY() - extremity1.getY());
			if(vect.getY()!=0){
			rapport = vect.getX() / vect.getY();
			norme = Math.hypot(1, rapport);
			invNorme = 1 / norme;
			sommets[0].translate((-1 * invNorme * thickness / 2), (rapport
					* invNorme * thickness / 2));
			sommets[1].translate((1 * invNorme * thickness / 2), (-rapport
					* invNorme * thickness / 2));
			sommets[2].translate((-1 * invNorme * thickness / 2), (rapport
					* invNorme * thickness / 2));
			sommets[3].translate((1 * invNorme * thickness / 2), (-rapport
					* invNorme * thickness / 2));
			}else{
			sommets[0].translate(0, thickness/2);
			sommets[1].translate(0, -thickness/2);
			sommets[2].translate(0, thickness/2);
			sommets[3].translate(0, -thickness/2);
		
			}
	}

	public void paint() {

		glBegin(GL_QUADS); // Définition du type de liaison entre les points
		// Chargement de la couleur du mur
		//glColor3ub((byte) color.getRed(), (byte) color.getGreen(),
				//(byte) color.getBlue());
		GL11.glColor3f(1.0f,0.0f,1.0f);

		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);

		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);

		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);

		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);

		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);

		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);

		GL11.glEnd();
	}

}
