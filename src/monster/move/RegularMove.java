package monster.move;

/**
 * Mouvements normaux qui se déplace vers sa cible
 * @author Felix
 */

import player.unit.Unit;
import monster.Monster;

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
