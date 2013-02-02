package synthesis.soundlab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This class is the controller for a {@link ParameterAudioBlock}. It shows a
 * slider and a text showing the current value of the parameter. When the slider
 * is moved, the linked ParameterAudioBlock is modified.
 * 
 * @author allegrem
 */
public class SLParameterView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JSlider slider;

	private JLabel valueLabel;

	private ParameterAudioBlock paramBlock;

	/**
	 * Create a new parameter view.
	 * 
	 * @param p
	 *            the ParameterAudioBlock to control.
	 */
	public SLParameterView(ParameterAudioBlock p) {
		super();
		this.paramBlock = p;
		buildControl();
	}

	/**
	 * Build the actual controller : a slider and a label both displaying the
	 * current value of the ParameterAudioBlock. A {@link ChangeListener} is
	 * also added to notify the ParameterAudioBlock when the slider is moved.
	 */
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
		valueLabel.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent arg0) {}
			@Override public void mousePressed(MouseEvent arg0) {}
			@Override public void mouseExited(MouseEvent arg0) {}
			@Override public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final JTextField valueTextField = new JTextField(String.valueOf(paramBlock.getValue()), 5);
				valueTextField.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						slider.setValue(Integer.parseInt(valueTextField.getText()));
						remove(valueTextField);
						add(valueLabel);
						updateUI();
					}
				});
				add(valueTextField);
				
				remove(valueLabel);
				updateUI();
			}
		});
		add(valueLabel);
	}
}
