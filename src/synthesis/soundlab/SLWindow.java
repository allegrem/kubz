package synthesis.soundlab;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import synthesis.soundlab2.SLParameterView;

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
	
	
	public SLParameterView addControl(String name, int min, int max) {
		SLParameterView control = slControlsPanel.addControl(name, min, max);
		pack();
		return control;
	}

}
