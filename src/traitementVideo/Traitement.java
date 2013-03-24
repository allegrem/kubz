package traitementVideo;

import java.util.*;
import utilities.*;

public class Traitement {

	/**
	 * Cette méthode permet de parcourir l'image pour en déterminer les
	 * composantes connexes (ici les taches lumineueses) La structure de données
	 * employée ici est une structure Union-Find basique (tourne en O(n*ln(n))
	 * ou n nombre de pixels)
	 * 
	 * @param screen
	 * @return
	 */
	private int fps;
	private VirtualPixel[][] vi;
	private ArrayList<ArrayList<VirtualPixel>> groupesConnexes;
	private int LENGTH = 640;
	private int HEIGHT = 480;
	private int distance = 5;
	private int taille = 50;
	private int seuil = 90;
	private int nseuils = 0; //nombre de pixels au dessus du seuil, utile pour debug et reglage des seuils
	private int ncomp = 0; // nombre de composantes connexes, utile pour debug et reglage des seuils
	private VirtualPixel[][] traitScreen;

	public Traitement(int LENGTH, int HEIGHT) {
		super();
		this.LENGTH = LENGTH;
		this.HEIGHT = HEIGHT;
		this.traitScreen = new VirtualPixel[HEIGHT][LENGTH];
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < LENGTH; j++) {
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
		byte[][] newCoefs = new byte[HEIGHT][LENGTH];
		// ici on calcule les nouvelles intensites apres le passage du filtre
		// gaussien
		
		// on pourrait l'appliquer proprement sur les bords mais je ne sais pas
		// comment se comporte un noyau de convolution au niveau des effets de
		// bord
		for (int i = 2; i < (HEIGHT - 2); i++) {
			for (int j = 2; j < (LENGTH - 2); j++) {
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
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < LENGTH; j++) {
				traitScreen[i][j].setIntensite(newCoefs[i][j]);
			}
		}
	}

