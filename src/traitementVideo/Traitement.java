package traitementVideo;

import gameEngine.GameEngine;

import java.util.*;
import utilities.*;

public class Traitement {
	
	/**
	 * Cette méthode permet de parcourir l'image pour en déterminer les composantes connexes (ici les taches lumineueses)
	 * La structure de données employée ici est une structure Union-Find basique (tourne en O(n*ln(n)) ou n nombre de pixels)
	 * @param screen
	 * @return
	 */
	private int fps;
	private VirtualPixel[][] vi;
	private ArrayList<ArrayList<VirtualPixel>> groupesConnexes;
	private GameEngine gameEngine;
	
	public void updateConnexe(VirtualPixel[][] screen, int LENGHT, int HEIGHT){
		
		vi = screen;
		int compteurComposantes = 0;
		ArrayList<ArrayList<VirtualPixel>> groupesConnexeIntermediare = new ArrayList<ArrayList<VirtualPixel>>();
		ArrayList<ArrayList<VirtualPixel>> retour = new ArrayList<ArrayList<VirtualPixel>>();
		groupesConnexeIntermediare.add(new ArrayList<VirtualPixel>());
		retour.add(new ArrayList<VirtualPixel>());
		
		if (vi[0][0].isBrightness()){
			compteurComposantes++;
			groupesConnexeIntermediare.add(compteurComposantes, new ArrayList<VirtualPixel>());
			groupesConnexeIntermediare.get(compteurComposantes).add(vi[0][0]);
			vi[0][0].setGroupeConnexe(compteurComposantes);
		}
		for(int j=1; j<HEIGHT;j++){
			if (vi[0][j].isBrightness()){
				if((vi[0][j-1].isBrightness())){;
					groupesConnexeIntermediare.get(vi[0][j-1].getGroupeConnexe()).add(vi[0][j]);
					vi[0][j].setGroupeConnexe(vi[0][j-1].getGroupeConnexe());
				}
				else{
					compteurComposantes++;
					groupesConnexeIntermediare.add(new ArrayList<VirtualPixel>());
					groupesConnexeIntermediare.get(compteurComposantes).add(vi[0][j]);
					vi[0][j].setGroupeConnexe(compteurComposantes);
				}
			}			
		}
		for(int i=1; i<LENGHT; i++){
			if (vi[i][0].isBrightness()){
				if((vi[i-1][0].isBrightness()==true)){
					vi[i][0].setGroupeConnexe(vi[i-1][0].getGroupeConnexe());
					groupesConnexeIntermediare.get(vi[i][0].getGroupeConnexe()).add(vi[i][0]);
				}
				else{
					compteurComposantes++;
					groupesConnexeIntermediare.add(compteurComposantes, new ArrayList<VirtualPixel>());
					groupesConnexeIntermediare.get(compteurComposantes).add(vi[i][0]);
					vi[i][0].setGroupeConnexe(compteurComposantes);
				}
			}
			for(int j=1; j<HEIGHT; j++){
				if (vi[i][j].isBrightness()){
					if ((vi[i][j-1].isBrightness())&&(!vi[i-1][j].isBrightness())){
						vi[i][j].setGroupeConnexe(vi[i][j-1].getGroupeConnexe());
						groupesConnexeIntermediare.get(vi[i][j].getGroupeConnexe()).add(vi[i][j]);
					}
					if ((vi[i-1][j].isBrightness())&&(!vi[i][j-1].isBrightness())){
						vi[i][j].setGroupeConnexe(vi[i-1][j].getGroupeConnexe());
						groupesConnexeIntermediare.get(vi[i][j].getGroupeConnexe()).add(vi[i][j]);
					}
					if ((vi[i-1][j].isBrightness())&&(!vi[i][j-1].isBrightness())){
						vi[i][j].setGroupeConnexe(vi[i-1][j].getGroupeConnexe());
						groupesConnexeIntermediare.get(vi[i][j].getGroupeConnexe()).add(vi[i][j]);
						uniongroupesConnexeIntermediare(groupesConnexeIntermediare,vi[i-1][j].getGroupeConnexe(),vi[i][j-1].getGroupeConnexe()); 
					}	
					else{
						compteurComposantes++; 
						groupesConnexeIntermediare.add(compteurComposantes, new ArrayList<VirtualPixel>());
						groupesConnexeIntermediare.get(compteurComposantes).add(vi[i][j]);
						vi[i][j].setGroupeConnexe(compteurComposantes);
					}										
				}
			}
		}
		
		/**
		 *  Cette sous méthode permet de mettre à jour la structure (les groupes vides sont effaces)
		 */
		int taille = groupesConnexeIntermediare.size();
		int compteurRetour = 0; 
		for(int groupe = 1; groupe < taille; groupe++){
			if (groupesConnexeIntermediare.get(groupe)!=null){
				for(VirtualPixel vp: groupesConnexeIntermediare.get(groupe)){
					compteurRetour++;
					vp.setGroupeConnexe(compteurRetour);
					retour.add(new ArrayList<VirtualPixel>());
					retour.get(compteurRetour).add(vp);
				}
			}
		}	
		
		groupesConnexes = retour;
		
	}
	
