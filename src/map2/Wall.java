package map2;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import OpenGL.Textures;

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
	private Point extremity1;
	private Point extremity2;
	private Point vect;
	private float normev;
	private int thickness;
	private final int height = 50;
	private double px=0.5;
	private double py=0.5;
	private double pr=0.5;
	private final double probParam=80.0;
	private double angle;
	
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

	public Wall(Point point, Point point2, int thickness, int type){
		this.extremity1 = point;
		this.extremity2 = point2;
		this.thickness=thickness;
		
	
		switch (type) {
		case NORMAL: {
			init();
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
	
		
	private void init (){
		vect = new Point(extremity2.getX() - extremity1.getX(),extremity2.getY() - extremity1.getY());
		normev = (float)Math.hypot(vect.getX(),vect.getY());
		if(vect.getX()>0)
			angle=Math.asin(vect.getY()/normev);
		else
			angle=Math.PI-Math.asin(vect.getY()/normev);
		initializeSommets();
	}
	
	
	private void computeHorizontal() {
		extremity2.setY(extremity1.getY());
		init();
		computeSommets();
		
	}

	private void computeVertical() {
		extremity2.setX(extremity1.getX());
		init();
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
		init();
		computeSommets();

	}

	/**
	 * Calcul la position des 4 sommets de la base du mur en fonction de ses
	 * extrémités et de son épaisseur
	 * 
	 */
	private void computeSommets() {
		double rapport;
		double norme;
		double invNorme;
		vect = new Point(extremity2.getX() - extremity1.getX(),extremity2.getY() - extremity1.getY());
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
		int nbre= Math.round(normev/50)+1;
		
		GL11.glColor3f(1.0f,1.0f,1.0f);
		glEnable(GL11.GL_TEXTURE_2D);
		if (Textures.textureWall==null)
			Textures.initTextureWall();
		Color.white.bind();
		Textures.textureWall.bind();
		glBegin(GL_QUADS); // Définition du type de liaison entre les points
		// Chargement de la couleur du mur
		
		//glColor3ub((byte) color.getRed(), (byte) color.getGreen(),
				//(byte) color.getBlue());

	
		GL11.glTexCoord2f(0,0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		GL11.glTexCoord2f(1,0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);
		GL11.glTexCoord2f(1,1);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		GL11.glTexCoord2f(0,1);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);

		GL11.glTexCoord2f(0,0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);
		GL11.glTexCoord2f(nbre,0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);

		GL11.glTexCoord2f(0,0);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);
		GL11.glTexCoord2f(1,0);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		GL11.glTexCoord2f(1,1);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);
		GL11.glTexCoord2f(0,1);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);

		GL11.glTexCoord2f(0,0);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);
		GL11.glTexCoord2f(nbre,nbre);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		GL11.glTexCoord2f(0,nbre);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);

		GL11.glTexCoord2f(0,0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);
		GL11.glTexCoord2f(0,1);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		GL11.glTexCoord2f(1,1);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);
		GL11.glTexCoord2f(0,1);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);

		GL11.glTexCoord2f(0,0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		GL11.glTexCoord2f(0,1);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);
		GL11.glTexCoord2f(1,1);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		GL11.glTexCoord2f(0,1);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);

		GL11.glEnd();
		
	
	}
	
	public void translateX(double longueur){
		extremity1.setX(extremity1.getX()+longueur);
		extremity2.setX(extremity2.getX()+longueur);
	}
	
	public void translateY(double longueur){
		extremity1.setY(extremity1.getY()+longueur);
		extremity2.setY(extremity2.getY()+longueur);
	}
	
	public void rotate(double inc){
		Point centre=new Point(extremity1.getX()+vect.getX()/2,extremity1.getY()+vect.getY()/2);
		angle+=inc*Math.PI/180;
		extremity1.setX(normev/2*Math.cos(angle+Math.PI)+centre.getX());
		extremity1.setY(normev/2*Math.sin(angle+Math.PI)+centre.getY());
		extremity2.setX(normev/2*Math.cos(angle)+centre.getX());
		extremity2.setY(normev/2*Math.sin(angle)+centre.getY());
		initializeSommets();
		computeSommets();
	
	}
	
	public void aleaMove(){
		boolean incx1 = RandomPerso.bernoulli(px);
		boolean incy1 = RandomPerso.bernoulli(py);
		boolean sensd= RandomPerso.bernoulli(pr);
		
		if (sensd){
			rotate(1);
			pr=(probParam-1.0)/probParam;
			
		}else{
				rotate(-1);
			pr=1.0/probParam;
		}
		
		if (incx1){
			if(extremity1.getX()<Map.width && extremity2.getX()<Map.width) {
			translateX(1);
			px=(probParam-1.0)/probParam;
			}
			else
				px=0.0;
			
		}else{
			if(extremity1.getX()>0 && extremity2.getX()>0) {
				translateX(-1);
			px=1.0/probParam;
			}
			else
				px=1.0;
		}
		
		if (incy1){
			if(extremity1.getY()<Map.length && extremity2.getY()<Map.length) {
				translateY(1);
			py=(probParam-1.0)/probParam;
			}
			else
				py=0.0;
		}else{
			if(extremity1.getY()>0 && extremity2.getY()>0) {
				translateY(-1);
			py=1.0/probParam;;
			}
			else
				py=1.0;
		}
	
		initializeSommets();
		computeSommets();
	
	}

}
