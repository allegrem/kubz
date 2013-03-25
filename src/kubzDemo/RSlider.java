package kubzDemo;

public class RSlider extends Slider{

	public RSlider( int min, int max, Fenetre fenetre) {
		super("R", min, max, fenetre);
	}

	@Override
	public void updateValue(int value) {
		fenetre.setR(value);
		
	}
	

}
