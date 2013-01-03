package synthesis.soundlab;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;


/**
 * @author allegrem
 *
 */
public class SLControl extends JPanel implements AudioBlock {

	private static final long serialVersionUID = 1L;
	private JSlider slider;
	private JLabel valueLabel;
	
	
	/**
	 * @param name
	 * @param min 
	 * @param max 
	 */
	public SLControl(String name, int min, int max) {
		super();
		
		int defaultValue = (max - min) / 2;
		
		add(new JLabel(name));
		
		this.slider = new JSlider(min, max, defaultValue);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				valueLabel.setText(String.valueOf(slider.getValue()));
			}
		});
		add(slider);
		
		this.valueLabel = new JLabel(String.valueOf(defaultValue));
		add(valueLabel);
	}
	

	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return (float) slider.getValue();
	}

	
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return (float) (2 * Math.PI * slider.getValue() * t);
	}

}
