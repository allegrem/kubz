/**
 * 
 */
package synthesis.soundlab;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 * @author allegrem
 *
 */
public class SLSpectrumView extends JPanel {
	
	public static final int Y_SIZE = 200;

	private static final int X_SIZE = 600;

	private static final long serialVersionUID = 1L;

	private SLWindow window;

	public SLSpectrumView(SLWindow window) {
		super();
		this.window = window;
		setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
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
			Complex[] result = fourier.transform(sound, TransformType.FORWARD);
			//never ask me why it works, i dont know!!
			for (int x = 0; x < result.length / 4; x++) {
				int x_coord = x * X_SIZE * 4 / result.length;
				g.drawLine(x_coord, Y_SIZE, x_coord,
						(int) (Y_SIZE - Math.abs(Math.log10(0.0001 + result[x].abs())) * Y_SIZE / 7));
				g.drawLine(x_coord, Y_SIZE, x_coord,
						(int) (Y_SIZE - Math.abs(Math.log10(0.0001 + result[result.length/2 + x].abs())) * Y_SIZE / 7));
			}
		}
	}

}
