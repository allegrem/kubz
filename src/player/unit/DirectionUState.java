package player.unit;

/**
 * 
 * @author Felix
 *
 */
public class DirectionUState extends UnitState {

	@Override
	public boolean canTap() {
		return true;
	}

	@Override
	public boolean canRotate() {
		return true;
	}

}
