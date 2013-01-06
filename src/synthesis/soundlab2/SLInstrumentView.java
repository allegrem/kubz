package synthesis.soundlab2;

import javax.swing.JPanel;

import synthesis.AudioBlock;

/**
 * @author allegrem
 *
 */
public class SLInstrumentView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private AudioBlock instrument;

	/**
	 * @param instrument
	 */
	public SLInstrumentView() {
		super();
	}
	
	
	public void setInstrument(AudioBlock instrument) {
		this.instrument = instrument;
	}
	
}
