package utilities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;

import views.monsters.MonsterView;

/**
 * Dessin de différentes courbes
 * a utiliser pour les attaques
 * @author berthier
 *
 */
public class Lines {
	private static float  a = 0;
	private static double beta = 0;
	private static double adistance=0;
	private static int nbVect=0;
	private static Vector vects[];
	private static Vector hvects[];
	private static Point points[];
	
	/**
	 * Calculs necessaires au trace
	 * 
	 * @param angle
	 *            angle du cone
	 * @param distance
	 *            Distance au centre du cone
	 * @param periode
	 *            periode de la sinusoide
	 * @param pas
	 *            Le pas entre chaque point
	 */
	public static void compute(float angle, float distance, float periode,float pas) {
		a=0;
		for (int i = 0; i <= nbVect; i++) {
			beta = (Math.PI - angle * Math.PI / 180) / 2 + a * Math.PI / 180;
			points[i] = new Point(distance * Math.cos(beta), distance* Math.sin(beta));
			a += pas;
		}

		
		for (int i = 0; i < nbVect/2; i++) {
			vects[i] = Maths.makeVector(points[i], points[i + 1]);
			hvects[i] = Maths.getNormalvector(vects[i]);
		}
		for (int i = nbVect/2; i < nbVect; i++) {
			vects[i] = Maths.makeVector(points[i], points[i + 1]);
			hvects[i] = Maths.opposite(Maths.getNormalvector(vects[i]));
		}
		

	}
	
	
	
	/**
	 * Dessine des cercles non modules
	 * @param angle
	 * @param distance
	 * @param periode
	 * @param pas
	 */
	public static void drawCircle (float angle, float distance, float periode,float pas){
		nbVect = Math.round(angle / pas);
		vects = new Vector[nbVect];
		hvects = new Vector[nbVect];
		points= new Point[nbVect + 1];
		compute( angle,distance, periode, pas);
		a=0;
		GL11.glBegin(GL11.GL_LINE_STRIP);

		for (int i = 0; i < nbVect; i++) {
			
			adistance = 0;
			
			GL11.glVertex3d(points[i].getX() + adistance*hvects[i].getX(),points[i].getY() + adistance*hvects[i].getY(), -adistance/2);
			a+=pas;
		}

		GL11.glEnd();
	}
	
	/**
	 * Dessine des cercles modules par une sinusoide
	 * @param angle
	 * @param distance
	 * @param periode
	 * @param pas
	 */
	public static void drawSinus (float angle, float distance, float periode,float pas){
		nbVect = Math.round(angle / pas);
		vects = new Vector[nbVect];
		hvects = new Vector[nbVect];
		points= new Point[nbVect + 1];
		compute( angle,distance, periode, pas);
		float a=0;
		GL11.glBegin(GL11.GL_LINE_STRIP);

		for (int i = 0; i < nbVect; i++) {
			
			adistance = distance/10 *Math.cos(2 * Math.PI * a / periode);
			
			GL11.glVertex3d(points[i].getX() + adistance*hvects[i].getX(),points[i].getY() + adistance*hvects[i].getY(), -adistance/2);
			a+=pas;
		}

		GL11.glEnd();
	}
	
	
	/**
	 * Dessine des cercles modules par un creneau
	 * @param angle
	 * @param distance
	 * @param periode
	 * @param pas
	 */
	public static void drawCreneau (float angle, float distance, float periode,float pas){
		nbVect = Math.round(angle / pas);
		vects = new Vector[nbVect];
		hvects = new Vector[nbVect];
		points= new Point[nbVect + 1];
		compute( angle,distance, periode, pas);
		float a=0;
		GL11.glBegin(GL11.GL_LINE_STRIP);

		for (int i = 0; i < nbVect; i++) {
			
			adistance = distance/10 *creneau(2 * Math.PI * a / periode);
			
			GL11.glVertex3d(points[i].getX() + adistance*hvects[i].getX(),points[i].getY() + adistance*hvects[i].getY(), -adistance/2);
			a+=pas;
		}

		GL11.glEnd();
	}
	
	
	/**
	 * renvoit un creneau de periode 2*PI
	 * @param x
	 * @return
	 */
	public static double creneau(double x){
		
		if (Math.cos(x)>=0.5)
			return 1.0;
		return 0.0;
	}

}
