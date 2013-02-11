package monster.move;

/**
 * Mouvements normaux qui se d�place vers sa cible
 * @author Felix
 */

import player.unit.Unit;
import monster.zoo.Monster;

public class RegularMove extends MoveType{
	

	
	public RegularMove(Monster monster, int speed) {
		super(monster, speed);
	}

	@Override
	public void move(){
		Unit cible = monster.getCible();
		if (cible!=null){
			moveTo(cible);
		}
		else{
			randMove();
		}
	}

}
