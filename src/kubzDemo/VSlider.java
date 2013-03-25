package kubzDemo;

import java.awt.Component;

public class VSlider extends Slider {

	public VSlider( int min, int max, Fenetre fenetre) {
		super("Vibreur", min, max, fenetre);
	}

	@Override
	public void updateValue(int value) {
		fenetre.setV(value);
	}

}
