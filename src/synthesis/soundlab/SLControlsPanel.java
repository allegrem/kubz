package synthesis.soundlab;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import synthesis.soundlab2.SLParameterView;


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
	
	
	public SLParameterView addControl(String name, int min, int max) {
		SLParameterView slControl = new SLParameterView(name, min, max);
		add(slControl);
		return slControl;
	}
	
}
