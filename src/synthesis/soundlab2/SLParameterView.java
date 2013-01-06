package synthesis.soundlab2;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import synthesis.basicblocks.noinputblocks.Parameter;

/**
 * @author allegrem
 * 
 */
public class SLParameterView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private JSlider slider;

	private JLabel valueLabel;

	private Parameter parameter;

	/**
	 * @param name
	 * @param min
	 * @param max
	 */
	public SLParameterView(Parameter parameter) {
		super();

		this.parameter = parameter;
		parameter.addObserver(this);
		buildControl();
	}

	private void buildControl() {
		int defaultValue = (parameter.getMax() - parameter.getMin()) / 2;

		add(new JLabel(parameter.getLabel()));

		this.slider = new JSlider(parameter.getMin(), parameter.getMax(),
				defaultValue);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				valueLabel.setText(String.valueOf(slider.getValue()));
				parameter.setValue(slider.getValue());
			}
		});
		add(slider);

		this.valueLabel = new JLabel(String.valueOf(defaultValue));
		add(valueLabel);
	}

	@Override
	public void update(Observable o, Object arg) {
		slider.setValue(parameter.getValue());
		valueLabel.setText(String.valueOf(parameter.getValue()));
	}

}
