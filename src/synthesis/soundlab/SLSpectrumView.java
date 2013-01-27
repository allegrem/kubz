/**
 * 
 */
package synthesis.soundlab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import synthesis.AudioBlock;

/**
 * @author allegrem
 * 
 */
public class SLSpectrumView extends JPanel {

	public static final int Y_SIZE = 200;

	private static final int X_SIZE = 600;

	private static final long serialVersionUID = 1L;

	private JLabel lblHz;

	private int[] spectrumCache;
	
	private int mouseX = -1;
	
	public SLSpectrumView() {
		super();
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
					text += String.valueOf((int) (11 - 0.61 * spectrumCache[e.getX()]))
							+ " dB"; // empirique...
				lblHz.setText(text);
				
				//update UI to show the two lines
				mouseX = e.getX();
				updateUI();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblHz.setText("- Hz ; - dB");
				
				//update UI to hide the two lines
				mouseX = -1;
				updateUI();
			}
		});

	}

	private void createToolbar() {
		JToolBar toolBar_3 = new JToolBar();
		toolBar_3.setFloatable(false);
		add(toolBar_3, BorderLayout.NORTH);

		lblHz = new JLabel("- Hz");
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
			for(int x=0; x<X_SIZE; x++)
				g.drawLine(x, Y_SIZE, x, spectrumCache[x]);
			
			//display the two lines
			if (mouseX != -1  &&  mouseX < spectrumCache.length) {
				g.drawLine(0, spectrumCache[mouseX], X_SIZE, spectrumCache[mouseX]);
				g.drawLine(mouseX, 0, mouseX, Y_SIZE);
			}
		}
	}

	public void computeSpectrum(byte[] soundBytes) {
		// looking for the smallest power of two above sound length
		int power2Length = 1;
		while (power2Length < soundBytes.length)
			power2Length *= 2;

		// converting byte array to double array
		double[] sound = new double[power2Length];
		for (int i = 0; i < soundBytes.length; i++)
			sound[i] = soundBytes[i];
		for (int i = soundBytes.length; i < power2Length; i++)
			sound[i] = 0; // add zeros at the end

		// compute fourier transform (never ask me why it works, i dont know!!)
		FastFourierTransformer fourier = new FastFourierTransformer(
				DftNormalization.STANDARD);
		Complex[] result = fourier.transform(sound, TransformType.FORWARD);
		spectrumCache = new int[X_SIZE];
		for (int x = 0; x < result.length/4; x++) {
			int x_coord = x * X_SIZE * 4 / result.length;
			int y1 = (int) (Y_SIZE - Math.abs(Math.log10(0.0001 + result[x]
					.abs())) * Y_SIZE / 7);
			int y2 = (int) (Y_SIZE - Math.abs(Math
					.log10(0.0001 + result[result.length / 4 + x].abs()))
					* Y_SIZE / 7);
			if (y2 < y1) //keep the higher value
				y1 = y2;
			if(spectrumCache[x_coord] == 0 || y1 < spectrumCache[x_coord])
				spectrumCache[x_coord] = y1; // save the result in cache
			
//			int x_coord = x * X_SIZE / result.length;
//			int y1 = (int) (Y_SIZE - Math.abs(Math.log10(0.0001 + result[x]
//					.abs())) * Y_SIZE / 7);
//			if(spectrumCache[x_coord] == 0 || y1 < spectrumCache[x_coord])
//				spectrumCache[x_coord] = y1; // save the result in cache
		}
		
		updateUI();
	}

}
