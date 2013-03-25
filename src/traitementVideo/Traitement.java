package traitementVideo;

/**
 * 
 * @author Felix
 */

import java.util.*;
import utilities.*;

public class Traitement {

	/**
	 * Cette méthode permet de parcourir l'image pour en déterminer les
	 * composantes connexes (ici les taches lumineueses) La structure de données
	 * employée ici est une structure Union-Find basique (tourne en O(n*ln(n))
	 * ou n nombre de pixels)
	 */
	private int fps = 6;
	private ArrayList<ArrayList<VirtualPixel>> groupesConnexes;
	ArrayList<Point> groupesConnexesPos = new ArrayList<Point>();
	private int LENGTH = 640;
	private int HEIGHT = 480;
	private int rayonConnexe = 5;
	private int distance = 7;
	private int taille = 11; // 30 (deux medians un moyen)
	private int seuil = 11; // 17 (deux medians un moyen)
	private int nseuils = 0; // nombre de pixels au dessus du seuil, utile pour
								// debug et reglage des seuils
	private int ncomp = 0; // nombre de composantes connexes, utile pour debug
							// et reglage des seuils
	private VirtualPixel[][] traitScreen; // le premier indice correspond a
											// x(gauche/droite) et le second a y
											// (haut/bas)

	public Traitement(int LENGTH, int HEIGHT) {
		super();
		this.LENGTH = LENGTH;
		this.HEIGHT = HEIGHT;
		this.traitScreen = new VirtualPixel[LENGTH][HEIGHT];
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				traitScreen[i][j] = new VirtualPixel(false, 0, new Point(i, j),
						(byte) 0);
			}
		}
	}

	/**
	 * Constructeur uttilise pour les tests unitaires
	 */
	public Traitement() {
		super();
	}

	public void flouGaussien(int LENGTH, int HEIGHT) {
		this.LENGTH = LENGTH;
		this.HEIGHT = HEIGHT;
		flouGaussien();
	}

	public void flouGaussien() {
		byte[][] gauss = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };
		byte[][] newCoefs = new byte[LENGTH][HEIGHT];
		// ici on calcule les nouvelles intensites apres le passage du filtre
		// gaussien

		// on pourrait l'appliquer proprement sur les bords mais je ne sais pas
		// comment se comporte un noyau de convolution au niveau des effets de
		// bord
		for (int i = 2; i < (LENGTH - 2); i++) {
			for (int j = 2; j < (HEIGHT - 2); j++) {
				byte coef = (byte) ((gauss[0][0]
						* traitScreen[i - 1][j - 1].getIntensite()
						+ gauss[0][1] * traitScreen[i - 1][j].getIntensite()
						+ gauss[0][2]
						* traitScreen[i - 1][j + 1].getIntensite()
						+ gauss[1][0] * traitScreen[i][j - 1].getIntensite()
						+ gauss[1][1] * traitScreen[i][j].getIntensite()
						+ gauss[1][2] * traitScreen[i][j + 1].getIntensite()
						+ gauss[2][0]
						* traitScreen[i + 1][j - 1].getIntensite()
						+ gauss[2][1] * traitScreen[i + 1][j].getIntensite() + gauss[2][2]
						* traitScreen[i + 1][j + 1].getIntensite()) / 16);
				newCoefs[i][j] = coef;
			}
		}
		// on change les intensites pour les nouvelles intensites
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				traitScreen[i][j].setIntensite(newCoefs[i][j]);
			}
		}
	}

	public void flouMoyen() {
		byte[][] median = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
		byte[][] newCoefs = new byte[LENGTH][HEIGHT];
		// ici on calcule les nouvelles intensites apres le passage du filtre
		// gaussien

		// on pourrait l'appliquer proprement sur les bords mais je ne sais pas
		// comment se comporte un noyau de convolution au niveau des effets de
		// bord
		for (int i = 2; i < (LENGTH - 2); i++) {
			for (int j = 2; j < (HEIGHT - 2); j++) {
				byte coef = (byte) ((median[0][0]
						* traitScreen[i - 1][j - 1].getIntensite()
						+ median[0][1] * traitScreen[i - 1][j].getIntensite()
						+ median[0][2]
						* traitScreen[i - 1][j + 1].getIntensite()
						+ median[1][0] * traitScreen[i][j - 1].getIntensite()
						+ median[1][1] * traitScreen[i][j].getIntensite()
						+ median[1][2] * traitScreen[i][j + 1].getIntensite()
						+ median[2][0]
						* traitScreen[i + 1][j - 1].getIntensite()
						+ median[2][1] * traitScreen[i + 1][j].getIntensite() + median[2][2]
						* traitScreen[i + 1][j + 1].getIntensite()) / 9);
				newCoefs[i][j] = coef;
			}
		}
		// on change les intensites pour les nouvelles intensites
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				traitScreen[i][j].setIntensite(newCoefs[i][j]);
			}
		}
	}

	public void flouMedian() {
		byte[][] newCoefs = new byte[LENGTH][HEIGHT];
		byte[] buffer = new byte[9];
		for (int i = 2; i < (LENGTH - 2); i++) {
			for (int j = 2; j < (HEIGHT - 2); j++) {
				buffer[0] = traitScreen[i - 1][j - 1].getIntensite();
				buffer[1] = traitScreen[i - 1][j].getIntensite();
				buffer[2] = traitScreen[i - 1][j + 1].getIntensite();
				buffer[3] = traitScreen[i][j - 1].getIntensite();
				buffer[4] = traitScreen[i][j].getIntensite();
				buffer[5] = traitScreen[i][j + 1].getIntensite();
				buffer[6] = traitScreen[i + 1][j - 1].getIntensite();
				buffer[7] = traitScreen[i + 1][j].getIntensite();
				buffer[8] = traitScreen[i + 1][j + 1].getIntensite();
				byte coef = findNthLowestNumber(buffer, (byte) 9, (byte) 5);
				newCoefs[i][j] = coef;
			}
		}
		// on change les intensites pour les nouvelles intensites
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				traitScreen[i][j].setIntensite(newCoefs[i][j]);
			}
		}
	}

	public void seuil() {
		nseuils = 0;
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (traitScreen[i][j].getIntensite() >= seuil) {
					traitScreen[i][j].setBrightness(true);
					nseuils++;
				} else {
					traitScreen[i][j].setBrightness(false);
				}
			}
		}
	}

	public void updateConnexe(VirtualPixel[][] screen, int LENGTH, int HEIGHT) {
		this.LENGTH = LENGTH;
		this.HEIGHT = HEIGHT;
		updateConnexe(screen);
	}

	public void updateConnexe() {
		updateConnexe(traitScreen);
	}

	public void updateConnexe(VirtualPixel[][] screen) {
		traitScreen = screen;
		ArrayList<ArrayList<VirtualPixel>> groupesConnexesIntermediaire;
		groupesConnexesIntermediaire = new ArrayList<ArrayList<VirtualPixel>>();
		groupesConnexesIntermediaire.add(new ArrayList<VirtualPixel>()); // on
																			// ajoute
																			// la
																			// liste
																			// des
																			// pixels
																			// de
																			// groupe
																			// connexe
																			// 0
		int compteurComposante = 1;

		// on remet les composantes a zero
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				traitScreen[i][j].setGroupeConnexe(0);
			}
		}

		if (traitScreen[0][0].isBrightness()) {
			groupesConnexesIntermediaire.add(new ArrayList<VirtualPixel>());
			traitScreen[0][0].setGroupeConnexe(compteurComposante);
			groupesConnexesIntermediaire.get(compteurComposante).add(
					traitScreen[0][0]);
			compteurComposante++;
		}
		// parcours de la premiere ligne
		for (int j = 1; j < HEIGHT; j++) {
			if (traitScreen[0][j].isBrightness()) {
				if (!traitScreen[0][j - 1].isBrightness()) {
					groupesConnexesIntermediaire
							.add(new ArrayList<VirtualPixel>());
					traitScreen[0][j].setGroupeConnexe(compteurComposante);
					groupesConnexesIntermediaire.get(compteurComposante).add(
							traitScreen[0][j]);
					compteurComposante++;
				} else {
					int newGroupe = traitScreen[0][j - 1].getGroupeConnexe();
					traitScreen[0][j].setGroupeConnexe(newGroupe);
					groupesConnexesIntermediaire.get(newGroupe).add(
							traitScreen[0][j]);
				}

			}

		}
		// parcours de la premiere colone
		for (int i = 1; i < LENGTH; i++) {
			if (traitScreen[i][0].isBrightness()) {
				if (!traitScreen[i - 1][0].isBrightness()) {
					groupesConnexesIntermediaire
							.add(new ArrayList<VirtualPixel>());
					traitScreen[i][0].setGroupeConnexe(compteurComposante);
					groupesConnexesIntermediaire.get(compteurComposante).add(
							traitScreen[i][0]);
					compteurComposante++;
				} else {
					int newGroupe = traitScreen[i - 1][0].getGroupeConnexe();
					traitScreen[i][0].setGroupeConnexe(newGroupe);
					groupesConnexesIntermediaire.get(newGroupe).add(
							traitScreen[i][0]);
				}

			}

		}
		// parcours du reste du tableau normalement
		for (int i = 1; i < LENGTH; i++) {
			for (int j = 1; j < HEIGHT; j++) {
				if (traitScreen[i][j].isBrightness()) {
					if ((traitScreen[i - 1][j].isBrightness())
							&& !(traitScreen[i][j - 1].isBrightness())) {

						int newGroupe = traitScreen[i - 1][j]
								.getGroupeConnexe();
						traitScreen[i][j].setGroupeConnexe(newGroupe);
						groupesConnexesIntermediaire.get(newGroupe).add(
								traitScreen[i][j]);
					}
					if (!(traitScreen[i - 1][j].isBrightness())
							&& (traitScreen[i][j - 1].isBrightness())) {
						int newGroupe = traitScreen[i][j - 1]
								.getGroupeConnexe();
						traitScreen[i][j].setGroupeConnexe(newGroupe);
						groupesConnexesIntermediaire.get(newGroupe).add(
								traitScreen[i][j]);
					}
					if (!(traitScreen[i - 1][j].isBrightness())
							&& !(traitScreen[i][j - 1].isBrightness())) {
						groupesConnexesIntermediaire
								.add(new ArrayList<VirtualPixel>());
						traitScreen[i][j].setGroupeConnexe(compteurComposante);
						groupesConnexesIntermediaire.get(compteurComposante)
								.add(traitScreen[i][j]);
						compteurComposante++;
					}
					if ((traitScreen[i - 1][j].isBrightness())
							&& (traitScreen[i][j - 1].isBrightness())) {
						int groupe1 = traitScreen[i - 1][j].getGroupeConnexe();
						int groupe2 = traitScreen[i][j - 1].getGroupeConnexe();
						traitScreen[i][j].setGroupeConnexe(groupe2);
						groupesConnexesIntermediaire.get(groupe2).add(
								traitScreen[i][j]);
						uniongroupesConnexeIntermediare(
								groupesConnexesIntermediaire, groupe1, groupe2);
					}
				}
			}
		}

		// on actualise le tableau des groupes connexes
		ArrayList<ArrayList<VirtualPixel>> vase = new ArrayList<ArrayList<VirtualPixel>>();
		vase.add(new ArrayList<VirtualPixel>());
		for (ArrayList<VirtualPixel> list : groupesConnexesIntermediaire) {
			if (list.size() > taille) {
				vase.add(list);
			}
		}
		groupesConnexes = vase;

		// on calcule le centre de chacune des composantes
		groupesConnexesPos.clear();
		for (ArrayList<VirtualPixel> array : groupesConnexes) {
			double xMoy = 0;
			double yMoy = 0;
			for (VirtualPixel vp : array) {
				xMoy = xMoy + vp.getPos().getX();
				yMoy = yMoy + vp.getPos().getY();
			}
			groupesConnexesPos.add(new Point((int) (xMoy / array.size()),
					(int) (yMoy / array.size())));
		}

		// on reunnit les composantes proches les unes des autres, car les
		// taches secondaires des diodes sont consideree comme distinctes (pas
		// la même composante, meme si c'est le meme centre)
		boolean[] removed = new boolean[groupesConnexes.size()];
		for (int i = 0; i < removed.length; i++)
			removed[i] = false;
		ArrayList<Point> remove = new ArrayList<Point>();
		for (int i = 0; i < removed.length; i++) {
			if (!removed[i]) {
				Point point = groupesConnexesPos.get(i);
				for (int j = 0; j < removed.length; j++) {
					if ((!removed[j])
							&& (i != j)
							&& (groupesConnexesPos.get(j).distanceTo(point) <= rayonConnexe)) {
						remove.add(groupesConnexesPos.get(j));
						removed[j] = true;
					}
				}
			}
		}
		for (Point point : remove)
			groupesConnexesPos.remove(point);
		groupesConnexesPos.trimToSize();

		ncomp = groupesConnexesPos.size() - 1;
	}

	/**
	 * Renvoie la position de tous les groupes connexes
	 * 
	 * @param groupesConnexeIntermediare
	 * @return
	 */
	public ArrayList<Point> getGroupesPos() {
		return groupesConnexesPos;
	}

	/**
	 * Methode qui permet de mettre à jour la structure Union-Find mise en place
	 * actuellemnt elle ajoute la plus petite ArrayList à la plus grande
	 * (complexité en nln(n) où n nombre de pixels à true) (si j'ai du courage
	 * j'implémenterai celle de Tarjan ou de Hopcroft-Ullman mais je promets
	 * rien)
	 * 
	 * @param groupesConnexeIntermediare
	 * @param groupe1
	 * @param groupe2
	 */
	private static void uniongroupesConnexeIntermediare(
			ArrayList<ArrayList<VirtualPixel>> groupesConnexeIntermediare,
			int groupe1, int groupe2) {
		if (groupesConnexeIntermediare.get(groupe1).size() < groupesConnexeIntermediare
				.get(groupe2).size()) {
			groupesConnexeIntermediare.get(groupe2).addAll(
					groupesConnexeIntermediare.get(groupe1));
			groupesConnexeIntermediare.get(groupe1).clear();
		} else {
			groupesConnexeIntermediare.get(groupe1).addAll(
					groupesConnexeIntermediare.get(groupe2));
			groupesConnexeIntermediare.get(groupe2).clear();
		}
	}

	/**
	 * Methode qui renvoie la position moyenne d'une composante connexe On
	 * considere que le centre de la tache est son barycentre
	 * 
	 * @param groupe
	 * @return
	 */
	public Point getGroupePos(int groupe) {
		return groupesConnexesPos.get(groupe);
	}

	/**
	 * Methode qui permet d'actualiser la position et la traitScreentesse des
	 * cubes Elle utilise une recherche locale en partant de la position
	 * attendue du cube Methode un peu dégueu mais je vois pas trop comment la
	 * faire avec un joli boucle
	 * 
	 * @param vc
	 */
	public void localSearch(VideoCube vc) {
		int expectedX1 = (int) (vc.getPos1().getX() + vc.getX1Speed() * fps);
		int expectedX2 = (int) (vc.getPos2().getX() + vc.getX2Speed() * fps);
		int expectedY1 = (int) (vc.getPos1().getY() + vc.getY1Speed() * fps);
		int expectedY2 = (int) (vc.getPos2().getY() + vc.getY2Speed() * fps);

		/*
		 * Ici on établie les pixels à distance distance de la position attendue
		 * Ensuite on parcours ces pixels par distance croissante a la position
		 * attendue Si on trouve une tache lumineuse on considere que c'est la
		 * nouvelle position et on sort de la boucle Si on ne trouve pas de
		 * tache lumineuse on appelle le GameEngine
		 */
		ArrayList<ArrayList<VirtualPixel>> pos1Lists = parcoursSpirale(
				(int) vc.getPos1().getX(), (int) vc.getPos1().getY(), distance);
		boolean found1 = false;
		for (ArrayList<VirtualPixel> list : pos1Lists) {
			for (VirtualPixel vp : list) {
				if (vp.isBrightness()) {
					updatePosSpeed1(vc, vp.getPos().getX(), vp.getPos().getY());
					found1 = true;
					break;
				}
			}
			if (found1)
				break;
		}
		if (!found1) /* gameEngine.setFrozen(null) */
			System.out.println("cube perdu");

		ArrayList<ArrayList<VirtualPixel>> pos2Lists = parcoursSpirale(
				(int) vc.getPos2().getX(), (int) vc.getPos2().getY(), distance);
		boolean found2 = false;
		for (ArrayList<VirtualPixel> list : pos2Lists) {
			for (VirtualPixel vp : list) {
				if (vp.isBrightness()) {
					updatePosSpeed2(vc, vp.getPos().getX(), vp.getPos().getY());
					found2 = true;
					break;
				}
			}
			if (found2)
				break;
		}
		if (!found2) /* gameEngine.setFrozen(null) */
			System.out.println("cube perdu");
		if (found1 && found2){
//			System.out.println("Je te vois");
			vc.updateMeanPos();
			System.out.println("x : " + vc.getMeanPos().getX()+ ", y : "+ vc.getMeanPos().getY());
		}
	}

	/**
	 * Methode qui met a jour la position et la traitScreentesse de la premiere
	 * diode d'un cube
	 * 
	 * @param vc
	 * @param newXPos
	 * @param newYPos
	 */
	private void updatePosSpeed1(VideoCube vc, double newXPos, double newYPos) {
		vc.setX1Speed((newXPos - vc.getPos1().getX()) / fps);
		vc.setY1Speed((newYPos - vc.getPos1().getX()) / fps);
		vc.getPos1().setX(newXPos);
		vc.getPos1().setY(newYPos);
	}

	/**
	 * Methode qui met a jour la position et la traitScreentesse de la seconde
	 * diode d'un cube
	 * 
	 * @param vc
	 * @param newXPos
	 * @param newYPos
	 */
	private void updatePosSpeed2(VideoCube vc, double newXPos, double newYPos) {
		vc.setX2Speed((newXPos - vc.getPos2().getX()) / fps);
		vc.setY2Speed((newYPos - vc.getPos2().getX()) / fps);
		vc.getPos2().setX(newXPos);
		vc.getPos2().setY(newYPos);
	}

	private ArrayList<ArrayList<VirtualPixel>> parcoursSpirale(int x, int y,
			int dist) {
		dist = distance;
		int upDist = Math.min(dist, y);
		int downDist = Math.min(dist, HEIGHT - y);
		int leftDist = Math.min(dist, x);
		int rightDist = Math.min(dist, LENGTH - x);
		ArrayList<ArrayList<VirtualPixel>> points = new ArrayList<ArrayList<VirtualPixel>>();
		int k = (int) Math.sqrt(2 * dist * dist) + 1;
		for (int l = 0; l <= k; l++)
			points.add(new ArrayList<VirtualPixel>());
		int d;
		for (int i = (x - leftDist); i <= (x + rightDist); i++) {
			for (int j = (y - upDist); j <= (y + downDist); j++) {
				d = (int) Math.sqrt(((i - x) * (i - x)) + ((j - y) * (j - y)));
				points.get(d).add(this.traitScreen[i][j]);
			}
		}
		return points;
	}

	public VirtualPixel[][] getTraitScreen() {
		return traitScreen;
	}

	public void setTraitScreen(VirtualPixel[][] traitScreen) {
		this.traitScreen = traitScreen;
	}

	/**
	 * Methode pour trouver la mediane d'un tableau de nombres Algorithme trouve
	 * dans "algorithmique" de Cormen Implementation trouvee sur
	 * http://www.developpez.net
	 * 
	 * @param buf
	 * @param bufLength
	 * @param n
	 * @return
	 */
	public static byte findNthLowestNumber(byte[] buf, int bufLength, int n) {
		// Modified algorithm according to
		// http://www.geocities.com/zabrodskyvlada/3alg.html
		// Contributed by Heinz Klar
		int i, j;
		int l = 0;
		int m = bufLength - 1;
		byte med = buf[n];
		byte dum;

		while (l < m) {
			i = l;
			j = m;
			do {
				while (buf[i] < med)
					i++;
				while (med < buf[j])
					j--;
				dum = buf[j];
				buf[j] = buf[i];
				buf[i] = dum;
				i++;
				j--;
			} while ((j >= n) && (i <= n));
			if (j < n)
				l = i;
			if (n < i)
				m = j;
			med = buf[n];
		}
		return med;
	}

	public int getNseuils() {
		return nseuils;
	}

	public void setNseuils(int nseuils) {
		this.nseuils = nseuils;
	}

	public int getNcomp() {
		return ncomp;
	}

	public void setNcomp(int ncomp) {
		this.ncomp = ncomp;
	}

}
