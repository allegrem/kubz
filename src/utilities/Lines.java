package utilities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.GL11;

import views.MonsterView;

public class Lines {
	/**
	 * Dessine une sinusoide dans le cone d'attaque
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
	public static void sinusoide(float angle, float distance, float periode,float pas) {
		float a = 0;
		double beta = 0;
		int nbVect = Math.round(angle / pas);
		double adistance=0;
		
		Vector vects[] = new Vector[nbVect];
		Vector hvects[] = new Vector[nbVect];
		Point points[] = new Point[nbVect + 1];

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
		
		a=0;
		GL11.glBegin(GL11.GL_LINE_STRIP);

		for (int i = 0; i < nbVect; i++) {
			
			adistance = distance/10 * Math.cos(2 * Math.PI * a / periode);
			
			GL11.glVertex3d(points[i].getX() + adistance*hvects[i].getX(),points[i].getY() + adistance*hvects[i].getY(), -adistance/2);
			a+=pas;
		}
		GL11.glVertex3d(points[nbVect].getX() + hvects[nbVect - 1].getX(),points[nbVect].getY() + hvects[nbVect - 1].getY(), 0);

		GL11.glEnd();

	}

}
