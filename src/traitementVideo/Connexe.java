package traitementVideo;

import java.util.*;
import utilities.*;

public class Connexe {
	
	
	//ce code pourrait être plus modulaire, mais il n'y en a pas vraiment besoin ...
	
	// A optimiser aussi au nivau du choix de la liste à transférer
	// A optimiser au niveau des variables souvent appelées 
	
	public static ArrayList<ArrayList<VirtualPixel>> setConnexe(VirtualScreen vs){
		
		VirtualPixel[][] vi = vs.getVirtualImage();
		int compteurComposantes = 0;
		ArrayList<ArrayList<VirtualPixel>> groupesConnexes = new ArrayList<ArrayList<VirtualPixel>>();
		ArrayList<ArrayList<VirtualPixel>> retour = new ArrayList<ArrayList<VirtualPixel>>();
		//ArrayList<ArrayList<Integer>> tableEq = new ArrayList<ArrayList<Integer>>();
		
		if (vi[0][0].isBrightness()==true){
			compteurComposantes++;
			groupesConnexes.add(compteurComposantes, new ArrayList<VirtualPixel>());
			//tableEq.add(compteurComposantes, new ArrayList<Integer>());
			groupesConnexes.get(compteurComposantes).add(vi[0][0]);
			//tableEq.get(compteurComposantes).add(new Integer(compteurComposantes));
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
					//tableEq.add(compteurComposantes, new ArrayList<Integer>());
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
					//tableEq.add(compteurComposantes, new ArrayList<Integer>());
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
						//on commence par agir sur le pixel courrant
						vi[i][j].setGroupeConnexe(vi[i-1][j].getGroupeConnexe());
						groupesConnexes.get(vi[i][j].getGroupeConnexe()).add(vi[i][j]);
						//on met à jour la liste d'quivalence, car le pixel du haut et de gauche font en fait parti d'une même classe d'équivalence
						int gr = vi[i-1][j].getGroupeConnexe(); //groupe vers leuel changer
						ArrayList<VirtualPixel> nouveau = groupesConnexes.get(vi[i-1][j].getGroupeConnexe());
						/* La partie suivante permet de transférer tous les pixels de la composante de gauche vers 
						 * celle du haut
						 */					
						for(VirtualPixel vp: groupesConnexes.get(vi[i][j-1].getGroupeConnexe())){
							nouveau.add(vp);
							vp.setGroupeConnexe(gr);
						}
						groupesConnexes.get(vi[i][j-1].getGroupeConnexe()).clear(); //on vide la liste
					}	
					else{
						compteurComposantes++; //on passe à une nouvelle composante
						groupesConnexes.add(compteurComposantes, new ArrayList<VirtualPixel>());//on crée la liste des pixels associés à cette composante
						//tableEq.add(compteurComposantes, new ArrayList<Integer>());//on ajoute une classe d'équivalenc dans la table
						groupesConnexes.get(compteurComposantes).add(vi[i][j]);//on ajoute ce pixel à la liste des pixels associés à cette composante
						vi[i][j].setGroupeConnexe(compteurComposantes);// on donne au pixel le groupe  qui convient
					}										
				}
			}
		}
		
		// on remet les groupes dans l'ordre dans une ArrayList pour le retour
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
		//on gère à présent les équivalences
		//for(int i=tableEq.size();i<0;i--)	
		
		return retour;
		
	}
	
	
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
}


