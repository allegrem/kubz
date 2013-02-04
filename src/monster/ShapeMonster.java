package monster;

import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.monsters.ShapeMonsterView;
import gameEngine.GameEngine;

public class ShapeMonster extends Monster {

	public ShapeMonster(float xStart, float yStart,ReadableColor color,GameEngine gameEngine) {
		super(xStart, yStart, gameEngine);
		view= new ShapeMonsterView(new Point(xStart,yStart), color);
		gameEngine.getMap().add(view);
	}

}
