package synthesis.soundlab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.security.InvalidParameterException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import synthesis.filters.BandsFilter;

/**
 * This class is the view for one band filter in a {@link BandsFilter}. Its
 * value can be modified between 0 (-infinity dB) and 100 (0dB). Its value is
 * synchronized with the BandsFilter (when the slider moves, the filter is
 * modified, and the when the filter changes, the slider is moved).
 * 
 * @author allegrem
 */
public class SLFilterSlider extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private final int num;

	private BandsFilter eq;

	private JSlider slider;

	private JLabel valueLabel;

	// tells if the change comes from the model or from the user
	private boolean changeFromModel = false;

	/**
	 * Create a new SLFilterSlider. It will create the controls and configure
	 * the listener and observer.
	 * @param num The number of the bar in the filter.
	 * @param eq The {@link BandsFilter} where the controller lives in.
	 */
	public SLFilterSlider(final int num, final BandsFilter eq) {
		super();
		
		if (num < 0 || num >= eq.getBarNumber())
			throw new InvalidParameterException("bar number out of range");
		this.num = num;
		
		this.eq = eq;
		eq.addObserver(this);

		setLayout(new BorderLayout());

		slider = new JSlider(1, 100, 100);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setPreferredSize(new Dimension(30, 130));
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// prevent infinite update calls with model
				if (!changeFromModel)
					eq.setBar(num, slider.getValue());
				valueLabel.setText(String.valueOf(slider.getValue()));
			}
		});
		add(slider, BorderLayout.CENTER);

		valueLabel = new JLabel(String.valueOf(slider.getValue()));
		add(valueLabel, BorderLayout.NORTH);

		JLabel label = new JLabel(String.valueOf(num));
		add(label, BorderLayout.SOUTH);
	}

	/**
	 * Change the value of the slider according to the model. This method is 
	 * protected against infinite update calls between controller and model.
	 */
	@Override
	public void update(Observable o, Object arg) {
		changeFromModel = true;
		slider.setValue(eq.getBar(num));
		changeFromModel = false;
		
//		System.out.println("filter slider updated");
	}

}
