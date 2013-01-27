/**
 * 
 */
package synthesis.soundlab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import synthesis.filters.Equalizer;

/**
 * @author allegrem
 * 
 */
public class SLFilterView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private static final int BARS = 11;

	private Equalizer equalizer;

	private SLWindow window;

	public SLFilterView(SLWindow window) {
		super();
		this.window = window;
		setLayout(new BorderLayout(0, 0));

		equalizer = new Equalizer(BARS);
		equalizer.addObserver(this);

		JPanel panel = new JPanel();
		for (int i = 0; i < BARS; i++) {
			JPanel slider = new FilterSlider(i, equalizer);
			panel.add(slider);
		}
		add(panel, BorderLayout.CENTER);

		createToolbar();
	}

	private void createToolbar() {
		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setFloatable(false);
		add(toolBar_2, BorderLayout.NORTH);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				equalizer.resetBars(100);
			}
		});
		toolBar_2.add(btnReset);

		JButton btnRandom = new JButton("Random");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				equalizer.random(100);
			}
		});
		toolBar_2.add(btnRandom);

		Component horizontalGlue = Box.createHorizontalGlue();
		toolBar_2.add(horizontalGlue);

		JLabel lblSignalView = new JLabel("Filter");
		toolBar_2.add(lblSignalView);
	}

	public byte[] filter(byte[] sound) {
		return equalizer.filter(sound);
	}

	@Override
	public void update(Observable o, Object arg) {
		window.updateSound();
	}

	private class FilterSlider extends JPanel implements ChangeListener,
			Observer {

		private static final long serialVersionUID = 1L;

		private final int num;

		private Equalizer eq;

		private JSlider slider;

		private JLabel valueLabel;

		// tells if the change comes from the model or from the user
		private boolean changeFromModel = false;

		/**
		 * @param min
		 * @param max
		 * @param value
		 */
		public FilterSlider(int num, Equalizer eq) {
			super();
			this.num = num;
			this.eq = eq;

			eq.addObserver(this);

			setLayout(new BorderLayout());

			slider = new JSlider(1, 100, 100);
			slider.setOrientation(JSlider.VERTICAL);
			slider.setPreferredSize(new Dimension(30, 130));
			slider.addChangeListener(this);
			add(slider, BorderLayout.CENTER);

			valueLabel = new JLabel(String.valueOf(slider.getValue()));
			add(valueLabel, BorderLayout.NORTH);

			JLabel label = new JLabel(String.valueOf(num));
			add(label, BorderLayout.SOUTH);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (!changeFromModel) // prevent infinite update calls with model
				eq.setBar(num, slider.getValue());
			valueLabel.setText(String.valueOf(slider.getValue()));
		}

		@Override
		public void update(Observable o, Object arg) {
			changeFromModel = true;
			slider.setValue(eq.getBar(num));
			changeFromModel = false;
		}

	}

}
