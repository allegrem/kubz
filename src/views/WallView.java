package views;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import map2.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.Color;

import utilities.Distance;
import utilities.Maths;
import utilities.Point;
import utilities.RandomPerso;
import utilities.Vector;

import OpenGL.Textures;

/**
 * Les murs de la map
 * 
 * @author paul
 * 
 */
public class WallView implements DisplayableFather{

	/*
	 * Constantes de classe. Servent à définir comment seront tracés les murs:
	 * -de manière classique, à l'horizontale ou à la verticale
	 * -avec des textures ou en couleur
	 */
	public static final int NORMAL = 0;
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	public final int COULEUR = 0;
	public final int TEXTURE = 1;

	/*
	 * Hauteur du mur
	 */
	private final int height = 50;

	/*
	 * Réglage de la probabilité que le mur fasse demi-tour. Plus probParam est
	 * grand, plus la probabiliré est faible.
	 */
	private final double probParam = 80.0;

	/*
	 * La façon dont sera peint le mur: en couleur ou avec texture
	 */
	private int typePeinture = TEXTURE;

	/*
	 * ON définit la couleur des murs
	 */
	private final Color color = Color.gray;

	private Point extremity1;
	private Point extremity2;
	private int thickness;// Epaisseur du mur

	private Vector vect; // Vecteur extremity1->extremity2
	private float normev;// Norme du vecteur
	private double angle;// Angle du vecteur avec l'horizontale

	/*
	 * Les 4 sommets de la base du mur
	 */
	private Point[] sommets = new Point[4];

	/*
	 * Les probabilités relatives au déplacement du mur
	 */
	private double px = 0.5;
	private double py = 0.5;
	private double pr = 0.5;

	/**
	 * Construction d'un mur
	 * 
	 * @param point
	 *            Extrémité du mur
	 * @param point2
	 *            Autre extrémité du mur
	 * @param thickness
	 *            Epaisseur du mur
	 * 
	 * @param type
	 *            Mode de création du mur (cf. constantes de classe)
	 * 
	 */

