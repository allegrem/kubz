package kubzDemo;

public class BSlider extends Slider{

	public BSlider( int min, int max, Fenetre fenetre) {
		super("B", min, max, fenetre);
	}

	@Override
	public void updateValue(int value) {
		fenetre.setB(value);
		
	}
	

}
