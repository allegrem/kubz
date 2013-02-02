package traitementVideo;

import java.util.*;
import utilities.*;

public class Connexe {
	
	/**
	 * Cette méthode permet de parcourir l'image pour en déterminer les composantes connexes (ici les taches lumineueses)
	 * La structure de données employée ici est une structure Union-Find basique (tourne en O(n*ln(n)) ou n nombre de pixels)
	 * @param screen
	 * @return
	 */
	
	public static ArrayList<ArrayList<VirtualPixel>> setConnexe(VirtualPixel[][] screen){
		
		VirtualPixel[][] vi = screen;
		int compteurComposantes = 0;
		ArrayList<ArrayList<VirtualPixel>> groupesConnexes = new ArrayList<ArrayList<VirtualPixel>>();
		ArrayList<ArrayList<VirtualPixel>> retour = new ArrayList<ArrayList<VirtualPixel>>();
		
		if (vi[0][0].isBrightness()==true){
			compteurComposantes++;
			groupesConnexes.add(compteurComposantes, new ArrayList<VirtualPixel>());
			groupesConnexes.get(compteurComposantes).add(vi[0][0]);
			vi[0][0].setGroupeConnexe(compteurComposantes);
		}
		for(int j=1; j<240;j++){
			if (vi[0][j].isBrightness()==true){
				if((vi[0][j-1].isBrightness()==true)){;
					groupesConnexes.get(vi[0][j-1].getGroupeConnexe()).add(vi[0][j]);
					vi[0][j].setGroupeConnexe(vi[0][j-1].getGroupeConnexe());
				}
				else{
					compteurComposantes++;
					groupesConnexes.add(compteurComposantes, new ArrayList<VirtualPixel>());
					groupesConnexes.get(compteurComposantes).add(vi[0][j]);
					vi[0][j].setGroupeConnexe(compteurComposantes);
				}
			}			
		}
		for(int i=1; i<240; i++){
			if (vi[i][0].isBrightness()==true){
				if((vi[i-1][0].isBrightness()==true)){
					vi[i][0].setGroupeConnexe(vi[i-1][0].getGroupeConnexe());
					groupesConnexes.get(vi[i][0].getGroupeConnexe()).add(vi[i][0]);
				}
				else{
					compteurComposantes++;
					groupesConnexes.add(compteurComposantes, new ArrayList<VirtualPixel>());
					groupesConnexes.get(compteurComposantes).add(vi[i][0]);
					vi[i][0].setGroupeConnexe(compteurComposantes);
				}
			}
			for(int j=1; j<240; j++){
				if (vi[i][j].isBrightness()==true){
					if ((vi[i][j-1].isBrightness()==true)&&(vi[i-1][j].isBrightness()==false)){
						vi[i][j].setGroupeConnexe(vi[i][j-1].getGroupeConnexe());
						groupesConnexes.get(vi[i][j].getGroupeConnexe()).add(vi[i][j]);
					}
					if ((vi[i-1][j].isBrightness()==true)&&(vi[i][j-1].isBrightness()==false)){
						vi[i][j].setGroupeConnexe(vi[i-1][j].getGroupeConnexe());
						groupesConnexes.get(vi[i][j].getGroupeConnexe()).add(vi[i][j]);
					}
					if ((vi[i-1][j].isBrightness()==true)&&(vi[i][j-1].isBrightness()==true)){
						vi[i][j].setGroupeConnexe(vi[i-1][j].getGroupeConnexe());
						groupesConnexes.get(vi[i][j].getGroupeConnexe()).add(vi[i][j]);
						unionGroupesConnexes(groupesConnexes,vi[i-1][j].getGroupeConnexe(),vi[i][j-1].getGroupeConnexe()); 
					}	
					else{
						compteurComposantes++; 
						groupesConnexes.add(compteurComposantes, new ArrayList<VirtualPixel>());
						groupesConnexes.get(compteurComposantes).add(vi[i][j]);
						vi[i][j].setGroupeConnexe(compteurComposantes);
					}										
				}
			}
		}
		
		/**
		 *  Cette sous méthode permet de mettre à jour la straucture (les groupes vides sont effaces)
		 */
		int taille = groupesConnexes.size();
		int compteurRetour = 0; 
		for(int groupe = 1; groupe < taille; groupe++){
			if (groupesConnexes.get(groupe)!=null){
				for(VirtualPixel vp: groupesConnexes.get(groupe)){
					compteurRetour++;
					vp.setGroupeConnexe(compteurRetour);
					retour.add(new ArrayList<VirtualPixel>());
					retour.get(compteurRetour).add(vp);
				}
			}
		}	
		
		return retour;
		
	}
	
	/**
	 * Renvoie la position de tous les groupes connexes
	 * @param groupesConnexes
	 * @return
	 */
	public static ArrayList<Point> getGroupesPos(ArrayList<ArrayList<VirtualPixel>> groupesConnexes){
		ArrayList<Point> groupesConnexesPos = new ArrayList<Point>();
		for(ArrayList<VirtualPixel> array : groupesConnexes){
			double xMoy = 0;
			double yMoy = 0;
			for(VirtualPixel vp : array){
				xMoy = xMoy + vp.getPos().getX();
				yMoy = yMoy + vp.getPos().getY(); 
			}
			groupesConnexesPos.add(new Point((int)(xMoy/array.size()),(int)(yMoy/array.size())));
		}
		return groupesConnexesPos;
	}
	
	/**
	 * Methode qui permet de mettre à jour la structure Union-Find mise en place (si j'ai du courage j'implémenterai celle de Tarjan ou de Hopcroft-Ullman mais je promets rien)
	 * @param groupesConnexes
	 * @param groupe1
	 * @param groupe2
	 */
	private static void unionGroupesConnexes(ArrayList<ArrayList<VirtualPixel>> groupesConnexes,int groupe1 ,int groupe2){
		if (groupe1<groupe2){
			for(VirtualPixel vp: groupesConnexes.get(groupe1)){
				groupesConnexes.get(groupe2).add(vp);
			}
			groupesConnexes.get(groupe1).clear();		
		}
		else{
			for(VirtualPixel vp: groupesConnexes.get(groupe2)){
				groupesConnexes.get(groupe1).add(vp);
			}
			groupesConnexes.get(groupe2).clear();
		}
		
	}
}