	/**
	 * Renvoie la position de tous les groupes connexes
	 * @param groupesConnexeIntermediare
	 * @return
	 */
	public ArrayList<Point> getGroupesPos(){
		ArrayList<Point> groupesConnexeIntermediarePos = new ArrayList<Point>();
		for(ArrayList<VirtualPixel> array : groupesConnexes){
			double xMoy = 0;
			double yMoy = 0;
			for(VirtualPixel vp : array){
				xMoy = xMoy + vp.getPos().getX();
				yMoy = yMoy + vp.getPos().getY(); 
			}
			groupesConnexeIntermediarePos.add(new Point((int)(xMoy/array.size()),(int)(yMoy/array.size())));
		}
		return groupesConnexeIntermediarePos;
	}
	
	/**
	 * Methode qui permet de mettre à jour la structure Union-Find mise en place (si j'ai du courage j'implémenterai celle de Tarjan ou de Hopcroft-Ullman mais je promets rien)
	 * @param groupesConnexeIntermediare
	 * @param groupe1
	 * @param groupe2
	 */
	private static void uniongroupesConnexeIntermediare(ArrayList<ArrayList<VirtualPixel>> groupesConnexeIntermediare,int groupe1 ,int groupe2){
		if (groupe1<groupe2){
			for(VirtualPixel vp: groupesConnexeIntermediare.get(groupe1)){
				groupesConnexeIntermediare.get(groupe2).add(vp);
			}
			groupesConnexeIntermediare.get(groupe1).clear();		
		}
		else{
			for(VirtualPixel vp: groupesConnexeIntermediare.get(groupe2)){
				groupesConnexeIntermediare.get(groupe1).add(vp);
			}
			groupesConnexeIntermediare.get(groupe2).clear();
		}	
	}
	/**
	 * Methode qui renvoie la position moyenne d'une composante connexe
	 * @param groupe
	 * @return
	 */
	public Point getGroupePos(int groupe){
		double xMoy = 0;
		double yMoy = 0;
		for(VirtualPixel vp: groupesConnexes.get(groupe)){
			xMoy = xMoy + vp.getPos().getX();
			yMoy = yMoy + vp.getPos().getY();
		}
		xMoy = xMoy/groupesConnexes.get(groupe).size();
		yMoy = yMoy/groupesConnexes.get(groupe).size();
		return new Point(xMoy,yMoy);
	}
	
