package map2;

import java.util.ArrayList;

import views.interfaces.Displayable;
import views.interfaces.DisplayableFather;


/**
 * Contient toutes les donnees concernant la map de jeu: map en elle meme et
 * unites a afficher
 * 
 * @author paul
 * 
 */
public class Map {


	private ArrayList<DisplayableFather> listObjects;
	public static int width;
	public static int length;
	private static Map map=null;
	
	public Map() {
		listObjects=new ArrayList<DisplayableFather>();
		map=this;
	}

/**
 * Genere le rendu de la map en affichant chaque objet 
 */
	public synchronized void paint() {
		for(DisplayableFather object: listObjects){
			object.paint();
			
		}

	}
	
/**
 * Ajout d'un objet pere a la map
 * @param object L'objet a ajouter
 * @return L'objet ajoute
 */
	public synchronized DisplayableFather add(DisplayableFather object){
		listObjects.add(object);
		return object;
	}
	
	/**
	 * Retrait d'un objet de la map
	 * 
	 * @param object Objet a retirer
	 * @return L'objet retire si cela a marche
	 */
	public synchronized DisplayableFather remove(DisplayableFather object){
		if (listObjects.size()>1){
		listObjects.remove(object);
		return object;
		}else{
			return null;
		}
	}

	/**
	 * Retirer le dernier objet ajoute
	 * 
	 */
	public synchronized int removeLast(){
		
		if (listObjects.size()>1){
			listObjects.remove(listObjects.size()-1);
			return listObjects.size();
			}else{
				return -1;
			}
	}
	
	/**
	 * 
	 * @return La liste des objets ajoutes a la map
	 */
	public synchronized ArrayList<DisplayableFather> getObjects(){
		return listObjects;
	}
	
	/**
	 * Retourne l'objet d'indice i
	 * @param i
	 * @return
	 */
	public synchronized DisplayableFather getObject(int i){
		return listObjects.get(i);
	}
	
	/**
	 * 
	 * @return La map
	 */
	public static Map getMap(){
		return map;
	}

	public static void setWidth(int width) {
		Map.width = width;
	}

	public static void setLength(int length) {
		Map.length = length;
	}
	
}

