package base;

import org.lwjgl.util.ReadableColor;
import views.BaseView;

import utilities.Point;

public class Base {
	private Point center;
	private int sens;
	private ReadableColor color;
	private static final float radius = 80.0f; // on code le rayon des bases "en dur"
	private BaseView view;
	
	/**
	 * Constructeur � partir de la vue ...
	 * @param center
	 * @param sens
	 * @param color
	 * @param view
	 */
	public Base(Point center, int sens, ReadableColor color, BaseView view) {
		super();
		this.center = center;
		this.sens = sens;
		this.color = color;
		this.view = view;
	}
	/**
	 * Constructuer � partir du mod�le, qui va cr�er la vue associ�e
	 * @param center
	 * @param sens
	 * @param color
	 */
	public Base(Point center, int sens, ReadableColor color) {
		super();
		this.center = center;
		this.sens = sens;
		this.color = color;
		view = new BaseView(center, color, sens);
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
	
}
