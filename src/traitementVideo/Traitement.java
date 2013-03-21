package traitementVideo;

import gameEngine.GameEngine;

import java.util.*;

import utilities.*;

public class Traitement {

	/**
	 * Cette m�thode permet de parcourir l'image pour en d�terminer les
	 * composantes connexes (ici les taches lumineueses) La structure de donn�es
	 * employ�e ici est une structure Union-Find basique (tourne en O(n*ln(n))
	 * ou n nombre de pixels)
	 * 
	 * @param screen
	 * @return
	 */
	private int fps;
	private VirtualPixel[][] vi;
	private ArrayList<ArrayList<VirtualPixel>> groupesConnexes;
	private GameEngine gameEngine;
	private int LENGTH = 640;
	private int HEIGHT = 480;
	private int distance = 5;
	private int seuil = 5;
	private VirtualPixel[][] traitScreen;

	public Traitement(int LENGTH, int HEIGHT) {
		super();
		this.LENGTH = LENGTH;
		this.HEIGHT = HEIGHT;
		this.traitScreen = new VirtualPixel[HEIGHT][LENGTH];
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < LENGTH; j++) {
				traitScreen[i][j] = new VirtualPixel(false, 0, new Point(i, j),(byte) 0);
			}
		}
	}

	/**
	 * Constructeur utilise pour les tests unitaires
	 */
	public Traitement() {
		super();
	}

	public void flouGaussien(int LENGTH, int HEIGHT){
		this.LENGTH = LENGTH;
		this.HEIGHT = HEIGHT;
		flouGaussien();
	}
	
	public void flouGaussien() {
		byte[][] gauss = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };
		byte[][] newCoefs = new byte[HEIGHT][LENGTH];
		// ici on calcule les nouvelles intensites apres le passage du filtre
		// gaussien
		for (int i = 2; i < (HEIGHT - 3); i++) {
			for (int j = 2; j < (LENGTH - 2); j++) {
				byte coef = (byte) ((
						gauss[0][0]	* traitScreen[i - 1][j - 1].getIntensite()
						+ gauss[0][1] * traitScreen[i - 1][j].getIntensite()
						+ gauss[0][2] * traitScreen[i - 1][j + 1].getIntensite()
						+ gauss[1][0] * traitScreen[i][j - 1].getIntensite()
						+ gauss[1][1] * traitScreen[i][j].getIntensite()
						+ gauss[1][2] * traitScreen[i][j + 1].getIntensite()
						+ gauss[2][0] * traitScreen[i + 1][j - 1].getIntensite()
						+ gauss[2][1] * traitScreen[i + 1][j].getIntensite() 
						+ gauss[2][2] * traitScreen[i + 1][j + 1].getIntensite()) 
						/ 16);
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
		//int iter = 0;
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < LENGTH; j++) {
				if (traitScreen[i][j].getIntensite() >= seuil){
					traitScreen[i][j].setBrightness(true);
					//iter++;
				}
			}
		}
		//System.out.println(iter);
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
		
		//on remet les composantes a zero
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
			if (list.size() > 3) {
				vase.add(list);
			}
		}
		groupesConnexes = vase;
	}

	/**
	 * Renvoie la position de tous les groupes connexes
	 * 
	 * @param groupesConnexeIntermediare
	 * @return
	 */
	public ArrayList<Point> getGroupesPos() {
		ArrayList<Point> groupesConnexeIntermediarePos = new ArrayList<Point>();
		for (ArrayList<VirtualPixel> array : groupesConnexes) {
			double xMoy = 0;
			double yMoy = 0;
			for (VirtualPixel vp : array) {
				xMoy = xMoy + vp.getPos().getX();
				yMoy = yMoy + vp.getPos().getY();
			}
			groupesConnexeIntermediarePos.add(new Point((int) (xMoy / array
					.size()), (int) (yMoy / array.size())));
		}
		return groupesConnexeIntermediarePos;
	}

	/**
	 * Methode qui permet de mettre � jour la structure Union-Find mise en place
	 * actuellemnt elle ajoute la plus petite ArrayList � la plus grande
	 * (complexit� en nln(n) o� n nombre de pixels � true) (si j'ai du courage
	 * j'impl�menterai celle de Tarjan ou de Hopcroft-Ullman mais je promets
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
	 * Methode un peu d�gueu mais je vois pas trop comment la faire avec un joli
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
		 * Ici on �tablie les pixels � distance distance de la position attendue
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
	 * Methode qui met a jour la position et la vitesse de la troisi�me diode
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
	 * l'ArrayList num�ro i contient les pixels distants de i de (x,y)
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

}
