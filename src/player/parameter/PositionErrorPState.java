package player.parameter;

public class PositionErrorPState extends ParameterState{

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public boolean canRotate() {
		return true;
	}

}