	public WallView(Point point, Point point2, int thickness, int type) {
		this.extremity1 = point;
		this.extremity2 = point2;
		this.thickness = thickness;

		switch (type) {
		case NORMAL: {
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

	/**
	 * Initialisation des paramètres concernant le mur: le vecteur directeur, sa
	 * norme et son angle avec l'horizontale.
	 * 
	 */
	private void init() {
		vect = new Vector(extremity2.getX() - extremity1.getX(),
				extremity2.getY() - extremity1.getY());
		normev = (float) Math.hypot(vect.getX(), vect.getY());

		if (vect.getX() > 0){
			/*
			 * On est bien dans l'espace d'arrivée
			 * de arcsin() ([-PI/2,PI/2])
			 */
			angle = Math.asin(vect.getY() / normev); 
		}
		else{
			/*
			 * On se place dans [PI/2,3*PI/2]
			 */
			angle = Math.PI - Math.asin(vect.getY() / normev);
		}
	}

	/**
	 * Mode de création de murs horizontaux
	 */
	private void computeHorizontal() {
		/*
		 * On modifie l'extrémité 2 pour que ordonnée soit la mème que celle de
		 * l'extrémité 1
		 */
		extremity2.setY(extremity1.getY());
		computeSommets();

	}

	/**
	 * Mode de création de murs verticaux
	 */
	private void computeVertical() {
		/*
		 * On modifie l'extrémité 2 pour que abscisse soit la mème que celle de
		 * l'extrémité 1
		 */
		extremity2.setX(extremity1.getX());
		computeSommets();
	}

	/**
	 * Initialise le tableau de sommets: les sommets 0 et 1 avec les coordonnées
	 * de l'extrémité 1 et les sommets 2 et 3 avec les coordonnées de
	 * l'extrémité 2
	 * 
	 */
	private void initializeSommets() {
		for (int i = 0; i < 2; i++) {
			sommets[i] = new Point(extremity1.getX(), extremity1.getY());
		}
		for (int i = 2; i < 4; i++) {
			sommets[i] = new Point(extremity2.getX(), extremity2.getY());
		}
	}

	/**
	 * Modifie l'emplacement et l'épaisseur du mur
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
	 * extrémités et de son épaisseur.
	 * 
	 */
	private void computeSommets() {

		double rapport;// Le rapport de l'absicsse sur l'ordonnée de vect
		double norme;// La norme du vecteur (-1,rapport) orthogonal à vect
		double invNorme;// L'inverse de cette norme

		init();
		initializeSommets();

		if (vect.getY() != 0) // On peut calculer le rapport
		{
			rapport = vect.getX() / vect.getY();
			norme = Math.hypot(1, rapport);
			invNorme = 1 / norme;

			/*
			 * Calcul des coordonnées des différents sommets. On utilise les
			 * extrémités (cf. initSommets()) que l'on translate dans la
			 * direction d'un vecteur hortogonal à vect sur une distance égale à
			 * la demi épaisseur dans un sens ou dans l'autre suivant le point.
			 */

			sommets[0].translate((-1 * invNorme * thickness / 2), (rapport
					* invNorme * thickness / 2));

			sommets[1].translate((1 * invNorme * thickness / 2), (-rapport
					* invNorme * thickness / 2));

			sommets[2].translate((-1 * invNorme * thickness / 2), (rapport
					* invNorme * thickness / 2));

			sommets[3].translate((1 * invNorme * thickness / 2), (-rapport
					* invNorme * thickness / 2));

		} else {
			/*
			 * On ne peut pas calculer le rapport mais les coordonnées se
			 * définissent facilement car vect est horizontal
			 */

			sommets[0].translate(0, thickness / 2);

			sommets[1].translate(0, -thickness / 2);

			sommets[2].translate(0, thickness / 2);

			sommets[3].translate(0, -thickness / 2);

		}
	}

	/**
	 * On dessine le mur
	 * 
	 */
	public void paint() {

		/*
		 * nombre de répétitions de la texture, si on l'utilise
		 */
		int nbre = Math.round(normev / 50) + 1;

		if (typePeinture == COULEUR) {
			/*
			 * On applique la couleur définie
			 */
			GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(),
					(byte) color.getBlue());

		} else if (typePeinture == TEXTURE) {

			glEnable(GL11.GL_TEXTURE_2D); // Activation du mode texture

			/*
			 * Si la texture n'est pas chargée, on la charge
			 */
			if (Textures.textureWall == null)
				Textures.initTextureWall();
			/*
			 * On colorie le fond en gris (;p) avant d'appliquer la texture
			 */
			Color.gray.bind();

			Textures.textureWall.bind();// On applique la texture

		}

		glBegin(GL_QUADS); // Définition du type de liaison entre les points

		/*
		 * Coordonnées de la texture correspondant à ce point du mur Si elles ne
		 * sont pas comprise dans [0,1], la texture sera reproduite plusieurs
		 * fois
		 */
		GL11.glTexCoord2f(0, 0);
		/*
		 * Tracé d'une extrémité du murSystem.out.println(normal.getX()+" "+normal.getY()+" "+normal.getZ());
		 */
		Vector vect1= Maths.makeVector(sommets[0].getX(), sommets[0].getY(), height,
				sommets[0].getX(), sommets[0].getY(), 0);
		Vector vect2= Maths.makeVector(sommets[1].getX(), sommets[1].getY(), 0,
				sommets[0].getX(), sommets[0].getY(), 0);   //CCW/CW??
		Vector normal=Maths.vect(vect2,vect1);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ()));  //set the normal vector for this face
		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		GL11.glTexCoord2f(1, 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);
		GL11.glTexCoord2f(1, 1);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		GL11.glTexCoord2f(0, 1);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);
		
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glTexCoord2f(0, 0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);
		GL11.glTexCoord2f(nbre, 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		GL11.glTexCoord2f(nbre, nbre);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		GL11.glTexCoord2f(0, nbre);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);
		
