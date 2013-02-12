package monster.zoo;


import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.informationViews.LifeView;
import views.monsters.CircleMonsterView;
import gameEngine.GameEngine;

public class CircleMonster extends Monster {

	public CircleMonster(float xStart, float yStart,ReadableColor color,GameEngine gameEngine) {
		super(xStart, yStart,gameEngine);
		view= new CircleMonsterView(new Point(xStart,yStart), color);
		gameEngine.getMap().add(view);
		lifeView=new LifeView(view);
		view.addChild(lifeView);
	}

}