	public void flouMoyen() {
		byte[][] median = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
		byte[][] newCoefs = new byte[HEIGHT][LENGTH];
		// ici on calcule les nouvelles intensites apres le passage du filtre
		// gaussien

		// on pourrait l'appliquer proprement sur les bords mais je ne sais pas
		// comment se comporte un noyau de convolution au niveau des effets de
		// bord
		for (int i = 2; i < (HEIGHT - 2); i++) {
			for (int j = 2; j < (LENGTH - 2); j++) {
				byte coef = (byte) 
						((median[0][0] * traitScreen[i - 1][j - 1].getIntensite()
						+ median[0][1] * traitScreen[i - 1][j].getIntensite()
						+ median[0][2] * traitScreen[i - 1][j + 1].getIntensite()
						+ median[1][0] * traitScreen[i][j - 1].getIntensite()
						+ median[1][1] * traitScreen[i][j].getIntensite()
						+ median[1][2] * traitScreen[i][j + 1].getIntensite()
						+ median[2][0] * traitScreen[i + 1][j - 1].getIntensite()
						+ median[2][1] * traitScreen[i + 1][j].getIntensite() 
						+ median[2][2] * traitScreen[i + 1][j + 1].getIntensite()) / 9);
				newCoefs[i][j] = coef;
			}
		}
		// on change les intensites pour les nouvelles intensites
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < LENGTH; j++) {
				traitScreen[i][j].setIntensite(newCoefs[i][j]);
			}
		}
	}
	
	public void flouMedian(){
		byte[][] newCoefs = new byte[HEIGHT][LENGTH];
		byte[] buffer = new byte[9];
		for (int i = 2; i < (HEIGHT - 2); i++) {
			for (int j = 2; j < (LENGTH - 2); j++) {
				buffer[0] = traitScreen[i - 1][j - 1].getIntensite();
				buffer[1] = traitScreen[i - 1][j].getIntensite();
				buffer[2] = traitScreen[i - 1][j + 1].getIntensite();
				buffer[3] = traitScreen[i][j - 1].getIntensite();
				buffer[4] = traitScreen[i][j].getIntensite();
				buffer[5] = traitScreen[i][j + 1].getIntensite();
				buffer[6] = traitScreen[i + 1][j - 1].getIntensite();
				buffer[7] = traitScreen[i + 1][j].getIntensite() ;
				buffer[8] = traitScreen[i + 1][j + 1].getIntensite();			
				byte coef = findNthLowestNumber(buffer,(byte) 9,(byte) 5);
				newCoefs[i][j] = coef;
			}
		}
		// on change les intensites pour les nouvelles intensites
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < LENGTH; j++) {
				traitScreen[i][j].setIntensite(newCoefs[i][j]);
			}
		}
	}
	

	public void seuil() {
		nseuils= 0;
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < LENGTH; j++) {
				if (traitScreen[i][j].getIntensite() >= seuil) {
					traitScreen[i][j].setBrightness(true);
					nseuils ++;
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
		vi = screen;
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
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < LENGTH; j++) {
				traitScreen[i][j].setGroupeConnexe(0);
			}
		}

		if (vi[0][0].isBrightness()) {
			groupesConnexesIntermediaire.add(new ArrayList<VirtualPixel>());
			vi[0][0].setGroupeConnexe(compteurComposante);
			groupesConnexesIntermediaire.get(compteurComposante).add(vi[0][0]);
			compteurComposante++;
		}
		// parcours de la premiere ligne
		for (int j = 1; j < LENGTH; j++) {
			if (vi[0][j].isBrightness()) {
				if (!vi[0][j - 1].isBrightness()) {
					groupesConnexesIntermediaire
							.add(new ArrayList<VirtualPixel>());
					vi[0][j].setGroupeConnexe(compteurComposante);
					groupesConnexesIntermediaire.get(compteurComposante).add(
							vi[0][j]);
					compteurComposante++;
				} else {
					int newGroupe = vi[0][j - 1].getGroupeConnexe();
					vi[0][j].setGroupeConnexe(newGroupe);
					groupesConnexesIntermediaire.get(newGroupe).add(vi[0][j]);
				}

			}

		}
		// parcours de la premiere colone
		for (int i = 1; i < HEIGHT; i++) {
			if (vi[i][0].isBrightness()) {
				if (!vi[i - 1][0].isBrightness()) {
					groupesConnexesIntermediaire
							.add(new ArrayList<VirtualPixel>());
					vi[i][0].setGroupeConnexe(compteurComposante);
					groupesConnexesIntermediaire.get(compteurComposante).add(
							vi[i][0]);
					compteurComposante++;
				} else {
					int newGroupe = vi[i - 1][0].getGroupeConnexe();
					vi[i][0].setGroupeConnexe(newGroupe);
					groupesConnexesIntermediaire.get(newGroupe).add(vi[i][0]);
				}

			}

		}
		// parcours du reste du tableau normalement
		for (int i = 1; i < HEIGHT; i++) {
			for (int j = 1; j < LENGTH; j++) {
				if (vi[i][j].isBrightness()) {
					if ((vi[i - 1][j].isBrightness())
							&& !(vi[i][j - 1].isBrightness())) {

						int newGroupe = vi[i - 1][j].getGroupeConnexe();
						vi[i][j].setGroupeConnexe(newGroupe);
						groupesConnexesIntermediaire.get(newGroupe).add(
								vi[i][j]);
					}
					if (!(vi[i - 1][j].isBrightness())
							&& (vi[i][j - 1].isBrightness())) {
						int newGroupe = vi[i][j - 1].getGroupeConnexe();
						vi[i][j].setGroupeConnexe(newGroupe);
						groupesConnexesIntermediaire.get(newGroupe).add(
								vi[i][j]);
					}
					if (!(vi[i - 1][j].isBrightness())
							&& !(vi[i][j - 1].isBrightness())) {
						groupesConnexesIntermediaire
								.add(new ArrayList<VirtualPixel>());
						vi[i][j].setGroupeConnexe(compteurComposante);
						groupesConnexesIntermediaire.get(compteurComposante)
								.add(vi[i][j]);
						compteurComposante++;
					}
					if ((vi[i - 1][j].isBrightness())
							&& (vi[i][j - 1].isBrightness())) {
						int groupe1 = vi[i - 1][j].getGroupeConnexe();
						int groupe2 = vi[i][j - 1].getGroupeConnexe();
						vi[i][j].setGroupeConnexe(groupe2);
						groupesConnexesIntermediaire.get(groupe2).add(vi[i][j]);
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
		ncomp = groupesConnexes.size()-1;
	}

	/**
	 * Renvoie la position de tous les groupes connexes
	 * 
	 * @param groupesConnexeIntermediare
	 * @return
	 */
	public ArrayList<Point> getGroupesPos() {
		ArrayList<Point> groupesConnexesPos = new ArrayList<Point>();
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
		double xMoy = 0;
		double yMoy = 0;
		for (VirtualPixel vp : groupesConnexes.get(groupe)) {
			xMoy = xMoy + vp.getPos().getX();
			yMoy = yMoy + vp.getPos().getY();
		}
		int size = groupesConnexes.get(groupe).size();
		xMoy = xMoy / size;
		yMoy = yMoy / size;
		return new Point(xMoy, yMoy);
	}

	/**
	 * Methode qui permet d'actualiser la position et la vitesse des cubes Elle
	 * utilise une recherche locale en partant de la position attendue du cube
	 * Methode un peu dégueu mais je vois pas trop comment la faire avec un joli
	 * boucle
	 * 
	 * @param vc
	 */
	public void localSearch(VideoCube vc) {
		int expectedX1 = (int) (vc.getPos1().getX() + vc.getX1Speed());
		int expectedX2 = (int) (vc.getPos2().getX() + vc.getX2Speed());
		// int expectedX3 = (int) (vc.getPos3().getX() + vc.getX3Speed());
		int expectedY1 = (int) (vc.getPos1().getY() + vc.getY1Speed());
		int expectedY2 = (int) (vc.getPos2().getY() + vc.getY2Speed());
		// int expectedY3 = (int) (vc.getPos3().getY() + vc.getY3Speed());

		/*
		 * Ici on établie les pixels à distance distance de la position attendue
		 * Ensuite on parcours ces pixels par distance croissante a la position
		 * attendue Si on trouve une tache lumineuse on considere que c'est la
		 * nouvelle position et on sort de la boucle Si on ne trouve pas de
		 * tache lumineuse on appelle le GameEngine
		 */
		ArrayList<ArrayList<VirtualPixel>> pos1Lists = parcoursSpirale(
				expectedX1, expectedY1, distance);
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
				expectedX2, expectedY2, distance);
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

		/*
		 * ArrayList<ArrayList<VirtualPixel>> pos3Lists =
		 * parcoursSpirale(expectedX3, expectedY3, distance); boolean found3 =
		 * false; for(ArrayList<VirtualPixel> list : pos3Lists){
		 * for(VirtualPixel vp: list){ if (vp.isBrightness()){
		 * updatePosSpeed1(vc, vp.getPos().getX(), vp.getPos().getY()); found3 =
		 * true; break; } } if (found3) break; } if (!found3)
		 * gameEngine.setFrozen(null);
		 */
		/*
		 * if (vi[expectedX1][expectedY1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1, expectedY1); else if (vi[expectedX1 +
		 * 1][expectedY1].isBrightness() == true) updatePosSpeed1(vc, expectedX1
		 * + 1, expectedY1); else if (vi[expectedX1 + 1][expectedY1 +
		 * 1].isBrightness() == true) updatePosSpeed1(vc, expectedX1 + 1,
		 * expectedY1 + 1); else if (vi[expectedX1][expectedY1 +
		 * 1].isBrightness() == true) updatePosSpeed1(vc, expectedX1, expectedY1
		 * + 1); else if (vi[expectedX1 - 1][expectedY1 + 1].isBrightness() ==
		 * true) updatePosSpeed1(vc, expectedX1 - 1, expectedY1 + 1); else if
		 * (vi[expectedX1 - 1][expectedY1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 1, expectedY1); else if
		 * (vi[expectedX1 - 1][expectedY1 - 1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 1, expectedY1 - 1); else if
		 * (vi[expectedX1][expectedY1 - 1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1, expectedY1 - 1); else if
		 * (vi[expectedX1 + 1][expectedY1 - 1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 + 1, expectedY1 - 1); else if
		 * (vi[expectedX1 + 2][expectedY1 - 1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 + 2, expectedY1 - 1); else if
		 * (vi[expectedX1 + 2][expectedY1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 + 2, expectedY1); else if
		 * (vi[expectedX1 + 2][expectedY1 + 1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 + 2, expectedY1 + 1); else if
		 * (vi[expectedX1 + 2][expectedY1 + 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 + 2, expectedY1 + 2); else if
		 * (vi[expectedX1 + 1][expectedY1 + 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 + 1, expectedY1 + 2); else if
		 * (vi[expectedX1][expectedY1 + 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1, expectedY1 + 2); else if
		 * (vi[expectedX1 - 1][expectedY1 + 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 1, expectedY1 + 2); else if
		 * (vi[expectedX1 - 2][expectedY1 + 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 2, expectedY1 + 2); else if
		 * (vi[expectedX1 - 2][expectedY1 + 1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 2, expectedY1 + 1); else if
		 * (vi[expectedX1 - 2][expectedY1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 2, expectedY1); else if
		 * (vi[expectedX1 - 2][expectedY1 - 1].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 2, expectedY1 - 1); else if
		 * (vi[expectedX1 - 2][expectedY1 - 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 2, expectedY1 - 2); else if
		 * (vi[expectedX1 - 1][expectedY1 - 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 - 1, expectedY1 - 2); else if
		 * (vi[expectedX1][expectedY1 - 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1, expectedY1 - 2); else if
		 * (vi[expectedX1 + 1][expectedY1 - 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 + 1, expectedY1 - 2); else if
		 * (vi[expectedX1 + 2][expectedY1 - 2].isBrightness() == true)
		 * updatePosSpeed1(vc, expectedX1 + 2, expectedY1 - 2); else
		 * gameEngine.setFrozen(vc.getOwner());
		 * 
		 * if (vi[expectedX2][expectedY2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2, expectedY2); else if (vi[expectedX2 +
		 * 1][expectedY2].isBrightness() == true) updatePosSpeed2(vc, expectedX2
		 * + 1, expectedY2); else if (vi[expectedX2 + 1][expectedY2 +
		 * 1].isBrightness() == true) updatePosSpeed2(vc, expectedX2 + 1,
		 * expectedY2 + 1); else if (vi[expectedX2][expectedY2 +
		 * 1].isBrightness() == true) updatePosSpeed2(vc, expectedX2, expectedY2
		 * + 1); else if (vi[expectedX2 - 1][expectedY2 + 1].isBrightness() ==
		 * true) updatePosSpeed2(vc, expectedX2 - 1, expectedY2 + 1); else if
		 * (vi[expectedX2 - 1][expectedY2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 1, expectedY2); else if
		 * (vi[expectedX2 - 1][expectedY2 - 1].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 1, expectedY2 - 1); else if
		 * (vi[expectedX2][expectedY2 - 1].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2, expectedY2 - 1); else if
		 * (vi[expectedX2 + 1][expectedY2 - 1].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 + 1, expectedY2 - 1); else if
		 * (vi[expectedX2 + 2][expectedY2 - 1].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 + 2, expectedY2 - 1); else if
		 * (vi[expectedX2 + 2][expectedY2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 + 2, expectedY2); else if
		 * (vi[expectedX2 + 2][expectedY2 + 1].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 + 2, expectedY2 + 1); else if
		 * (vi[expectedX2 + 2][expectedY2 + 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 + 2, expectedY2 + 2); else if
		 * (vi[expectedX2 + 1][expectedY2 + 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 + 1, expectedY2 + 2); else if
		 * (vi[expectedX2][expectedY2 + 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2, expectedY2 + 2); else if
		 * (vi[expectedX2 - 1][expectedY2 + 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 1, expectedY2 + 2); else if
		 * (vi[expectedX2 - 2][expectedY2 + 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 2, expectedY2 + 2); else if
		 * (vi[expectedX2 - 2][expectedY2 + 1].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 2, expectedY2 + 1); else if
		 * (vi[expectedX2 - 2][expectedY2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 2, expectedY2); else if
		 * (vi[expectedX2 - 2][expectedY2 - 1].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 2, expectedY2 - 1); else if
		 * (vi[expectedX2 - 2][expectedY2 - 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 2, expectedY2 - 2); else if
		 * (vi[expectedX2 - 1][expectedY2 - 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 - 1, expectedY2 - 2); else if
		 * (vi[expectedX2][expectedY2 - 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2, expectedY2 - 2); else if
		 * (vi[expectedX2 + 1][expectedY2 - 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 + 1, expectedY2 - 2); else if
		 * (vi[expectedX2 + 2][expectedY2 - 2].isBrightness() == true)
		 * updatePosSpeed2(vc, expectedX2 + 2, expectedY2 - 2); else
		 * gameEngine.setFrozen(vc.getOwner());
		 * 
		 * if (vi[expectedX3][expectedY3].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3, expectedY3); else if (vi[expectedX3 +
		 * 1][expectedY3].isBrightness() == true) updatePosSpeed3(vc, expectedX3
		 * + 1, expectedY3); else if (vi[expectedX3 + 1][expectedY3 +
		 * 1].isBrightness() == true) updatePosSpeed3(vc, expectedX3 + 1,
		 * expectedY3 + 1); else if (vi[expectedX3][expectedY3 +
		 * 1].isBrightness() == true) updatePosSpeed3(vc, expectedX3, expectedY3
		 * + 1); else if (vi[expectedX3 - 1][expectedY3 + 1].isBrightness() ==
		 * true) updatePosSpeed3(vc, expectedX3 - 1, expectedY3 + 1); else if
		 * (vi[expectedX3 - 1][expectedY3].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 1, expectedY3); else if
		 * (vi[expectedX3 - 1][expectedY3 - 1].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 1, expectedY3 - 1); else if
		 * (vi[expectedX3][expectedY3 - 1].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3, expectedY3 - 1); else if
		 * (vi[expectedX3 + 1][expectedY3 - 1].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 + 1, expectedY3 - 1); else if
		 * (vi[expectedX3 + 2][expectedY3 - 1].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 + 2, expectedY3 - 1); else if
		 * (vi[expectedX3 + 2][expectedY3].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 + 2, expectedY3); else if
		 * (vi[expectedX3 + 2][expectedY3 + 1].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 + 2, expectedY3 + 1); else if
		 * (vi[expectedX3 + 2][expectedY3 + 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 + 2, expectedY3 + 2); else if
		 * (vi[expectedX3 + 1][expectedY3 + 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 + 1, expectedY3 + 2); else if
		 * (vi[expectedX3][expectedY3 + 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3, expectedY3 + 2); else if
		 * (vi[expectedX3 - 1][expectedY3 + 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 1, expectedY3 + 2); else if
		 * (vi[expectedX3 - 2][expectedY3 + 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 2, expectedY3 + 2); else if
		 * (vi[expectedX3 - 2][expectedY3 + 1].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 2, expectedY3 + 1); else if
		 * (vi[expectedX3 - 2][expectedY3].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 2, expectedY3); else if
		 * (vi[expectedX3 - 2][expectedY3 - 1].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 2, expectedY3 - 1); else if
		 * (vi[expectedX3 - 2][expectedY3 - 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 2, expectedY3 - 2); else if
		 * (vi[expectedX3 - 1][expectedY3 - 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 - 1, expectedY3 - 2); else if
		 * (vi[expectedX3][expectedY3 - 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3, expectedY3 - 2); else if
		 * (vi[expectedX3 + 1][expectedY3 - 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 + 1, expectedY3 - 2); else if
		 * (vi[expectedX3 + 2][expectedY3 - 2].isBrightness() == true)
		 * updatePosSpeed3(vc, expectedX3 + 2, expectedY3 - 2); else
		 * gameEngine.setFrozen(vc.getOwner());
		 */
	}

	/**
	 * Methode qui met a jour la position et la vitesse de la premiere diode
	 * d'un cube
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
	 * Methode qui met a jour la position et la vitesse de la seconde diode d'un
	 * cube
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

	/**
	 * Methode qui met a jour la position et la vitesse de la troisième diode
	 * d'un cube
	 * 
	 * @param vc
	 * @param newXPos
	 * @param newYPos
	 */
	/*
	 * private void updatePosSpeed3(VideoCube vc, double newXPos, double
	 * newYPos) { vc.setX3Speed((newXPos - vc.getPos3().getX()) / fps);
	 * vc.setY3Speed((newYPos - vc.getPos3().getX()) / fps);
	 * vc.getPos3().setX(newXPos); vc.getPos3().setY(newYPos); }
	 * 
	 * public ArrayList<ArrayList<VirtualPixel>> getGroupesConnexes() { return
	 * groupesConnexes; }
	 * 
	 * public void setGroupesConnexes( ArrayList<ArrayList<VirtualPixel>>
	 * groupesConnexes) { this.groupesConnexes = groupesConnexes; }
	 * 
	 * 
	 * /** Methode qui retourne une ArrayList d'ArrayList de VirtualPixe ou
	 * l'ArrayList numéro i contient les pixels distants de i de (x,y)
	 * 
	 * @param x
	 * 
	 * @param y
	 * 
	 * @param dist
	 */
	private ArrayList<ArrayList<VirtualPixel>> parcoursSpirale(int x, int y,
			int dist) {
		int upDist = Math.min(dist, y);
		int downDist = Math.min(dist, HEIGHT - y);
		int leftDist = Math.min(dist, x);
		int rightDist = Math.min(dist, LENGTH - x);
		ArrayList<ArrayList<VirtualPixel>> points = new ArrayList<ArrayList<VirtualPixel>>();
		points.ensureCapacity((int) Math.sqrt(2 * dist * dist));
		for (int i = (x - leftDist); i <= (x + rightDist); i++) {
			for (int j = (y - upDist); j <= (y + downDist); j++) {
				int d = (int) Math.sqrt(i * i + j * j);
				points.get(d).add(this.vi[i][j]);
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
	 * Methode pour trouver la mediane d'un tableau de nombres
	 * Algorithme trouve dans "algorithmique" de Cormen
	 * Implementation trouvee sur http://www.developpez.net
	 * @param buf
	 * @param bufLength
	 * @param n
	 * @return
	 */
	public static byte findNthLowestNumber(byte[] buf, int bufLength, int n) {
		// Modified algorithm according to http://www.geocities.com/zabrodskyvlada/3alg.html
		// Contributed by Heinz Klar
        int i,j;
        int l=0;
        int m=bufLength-1;
        byte med=buf[n];
        byte dum ;
 
        while (l<m) {
            i=l ;
            j=m ;
            do {
                while (buf[i]<med) i++ ;
                while (med<buf[j]) j-- ;
                dum=buf[j];
                buf[j]=buf[i];
                buf[i]=dum;
                i++ ; j-- ;
            } while ((j>=n) && (i<=n)) ;
            if (j<n) l=i ;
            if (n<i) m=j ;
            med=buf[n] ;
        }
    return med ;
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
