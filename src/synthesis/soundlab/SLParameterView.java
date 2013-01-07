package synthesis.soundlab;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import synthesis.parameter.ParameterAudioBlock;



/**
 * @author allegrem
 * 
 */
public class SLParameterView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private JSlider slider;

	private JLabel valueLabel;

	private ParameterAudioBlock paramBlock;

	/**
	 * @param name
	 * @param min
	 * @param max
	 */
	public SLParameterView(ParameterAudioBlock p) {
		super();

		this.paramBlock = p;
		p.addObserver(this);
		buildControl();
	}

	private void buildControl() {
		add(new JLabel(paramBlock.getLabel()));

		this.slider = new JSlider(paramBlock.getMin(), paramBlock.getMax(),
				paramBlock.getValue());
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				valueLabel.setText(String.valueOf(slider.getValue()));
				paramBlock.setValue(slider.getValue());
			}
		});
		add(slider);

		this.valueLabel = new JLabel(String.valueOf(paramBlock.getValue()));
		add(valueLabel);
	}

	@Override
	public void update(Observable o, Object arg) {
		slider.setValue(paramBlock.getValue());
		valueLabel.setText(String.valueOf(paramBlock.getValue()));
	}

}
