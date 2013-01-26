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
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

//import org.apache.commons.math.complex.Complex;
//import org.apache.commons.math.transform.FastFourierTransformer;

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

	private SLWindow window;

	private JLabel lblHz;

	private int[] spectrumCache;

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
				int x = (int) (e.getX() * AudioBlock.SAMPLE_RATE / 12 / SLSpectrumView.Y_SIZE); //empirique...
				String text = String.valueOf(x) + " Hz ; ";
				if(e.getX() > spectrumCache.length)
					text += "- dB";
				else
					text += String.valueOf(55 - spectrumCache[e.getX()]) + " dB"; //empirique...
				lblHz.setText(text);
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblHz.setText("- Hz ; - dB");
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

		if (window != null) {
			byte[] soundBytes = window.getLastSound();
			
			//looking for the smallest power of two above sound length
			int power2Length = 1;
			while(power2Length < soundBytes.length)
				power2Length *= 2;
			
			//converting byte array to double array
			double[] sound = new double[power2Length];
			for(int i=0; i<soundBytes.length; i++)
				sound[i] = soundBytes[i];
			for(int i=soundBytes.length; i<power2Length; i++) 
				sound[i] = 0;  //add zeros at the end
			
			//compute fourier transform
			FastFourierTransformer fourier = new FastFourierTransformer(DftNormalization.STANDARD);
			Complex[] result = fourier.transform(sound, TransformType.FORWARD); //PRENDRE fft(double) !!!!! (protected??)
			//never ask me why it works, i dont know!!
			spectrumCache = new int[X_SIZE+2];
			for (int x = 0; x < result.length/4; x++) {
				int x_coord = x * X_SIZE * 4 / result.length;
				int y1 = (int) (Y_SIZE - Math.abs(Math.log10(0.0001 + result[x].abs())) * Y_SIZE / 7);
				int y2 = (int) (Y_SIZE - Math.abs(Math.log10(0.0001 + result[result.length/4 + x].abs())) * Y_SIZE / 7);
				int max = y1;
				if (y2 < y1)
					max = y2;
				g.drawLine(x_coord, Y_SIZE, x_coord, max);
				spectrumCache[x_coord] = max; //save the result in cache
			}
		}
	}

}
