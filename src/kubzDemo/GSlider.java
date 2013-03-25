package kubzDemo;

public class GSlider extends Slider{

	public GSlider( int min, int max, Fenetre fenetre) {
		super("G", min, max, fenetre);
	}

	@Override
	public void updateValue(int value) {
		fenetre.setG(value);
		
	}
	

}