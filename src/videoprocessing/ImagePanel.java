package videoprocessing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import javax.swing.JPanel;


public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	
	public ImagePanel(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public void updateImage(Raster newImage) {
		image.setData(newImage);
		updateUI();
	}

	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }
	
}
