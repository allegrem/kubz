package synthesis.soundlab;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;

/**
 * @author allegrem
 *
 */
public class SLWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private final SLControlsPanel slControlsPanel;

	/**
	 * @throws HeadlessException
	 */
	public SLWindow() {
		super("kubz soundlab");
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setPreferredSize(new Dimension(600, 400));
	    
	    slControlsPanel = new SLControlsPanel();
	    setContentPane(slControlsPanel);
	    
	    pack();
	    setVisible(true);
	}
	
	
	public SLControl addControl(String name, int min, int max) {
		SLControl control = slControlsPanel.addControl(name, min, max);
		pack();
		return control;
	}

}
