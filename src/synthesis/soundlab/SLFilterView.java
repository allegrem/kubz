/**
 * 
 */
package synthesis.soundlab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class SLFilterView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int BARS = 10;
	
	private Equalizer equalizer;

	private SLWindow window;
	
	public SLFilterView(SLWindow window) {
		super();
		this.window = window;
		setLayout(new BorderLayout(0, 0));
		
		equalizer = new Equalizer(BARS);
		
		JPanel panel = new JPanel();
		for(int i=0; i<BARS; i++) {
			JPanel slider = new FilterSlider(i, equalizer, this);
			panel.add(slider);
		}
		add(panel, BorderLayout.CENTER);
		
		createToolbar();
	}

	private void createToolbar() {
		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setFloatable(false);
		add(toolBar_2, BorderLayout.NORTH);

		Component horizontalGlue = Box.createHorizontalGlue();
		toolBar_2.add(horizontalGlue);

		JLabel lblSignalView = new JLabel("Filter");
		toolBar_2.add(lblSignalView);
	}
	
	
	public byte[] filter(byte[] sound) {
		return equalizer.filter(sound);
	}
	
	
	public void update() {
		window.updateSound();
	}
	
	
	
	private class FilterSlider extends JPanel implements ChangeListener {

		private static final long serialVersionUID = 1L;
		
		private final int num;

		private Equalizer eq;

		private JSlider slider;

		private JLabel valueLabel;

		private SLFilterView filterView;

		/**
		 * @param min
		 * @param max
		 * @param value
		 */
		public FilterSlider(int num, Equalizer eq, SLFilterView filterView) {
			super();
			this.num = num;
			this.eq = eq;
			this.filterView = filterView;
			
			setLayout(new BorderLayout());
			
			slider = new JSlider(1, 100, 100);
			slider.setOrientation(JSlider.VERTICAL);
			slider.setPreferredSize(new Dimension(30,150));
			add(slider, BorderLayout.CENTER);
			
			JLabel label = new JLabel("FIXME");
			add(label, BorderLayout.SOUTH);
			
			valueLabel = new JLabel("100");
			add(valueLabel, BorderLayout.NORTH);
			
			slider.addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			eq.setBar(num, slider.getValue());
			valueLabel.setText(String.valueOf(slider.getValue()));
			filterView.update();
		}
		
	}

}
