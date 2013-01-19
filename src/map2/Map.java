package map2;

import java.util.ArrayList;

import views.Displayable;
import views.DisplayableFather;


/**
 * Contient toutes les données concernant la map de jeu: map en elle même et
 * unités à afficher
 * 
 * @author paul
 * 
 */
public class Map {
	private ArrayList<DisplayableFather> listObjects;
	public static int width;
	public static int length;
	private static Map map=null;
	
	public Map(int width,int length) {
		listObjects=new ArrayList<DisplayableFather>();
		this.width = width;
		this.length = length;
		map=this;
	}


	public void paint() {
		for(DisplayableFather object: listObjects){
			object.paint();
			
		}

	}
	

	public DisplayableFather add(DisplayableFather object){
		listObjects.add(object);
		return object;
	}
	
	public DisplayableFather remove(DisplayableFather object){
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
	
	public ArrayList<DisplayableFather> getObjects(){
		return listObjects;
	}
	
	public static Map getMap(){
		return map;
	}

}

