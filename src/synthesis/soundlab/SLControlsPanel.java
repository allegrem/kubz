package synthesis.soundlab;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 * @author allegrem
 *
 */
public class SLControlsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public SLControlsPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	
	public SLControl addControl(String name, int min, int max) {
		SLControl slControl = new SLControl(name, min, max);
		add(slControl);
		return slControl;
	}
	
}
