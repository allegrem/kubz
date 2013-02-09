/**
 * 
 */
package synthesis.soundlab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import synthesis.Sound;

/**
 * @author allegrem
 * 
 */
public class SLSoundView extends JPanel implements Observer {

	private static final int Y_SIZE = 200;

	private static final int X_SIZE = 600;

	private static final long serialVersionUID = 1L;

	private int zoomX = 0;

	private int currentSoundLength = 0;

	private int offsetX = 0;

	private static final int MANUAL_OFFSET = 25;

	private SLWindow window;

	public SLSoundView(SLWindow window) {
		super();
		
		this.window = window;

		setPreferredSize(new Dimension(X_SIZE, Y_SIZE + 20));
		setMinimumSize(new Dimension(X_SIZE, Y_SIZE + 20));
		setLayout(new BorderLayout(0, 0));

		createToolbar();
	}

	private void createToolbar() {
		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setFloatable(false);
		add(toolBar_2, BorderLayout.NORTH);

		JButton btnZoomIn = new JButton("Zoom in");
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoom(0.5);
			}
		});
		toolBar_2.add(btnZoomIn);

		JButton btnZoomOut = new JButton("Zoom out");
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoom(2);
				if (zoomX == 0 && currentSoundLength > 0)
					zoomX = 2;
			}
		});
		toolBar_2.add(btnZoomOut);

		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				offset(-1 * zoomX / 10);
			}
		});
		toolBar_2.add(button);

		JButton button_1 = new JButton(">");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				offset(zoomX / 10);
			}
		});
		toolBar_2.add(button_1);

		Component horizontalGlue = Box.createHorizontalGlue();
		toolBar_2.add(horizontalGlue);

		JLabel lblSignalView = new JLabel("Signal View");
		toolBar_2.add(lblSignalView);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Sound sound = window.getSound();

		if (sound != null) {
			byte[] soundBytes = sound.getSound();
			int maxX = zoomX - 2;
			int xCoord1 = 0, yCoord1 = 0, xCoord2 = 0, yCoord2 = 0;
			for (int x = 0; x < maxX - 1; x++) {
				xCoord2 = x * X_SIZE / zoomX;
				yCoord2 = Y_SIZE - (soundBytes[offsetX + x] + 127) * Y_SIZE
						/ 255 + MANUAL_OFFSET;
				g.drawLine(xCoord1, yCoord1, xCoord2, yCoord2);
				xCoord1 = xCoord2;
				yCoord1 = yCoord2;
			}
			g.drawLine(0, MANUAL_OFFSET + Y_SIZE / 2, X_SIZE, MANUAL_OFFSET
					+ Y_SIZE / 2);
		}
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

	private void offset(int i) {
		offsetX += i;
		if (offsetX + zoomX > currentSoundLength)
			offsetX = currentSoundLength - zoomX; // don't go out of range
		if (offsetX < 0)
			offsetX = 0;
		updateUI();
	}

	@Override
	public void update(Observable o, Object arg) {
		currentSoundLength = window.getSoundLength();
		zoomX = currentSoundLength;
		updateUI();
		
		System.out.println("sound view updated");
	}

}
