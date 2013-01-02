package map2;

import org.lwjgl.util.Color;

import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.ReadablePoint;

/**
 * Une unité d'un joueur ou un monstre
 * 
 * @author paul
 * 
 */
public abstract class Unit {
	protected static final double size= 10.0;
	protected static final int height = 10;
	private Point position;
	private ReadableColor color;
	protected ReadableColor actualColor;

	public Unit(Point position, ReadableColor color) {
		this.position = position;
		this.color = color;
		actualColor = color;
	}

	public abstract void paint();

	public void translate(int dx, int dy) {
		position.translate(dx, dy);
	}

	

	public void setLocation(int x, int y) {
		position.move(x, y);

	}

	public void setLocation(Point p) {
		position.setLocation(p);

	}

	public double getX() {

		return position.getX();
	}

	public double getY() {

		return position.getY();
	}

	/**
	 * Si le cube n'est plus sur la table et n'est plus repéré par la caméra, on
	 * affiche l'unité en rouge
	 * 
	 */
	public void unitUntracked() {
		actualColor = Color.RED;

	}

	/**
	 * Si le cube est reposé sur la tabe, l'unité reprend sa couleur normale
	 * 
	 */
	public void unitTracked() {
		actualColor = color;

	}

}
