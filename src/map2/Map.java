package map2;

import java.util.ArrayList;

import views.Displayable;


/**
 * Contient toutes les données concernant la map de jeu: map en elle même et
 * unités à afficher
 * 
 * @author paul
 * 
 */
public class Map {
	private ArrayList<Displayable> listObjects;
	public static int width;
	public static int length;
	
	public Map(int width,int length) {
		listObjects=new ArrayList<Displayable>();
		this.width = width;
		this.length = length;
	}


	public void paint() {
		for(Displayable object: listObjects){
			object.paint();
			
		}

	}
	
	/*
	 * Classe les objets affichables selon leur index 
	 * (profondeur d'affichage);
	 */
	public void Sort(){
		
		
	}

	public Displayable add(Displayable object){
		listObjects.add(object);
		return object;
	}
	
	public Displayable remove(Displayable object){
		if (listObjects.size()>1){
		listObjects.remove(object);
		return object;
		}else{
			return null;
		}
	}

	public int removeLast(){
		
		if (listObjects.size()>1){
			listObjects.remove(listObjects.size()-1);
			return listObjects.size();
			}else{
				return -1;
			}
	}
	
	public ArrayList<Displayable> getObjects(){
		
		return listObjects;
	}

}

