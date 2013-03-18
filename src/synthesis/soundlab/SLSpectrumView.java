package synthesis.soundlab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.commons.math3.complex.Complex;



import synthesis.AudioBlock;

/**
 * @author allegrem
 * 
 */
public class SLSpectrumView extends JPanel implements Observer {

	private static final String DEFAULT_TOOLBAR_TEXT = "- Hz ; - dB";

	public static final int Y_SIZE = 200;

	private static final int X_SIZE = 600;

	private static final long serialVersionUID = 1L;

	private JLabel lblHz;

	private int mouseX = -1;

	private int[] spectrumCache;

	private SLWindow window;

	public SLSpectrumView(SLWindow window) {
		super();
		
		this.window = window;
		
		setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
		setMinimumSize(new Dimension(X_SIZE, Y_SIZE));
		setLayout(new BorderLayout(0, 0));

		createToolbar();

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = (int) (e.getX() * AudioBlock.SAMPLE_RATE / 12 / SLSpectrumView.Y_SIZE); // empirique...
				String text = String.valueOf(x) + " Hz ; ";
				if (spectrumCache == null || e.getX() > spectrumCache.length)
					text += "- dB";
				else
					text += String.valueOf((int) (11 - 0.61 * spectrumCache[e
							.getX()])) + " dB"; // empirique...
				lblHz.setText(text);

				// update UI to show the two lines
				mouseX = e.getX();
				updateUI();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblHz.setText(DEFAULT_TOOLBAR_TEXT);

				// update UI to hide the two lines
				mouseX = -1;
				updateUI();
			}
		});
		
	}

	private void createToolbar() {
		JToolBar toolBar_3 = new JToolBar();
		toolBar_3.setFloatable(false);
		add(toolBar_3, BorderLayout.NORTH);

		lblHz = new JLabel(DEFAULT_TOOLBAR_TEXT);
		toolBar_3.add(lblHz);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		toolBar_3.add(horizontalGlue_1);

		JLabel lblSpectrumView = new JLabel("Spectrum View");
		toolBar_3.add(lblSpectrumView);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (spectrumCache != null && spectrumCache.length > 0) {
			for (int x = 0; x < X_SIZE; x++)
				g.drawLine(x, Y_SIZE, x, spectrumCache[x]);

			// display the two lines
			if (mouseX != -1 && mouseX < spectrumCache.length) {
				g.setColor(Color.DARK_GRAY);
				g.drawLine(0, spectrumCache[mouseX], X_SIZE,
						spectrumCache[mouseX]);
				g.drawLine(mouseX, 0, mouseX, Y_SIZE);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
//		Complex[] result = sound.getSpectrum();
		Complex[] result = window.getSound().getSpectrum();
		
		spectrumCache = new int[X_SIZE];
		for (int x = 0; x < result.length / 4; x++) {
			int x_coord = x * X_SIZE * 4 / result.length;
			int y1 = (int) (Y_SIZE + 75 - Math.abs(Math.log10(0.0001 + result[x]
					.abs())) * Y_SIZE / 5);
			int y2 = (int) (Y_SIZE + 75 - Math.abs(Math
					.log10(0.0001 + result[result.length / 4 + x].abs()))
					* Y_SIZE / 5);
			if (y2 < y1) // keep the higher value
				y1 = y2;
			if (spectrumCache[x_coord] == 0 || y1 < spectrumCache[x_coord])
				spectrumCache[x_coord] = y1; // save the result in cache
		}
		updateUI();
		
		System.out.println("spectrum view updated");
	}

}