		vect1= Maths.makeVector(sommets[3].getX(), sommets[3].getY(), height,
				sommets[3].getX(), sommets[3].getY(), 0);
		vect2= Maths.makeVector(sommets[2].getX(), sommets[2].getY(), 0,
				sommets[3].getX(), sommets[3].getY(), 0);
		normal=Maths.vect(vect2,vect1);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ())); 
		GL11.glTexCoord2f(0, 0);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);
		GL11.glTexCoord2f(1, 0);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		GL11.glTexCoord2f(1, 1);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);
		GL11.glTexCoord2f(0, 1);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);
		
		GL11.glNormal3f(0.0f, 0.0f, -1.0f);
		GL11.glTexCoord2f(0, 0);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);
		GL11.glTexCoord2f(0, nbre);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);
		GL11.glTexCoord2f(nbre, nbre);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		GL11.glTexCoord2f(0, nbre);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);

		vect1= Maths.makeVector(sommets[1].getX(), sommets[1].getY(), height,
				sommets[1].getX(), sommets[1].getY(), 0);
		vect2= Maths.makeVector(sommets[3].getX(), sommets[3].getY(), 0,
				sommets[1].getX(), sommets[1].getY(), 0);
		normal=Maths.vect(vect2,vect1);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ())); 
		GL11.glTexCoord2f(0, 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);
		GL11.glTexCoord2f(0, 1);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), height);
		GL11.glTexCoord2f(1, 1);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), height);
		GL11.glTexCoord2f(0, 1);
		glVertex3d(sommets[3].getX(), sommets[3].getY(), 0);
		
		vect1= Maths.makeVector(sommets[0].getX(), sommets[0].getY(), height,
				sommets[0].getX(), sommets[0].getY(), 0);
		vect2= Maths.makeVector(sommets[2].getX(), sommets[2].getY(), 0,
				sommets[0].getX(), sommets[0].getY(), 0);
		normal=Maths.vect(vect1,vect2);
		GL11.glNormal3f((float)(normal.getX()), (float)(normal.getY()),(float)( normal.getZ())); 
		GL11.glTexCoord2f(0, 0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		GL11.glTexCoord2f(0, 1);
		glVertex3d(sommets[0].getX(), sommets[0].getY(), height);
		GL11.glTexCoord2f(1, 1);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), height);
		GL11.glTexCoord2f(0, 1);
		glVertex3d(sommets[2].getX(), sommets[2].getY(), 0);

		GL11.glEnd();

	}

	/**
	 * Translation du mur selon l'axe x
	 * 
	 * @param longueur
	 *            longueur de la translation
	 */
	public void translateX(double longueur) {

		extremity1.setX(extremity1.getX() + longueur);
		extremity2.setX(extremity2.getX() + longueur);

		/*
		 * On recalcule la position des sommets puisqu'ils ont bougé
		 */
		computeSommets();
	}

	/**
	 * Translation du mur selon l'axe y
	 * 
	 * @param longueur
	 *            longueur de la translation
	 */
	public void translateY(double longueur) {

		extremity1.setY(extremity1.getY() + longueur);
		extremity2.setY(extremity2.getY() + longueur);

		/*
		 * On recalcule la position des sommets puisqu'ils ont bougé
		 */
		computeSommets();
	}

	/**
	 * Rotation du mur
	 * 
	 * @param inc
	 *            Angle an degré de la rotation
	 */
	public void rotate(double inc) {

		/*
		 * Centre de la rotation
		 */
		Point centre = new Point(extremity1.getX() + vect.getX() / 2,
				extremity1.getY() + vect.getY() / 2);

		angle += inc * Math.PI / 180;// On incrémente l'angle du mur

		/*
		 * Calcul des nouvelles coordonnées des extrémités
		 */
		extremity1.setX(normev / 2 * Math.cos(angle + Math.PI) + centre.getX());
		extremity1.setY(normev / 2 * Math.sin(angle + Math.PI) + centre.getY());
		extremity2.setX(normev / 2 * Math.cos(angle) + centre.getX());
		extremity2.setY(normev / 2 * Math.sin(angle) + centre.getY());

		/*
		 * On recalcule la position des sommets puisqu'ils ont bougé
		 */
		computeSommets();

	}

	/**
	 * Génère un mouvement aléatoire des murs en translation se lon x et y et en
	 * rotation
	 */
	public void aleaMove() {

		boolean incx1 = RandomPerso.bernoulli(px);
		boolean incy1 = RandomPerso.bernoulli(py);
		boolean sensd = RandomPerso.bernoulli(pr);

		if (sensd) // Si le mur doit tourner dans le sens direct
		{
			rotate(1);

			/*
			 * On définit une probabilité très grande pour qu'au prochain tour
			 * le mur continue à tourner dans le sens direct, car sinon il ne va
			 * faire que "vibrer"
			 */
			pr = (probParam - 1.0) / probParam;

		} else // Sinon il tourne dans le sens indirect
		{
			rotate(-1);

			/*
			 * On définit une probabilité très faible pour qu'au prochain tour
			 * le mur continue à tourner dans le sens direct, car sinon il ne va
			 * faire que "vibrer"
			 */
			pr = 1.0 / probParam;
		}

		if (incx1)// Si le mur doit se déplacer vers la droite
		{
			/*
			 * On vérifie que le mur ne va pas sortir de la map
			 */
			if (extremity1.getX() < Map.width && extremity2.getX() < Map.width) {
				translateX(1);
				px = (probParam - 1.0) / probParam;
			} else
				/*
				 * Le mur doit absolument faire demi-tour si il touche le bord
				 * de la map
				 */
				px = 0.0;
		} else // Sinon il doit se déplacer vers la gauche
		{

			if (extremity1.getX() > 0 && extremity2.getX() > 0) {
				translateX(-1);
				px = 1.0 / probParam;
			} else
				px = 1.0;
		}

		if (incy1) // Si le mur doit se déplacer vers le bas
		{
			if (extremity1.getY() < Map.length
					&& extremity2.getY() < Map.length) {
				translateY(1);
				py = (probParam - 1.0) / probParam;
			} else
				py = 0.0;
		} else // Sinon il doit se déplacer vers le haut
		{

			if (extremity1.getY() > 0 && extremity2.getY() > 0) {
				translateY(-1);
				py = 1.0 / probParam;
				;
			} else
				py = 1.0;
		}

	}
	

	/**
	 * Renvoie une chaîne de caractère contenant les coordonnées des extrémités
	 * du mur ainsi que son épaisseur
	 * 
	 * @return La chaîne de caractères contenant les informations
	 */
	public String getCharac() {
		return extremity1.getX() + " " + extremity1.getY() + " "
				+ extremity2.getX() + " " + extremity2.getY() + " " + thickness;
	}



	@Override
	public int getTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTimeOut(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInZone(Point mousePoint) {
		
		
		if (Distance.distanceToLine(sommets[0], sommets[2],mousePoint)<= thickness && 
				Distance.distanceToLine(sommets[1], sommets[3],mousePoint) <= thickness &&
				Distance.distanceToLine(sommets[0],sommets[1],mousePoint)<=vect.norme() &&
				Distance.distanceToLine(sommets[2],sommets[3],mousePoint)<=vect.norme())
			{
			return true;
			}
		return false;
	}

	@Override
	public void setColor(ReadableColor color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getX() {
		
		return (extremity1.getX()+extremity2.getX())/2;
	}

	@Override
	public double getY() {
		return (extremity1.getY()+extremity2.getY())/2;
	}

	@Override
	public ArrayList<DisplayableChild> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(DisplayableChild object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeChild(DisplayableChild child) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public boolean collisionCanOccure(Point point, float taille) {
		Vector vect1=Maths.makeVector(getX(), getY(), 0, sommets[1].getX(), sommets[1].getY(), 0);
		double dist=vect1.norme();
		Vector vect2= Maths.makeVector(point.getX(), point.getY(), 0, getX(), getY(), 0);
		if(dist+taille>=vect2.norme())
			return true;
		return false;
	}

	
}
