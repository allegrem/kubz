package synthesis.soundlab;

import javax.swing.JPanel;

import Parameter.ParameterAudioBlock;
import synthesis.fmInstruments.FmInstrument;

/**
 * @author allegrem
 *
 */
public class SLInstrumentView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private FmInstrument instrument;

	/**
	 * @param instrument
	 */
	public SLInstrumentView() {
		super();
	}
	
	
	public void setInstrument(FmInstrument instrument) {
		this.instrument = instrument;
		for(ParameterAudioBlock p : instrument.getParameters()) {
			add(new SLParameterView(p));
		}
	}
	
}
