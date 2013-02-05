package base;

import gameEngine.GameEngine;
import org.lwjgl.util.ReadableColor;
import utilities.Point;
import views.staticViews.BaseView;

public class Base {
	private Point center;
	private int sens;
	private ReadableColor color;
	private static final float radius = 80.0f; // on code le rayon des bases "en dur"
	private BaseView view;
	private GameEngine gameEngine;

	/**
	 * Constructeur a partir du modele, qui va creer la vue associee
	 * @param center
	 * @param sens
	 * @param color
	 * @param gameEngine 
	 */
	public Base(Point center, ReadableColor color, int sens, GameEngine gameEngine) {
		super();
		this.center = center;
		this.sens = sens;
		this.color = color;
		this.gameEngine=gameEngine;
		view = new BaseView(center, color, sens);
		gameEngine.getMap().add(view);
	}
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
		view.setCenter(center);
	}
	public int getSens() {
		return sens;
	}
	public void setSens(int sens) {
		this.sens = sens;
	}
	public ReadableColor getColor() {
		return color;
	}
	public void setColor(ReadableColor color) {
		this.color = color;
	}
	public GameEngine getGameEngine(){
		return gameEngine;
	}
	
	
}
