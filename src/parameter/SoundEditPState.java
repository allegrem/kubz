package parameter;

public class SoundEditPState extends ParameterState{

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
