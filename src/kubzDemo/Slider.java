package kubzDemo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import synthesis.parameter.ParameterAudioBlock;


public abstract class Slider extends JPanel {

	private static final long serialVersionUID = 1L;

	private JSlider slider;

	private JLabel valueLabel;
	
	private String name;
	private int min=0;
	private int max=100;
	private int value=0;
	protected Fenetre fenetre;



	public Slider(String name, int min,int max, Fenetre fenetre) {
		super();
		this.name=name;
		this.min=min;
		this.max=max;
		this.fenetre=fenetre;
		value=(min+max)/2;
		build();
	}


	private void build() {
		this.slider = new JSlider(min, max,
				(min+max)/2);
		this.valueLabel = new JLabel(name+"= "+String.valueOf((min+max)/2));
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				valueLabel.setText(name + "= "+String.valueOf(slider.getValue()));
				value=slider.getValue();
				updateValue(value);
			}

			
		});
		slider.setOrientation(SwingConstants.VERTICAL);
		add(slider);
		add(valueLabel);

		
	}
	public abstract void updateValue(int value);
}