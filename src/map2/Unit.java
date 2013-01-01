package map2;

import org.lwjgl.util.Color;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.ReadablePoint;
/**
 * Une unité d'un joueur ou un monstre
 * 
 * @author paul
 *
 */
public abstract class Unit {
	private Point position;
	private Color color;
	protected ReadableColor actualColor;
	
	
	public Unit(Point position,Color color){
		this.position=position;
		this.color=color;
		actualColor=color;
	}
	
	public abstract void paint();
	
	public void translate(int dx, int dy){
		position.translate(dx,dy);
	}
	
	public void translate(ReadablePoint p){
		position.translate(p);
	}
	
	public void setLocation(int x, int y){
		position.setLocation(x,y);
		
	}
	
	public void setLocation(ReadablePoint p){
		position.setLocation(p);
		
	}
	
	public int getX(){
		
		return position.getX();
	}
	
	public int getY(){
		
		return position.getY();
	}
	
	/**
	 * Si le cube n'est plus sur la table et n'est plus repéré par la caméra, on affiche l'unité en rouge
	 * 
	 */
	public void unitUntracked(){
		actualColor=Color.RED;
		
	}
	/**
	 * Si le cube est reposé sur la tabe, l'unité reprend sa couleur normale
	 * 
	 */
	public void unitTracked(){
		actualColor=color;
		
	}
	
}
