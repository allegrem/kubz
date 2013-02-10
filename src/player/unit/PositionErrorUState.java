package player.unit;

/**
 * 
 * @author Felix
 *
 */

public class PositionErrorUState extends UnitState {

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public boolean canRotate() {
		return true;
	}

}
