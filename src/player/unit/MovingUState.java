package player.unit;

/**
 * 
 * @author Felix
 *
 */

public class MovingUState extends UnitState {

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public boolean canTap() {
		return true;
	}

	@Override
	public boolean canRotate() {
		return true;
	}

}
