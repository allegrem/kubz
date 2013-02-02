package synthesis.soundlab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import synthesis.filters.BandsFilter;

/**
 * This class is the view for a {@link BandsFilter}. Each band filter is
 * controlled by a {@link SLFilterSlider}. The view also provides some action
 * buttons : reset (sets all the sliders to 100), random (generate a random
 * filter) and bypass (skip the filtering when computing the sound).
 * 
 * @author allegrem
 */
public class SLBandsFilterView extends JPanel {

	private static final long serialVersionUID = 1L;

	private BandsFilter bandsFilter;

	private SLWindow window;

	private JButton btnBypass;

	/**
	 * Create a new {@link SLBandsFilterView}.
	 * 
	 * @param window
	 *            the {@link SLWindow} where the view lives in.
	 * @param bandsFilter
	 *            the {@link BandsFilter} to show.
	 */
	public SLBandsFilterView(SLWindow window, BandsFilter bandsFilter) {
		super();
		this.window = window;
		this.bandsFilter = bandsFilter;

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		for (int i = 0; i < bandsFilter.getBarNumber(); i++) {
			JPanel slider = new SLFilterSlider(i, bandsFilter);
			panel.add(slider);
		}
		add(panel, BorderLayout.CENTER);

		createToolbar();
	}

	/**
	 * Create the toolbar of the view. It provides three action buttons : reset,
	 * random and bypass.
	 */
	private void createToolbar() {
		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setFloatable(false);
		add(toolBar_2, BorderLayout.NORTH);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bandsFilter.resetBars(100);
			}
		});
		toolBar_2.add(btnReset);

		JButton btnRandom = new JButton("Random");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bandsFilter.random();
			}
		});
		toolBar_2.add(btnRandom);

		btnBypass = new JButton("Bypass");
		btnBypass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// window.toogleBypass(); TODO
				if (btnBypass.getBackground() != Color.GRAY)
					btnBypass.setBackground(Color.GRAY);
				else
					btnBypass.setBackground(null);
			}
		});
		toolBar_2.add(btnBypass);

		Component horizontalGlue = Box.createHorizontalGlue();
		toolBar_2.add(horizontalGlue);

		JLabel lblSignalView = new JLabel("Filter");
		toolBar_2.add(lblSignalView);
	}

}