	/**
	 * Methode qui permet d'actualiser la position et la vitesse des cubes
	 * Elle utilise une recherche locale en partant de la position attendue du cube
	 * Methode un peu dégueu mais je vois pas trop comment la faire avec un joli boucle
	 * @param vc
	 */
	public void localSearch(VirtualCube vc){
		int expectedX1 = (int) (vc.getPos1().getX() + vc.getX1Speed());
		int expectedX2 = (int) (vc.getPos2().getX() + vc.getX2Speed());
		int expectedX3 = (int) (vc.getPos3().getX() + vc.getX3Speed());
		int expectedY1 = (int) (vc.getPos1().getY() + vc.getY1Speed());
		int expectedY2 = (int) (vc.getPos2().getY() + vc.getY2Speed());
		int expectedY3 = (int) (vc.getPos3().getY() + vc.getY3Speed());
		/*
		 * Bon là c'est hyper HYPER dégueu mais j'ai pas trouvé comment faire joli :/
		 * (enfin si mais ça prend trois plombes...(la seule boucle que j'ai trouvé fait 50 lignes ...))
		 */
		if (vi[expectedX1][expectedY1].isBrightness()==true) updatePosSpeed1(vc,expectedX1,expectedY1);
		else if (vi[expectedX1+1][expectedY1].isBrightness()==true) updatePosSpeed1(vc,expectedX1+1,expectedY1);
		else if (vi[expectedX1+1][expectedY1+1].isBrightness()==true) updatePosSpeed1(vc,expectedX1+1,expectedY1+1);
		else if (vi[expectedX1][expectedY1+1].isBrightness()==true) updatePosSpeed1(vc,expectedX1,expectedY1+1);
		else if (vi[expectedX1-1][expectedY1+1].isBrightness()==true) updatePosSpeed1(vc,expectedX1-1,expectedY1+1);
		else if (vi[expectedX1-1][expectedY1].isBrightness()==true) updatePosSpeed1(vc,expectedX1-1,expectedY1);
		else if (vi[expectedX1-1][expectedY1-1].isBrightness()==true) updatePosSpeed1(vc,expectedX1-1,expectedY1-1);
		else if (vi[expectedX1][expectedY1-1].isBrightness()==true) updatePosSpeed1(vc,expectedX1,expectedY1-1);
		else if (vi[expectedX1+1][expectedY1-1].isBrightness()==true) updatePosSpeed1(vc,expectedX1+1,expectedY1-1);
		else if (vi[expectedX1+2][expectedY1-1].isBrightness()==true) updatePosSpeed1(vc,expectedX1+2,expectedY1-1);
		else if (vi[expectedX1+2][expectedY1].isBrightness()==true) updatePosSpeed1(vc,expectedX1+2,expectedY1);
		else if (vi[expectedX1+2][expectedY1+1].isBrightness()==true) updatePosSpeed1(vc,expectedX1+2,expectedY1+1);
		else if (vi[expectedX1+2][expectedY1+2].isBrightness()==true) updatePosSpeed1(vc,expectedX1+2,expectedY1+2);
		else if (vi[expectedX1+1][expectedY1+2].isBrightness()==true) updatePosSpeed1(vc,expectedX1+1,expectedY1+2);
		else if (vi[expectedX1][expectedY1+2].isBrightness()==true) updatePosSpeed1(vc,expectedX1,expectedY1+2);
		else if (vi[expectedX1-1][expectedY1+2].isBrightness()==true) updatePosSpeed1(vc,expectedX1-1,expectedY1+2);
		else if (vi[expectedX1-2][expectedY1+2].isBrightness()==true) updatePosSpeed1(vc,expectedX1-2,expectedY1+2);
		else if (vi[expectedX1-2][expectedY1+1].isBrightness()==true) updatePosSpeed1(vc,expectedX1-2,expectedY1+1);
		else if (vi[expectedX1-2][expectedY1].isBrightness()==true) updatePosSpeed1(vc,expectedX1-2,expectedY1);
		else if (vi[expectedX1-2][expectedY1-1].isBrightness()==true) updatePosSpeed1(vc,expectedX1-2,expectedY1-1);
		else if (vi[expectedX1-2][expectedY1-2].isBrightness()==true) updatePosSpeed1(vc,expectedX1-2,expectedY1-2);
		else if (vi[expectedX1-1][expectedY1-2].isBrightness()==true) updatePosSpeed1(vc,expectedX1-1,expectedY1-2);
		else if (vi[expectedX1][expectedY1-2].isBrightness()==true) updatePosSpeed1(vc,expectedX1,expectedY1-2);
		else if (vi[expectedX1+1][expectedY1-2].isBrightness()==true) updatePosSpeed1(vc,expectedX1+1,expectedY1-2);
		else if (vi[expectedX1+2][expectedY1-2].isBrightness()==true) updatePosSpeed1(vc,expectedX1+2,expectedY1-2);
		else gameEngine.setFrozen(vc.getOwner());
		
		if (vi[expectedX2][expectedY2].isBrightness()==true) updatePosSpeed2(vc,expectedX2,expectedY2);
		else if (vi[expectedX2+1][expectedY2].isBrightness()==true) updatePosSpeed2(vc,expectedX2+1,expectedY2);
		else if (vi[expectedX2+1][expectedY2+1].isBrightness()==true) updatePosSpeed2(vc,expectedX2+1,expectedY2+1);
		else if (vi[expectedX2][expectedY2+1].isBrightness()==true) updatePosSpeed2(vc,expectedX2,expectedY2+1);
		else if (vi[expectedX2-1][expectedY2+1].isBrightness()==true) updatePosSpeed2(vc,expectedX2-1,expectedY2+1);
		else if (vi[expectedX2-1][expectedY2].isBrightness()==true) updatePosSpeed2(vc,expectedX2-1,expectedY2);
		else if (vi[expectedX2-1][expectedY2-1].isBrightness()==true) updatePosSpeed2(vc,expectedX2-1,expectedY2-1);
		else if (vi[expectedX2][expectedY2-1].isBrightness()==true) updatePosSpeed2(vc,expectedX2,expectedY2-1);
		else if (vi[expectedX2+1][expectedY2-1].isBrightness()==true) updatePosSpeed2(vc,expectedX2+1,expectedY2-1);
		else if (vi[expectedX2+2][expectedY2-1].isBrightness()==true) updatePosSpeed2(vc,expectedX2+2,expectedY2-1);
		else if (vi[expectedX2+2][expectedY2].isBrightness()==true) updatePosSpeed2(vc,expectedX2+2,expectedY2);
		else if (vi[expectedX2+2][expectedY2+1].isBrightness()==true) updatePosSpeed2(vc,expectedX2+2,expectedY2+1);
		else if (vi[expectedX2+2][expectedY2+2].isBrightness()==true) updatePosSpeed2(vc,expectedX2+2,expectedY2+2);
		else if (vi[expectedX2+1][expectedY2+2].isBrightness()==true) updatePosSpeed2(vc,expectedX2+1,expectedY2+2);
		else if (vi[expectedX2][expectedY2+2].isBrightness()==true) updatePosSpeed2(vc,expectedX2,expectedY2+2);
		else if (vi[expectedX2-1][expectedY2+2].isBrightness()==true) updatePosSpeed2(vc,expectedX2-1,expectedY2+2);
		else if (vi[expectedX2-2][expectedY2+2].isBrightness()==true) updatePosSpeed2(vc,expectedX2-2,expectedY2+2);
		else if (vi[expectedX2-2][expectedY2+1].isBrightness()==true) updatePosSpeed2(vc,expectedX2-2,expectedY2+1);
		else if (vi[expectedX2-2][expectedY2].isBrightness()==true) updatePosSpeed2(vc,expectedX2-2,expectedY2);
		else if (vi[expectedX2-2][expectedY2-1].isBrightness()==true) updatePosSpeed2(vc,expectedX2-2,expectedY2-1);
		else if (vi[expectedX2-2][expectedY2-2].isBrightness()==true) updatePosSpeed2(vc,expectedX2-2,expectedY2-2);
		else if (vi[expectedX2-1][expectedY2-2].isBrightness()==true) updatePosSpeed2(vc,expectedX2-1,expectedY2-2);
		else if (vi[expectedX2][expectedY2-2].isBrightness()==true) updatePosSpeed2(vc,expectedX2,expectedY2-2);
		else if (vi[expectedX2+1][expectedY2-2].isBrightness()==true) updatePosSpeed2(vc,expectedX2+1,expectedY2-2);
		else if (vi[expectedX2+2][expectedY2-2].isBrightness()==true) updatePosSpeed2(vc,expectedX2+2,expectedY2-2);
		else gameEngine.setFrozen(vc.getOwner());
		
		if (vi[expectedX3][expectedY3].isBrightness()==true) updatePosSpeed3(vc,expectedX3,expectedY3);
		else if (vi[expectedX3+1][expectedY3].isBrightness()==true) updatePosSpeed3(vc,expectedX3+1,expectedY3);
		else if (vi[expectedX3+1][expectedY3+1].isBrightness()==true) updatePosSpeed3(vc,expectedX3+1,expectedY3+1);
		else if (vi[expectedX3][expectedY3+1].isBrightness()==true) updatePosSpeed3(vc,expectedX3,expectedY3+1);
		else if (vi[expectedX3-1][expectedY3+1].isBrightness()==true) updatePosSpeed3(vc,expectedX3-1,expectedY3+1);
		else if (vi[expectedX3-1][expectedY3].isBrightness()==true) updatePosSpeed3(vc,expectedX3-1,expectedY3);
		else if (vi[expectedX3-1][expectedY3-1].isBrightness()==true) updatePosSpeed3(vc,expectedX3-1,expectedY3-1);
		else if (vi[expectedX3][expectedY3-1].isBrightness()==true) updatePosSpeed3(vc,expectedX3,expectedY3-1);
		else if (vi[expectedX3+1][expectedY3-1].isBrightness()==true) updatePosSpeed3(vc,expectedX3+1,expectedY3-1);
		else if (vi[expectedX3+2][expectedY3-1].isBrightness()==true) updatePosSpeed3(vc,expectedX3+2,expectedY3-1);
		else if (vi[expectedX3+2][expectedY3].isBrightness()==true) updatePosSpeed3(vc,expectedX3+2,expectedY3);
		else if (vi[expectedX3+2][expectedY3+1].isBrightness()==true) updatePosSpeed3(vc,expectedX3+2,expectedY3+1);
		else if (vi[expectedX3+2][expectedY3+2].isBrightness()==true) updatePosSpeed3(vc,expectedX3+2,expectedY3+2);
		else if (vi[expectedX3+1][expectedY3+2].isBrightness()==true) updatePosSpeed3(vc,expectedX3+1,expectedY3+2);
		else if (vi[expectedX3][expectedY3+2].isBrightness()==true) updatePosSpeed3(vc,expectedX3,expectedY3+2);
		else if (vi[expectedX3-1][expectedY3+2].isBrightness()==true) updatePosSpeed3(vc,expectedX3-1,expectedY3+2);
		else if (vi[expectedX3-2][expectedY3+2].isBrightness()==true) updatePosSpeed3(vc,expectedX3-2,expectedY3+2);
		else if (vi[expectedX3-2][expectedY3+1].isBrightness()==true) updatePosSpeed3(vc,expectedX3-2,expectedY3+1);
		else if (vi[expectedX3-2][expectedY3].isBrightness()==true) updatePosSpeed3(vc,expectedX3-2,expectedY3);
		else if (vi[expectedX3-2][expectedY3-1].isBrightness()==true) updatePosSpeed3(vc,expectedX3-2,expectedY3-1);
		else if (vi[expectedX3-2][expectedY3-2].isBrightness()==true) updatePosSpeed3(vc,expectedX3-2,expectedY3-2);
		else if (vi[expectedX3-1][expectedY3-2].isBrightness()==true) updatePosSpeed3(vc,expectedX3-1,expectedY3-2);
		else if (vi[expectedX3][expectedY3-2].isBrightness()==true) updatePosSpeed3(vc,expectedX3,expectedY3-2);
		else if (vi[expectedX3+1][expectedY3-2].isBrightness()==true) updatePosSpeed3(vc,expectedX3+1,expectedY3-2);
		else if (vi[expectedX3+2][expectedY3-2].isBrightness()==true) updatePosSpeed3(vc,expectedX3+2,expectedY3-2);
		else gameEngine.setFrozen(vc.getOwner());
	}
	
	
	/**
	 * Methode qui met a jour la position et la vitesse de la premiere diode d'un cube
	 * @param vc
	 * @param newXPos
	 * @param newYPos
	 */
	private void updatePosSpeed1(VirtualCube vc, double newXPos, double newYPos){
		vc.setX1Speed((newXPos-vc.getPos1().getX())/fps);
		vc.setY1Speed((newYPos-vc.getPos1().getX())/fps);
		vc.getPos1().setX(newXPos);
		vc.getPos1().setY(newYPos);
	}
	/**
	 * Methode qui met a jour la position et la vitesse de la seconde diode d'un cube
	 * @param vc
	 * @param newXPos
	 * @param newYPos
	 */
	private void updatePosSpeed2(VirtualCube vc, double newXPos, double newYPos){
		vc.setX2Speed((newXPos-vc.getPos2().getX())/fps);
		vc.setY2Speed((newYPos-vc.getPos2().getX())/fps);
		vc.getPos2().setX(newXPos);
		vc.getPos2().setY(newYPos);
	}
	/**
	 * Methode qui met a jour la position et la vitesse de la troisième diode d'un cube
	 * @param vc
	 * @param newXPos
	 * @param newYPos
	 */
	private void updatePosSpeed3(VirtualCube vc, double newXPos, double newYPos){
		vc.setX3Speed((newXPos-vc.getPos3().getX())/fps);
		vc.setY3Speed((newYPos-vc.getPos3().getX())/fps);
		vc.getPos3().setX(newXPos);
		vc.getPos3().setY(newYPos);
	}

	public ArrayList<ArrayList<VirtualPixel>> getGroupesConnexes() {
		return groupesConnexes;
	}

	public void setGroupesConnexes(
			ArrayList<ArrayList<VirtualPixel>> groupesConnexes) {
		this.groupesConnexes = groupesConnexes;
	}
	
	
	
	
}


