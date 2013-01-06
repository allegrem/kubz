/**
 * 
 */
package synthesis.soundlab;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author allegrem
 * 
 */
public class SLSoundView extends JPanel {

	private static final int Y_SIZE = 200;

	private static final int X_SIZE = 600;

	private static final long serialVersionUID = 1L;

	private SLWindow window;

	private int zoomX = 0;

	private int currentSoundLength = 0;

	private int offsetX = 0;

	SLSoundView(SLWindow window) {
		super();
		this.window = window;
		setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (window != null) {
			byte[] sound = window.getLastSound();
			for (int x = 0; x < zoomX - 2; x++) {
				g.drawLine(x * X_SIZE / zoomX, (sound[offsetX + x] + 127)
						* Y_SIZE / 255 + 25, (x + 1) * X_SIZE / zoomX,
						(sound[offsetX + x + 1] + 127) * Y_SIZE / 255 + 25);
			}
		}
	}

	public void zoomIn() {
		zoom(0.5);
	}

	public void zoomOut() {
		zoom(2);
		if (zoomX == 0 && currentSoundLength > 0)
			zoomX = 2;
	}

	private void zoom(double d) {
		zoomX *= d;
		if (zoomX > currentSoundLength) // cant zoom larger than sound length
			zoomX = currentSoundLength;
		if (offsetX + zoomX > currentSoundLength) // move to left if we zoom out
													// and get out of range
			offsetX = currentSoundLength - zoomX;
		updateUI();
	}

	public void zoomAll(int soundLength) {
		currentSoundLength = soundLength;
		zoomX = soundLength;
	}

	public void toRight() {
		offset(zoomX / 10);
	}

	public void toLeft() {
		offset(-1 * zoomX / 10);
	}

	private void offset(int i) {
		offsetX += i;
		if (offsetX + zoomX > currentSoundLength)
			offsetX = currentSoundLength - zoomX; // dont go out of range
		if (offsetX < 0)
			offsetX = 0;
		updateUI();
	}

}
