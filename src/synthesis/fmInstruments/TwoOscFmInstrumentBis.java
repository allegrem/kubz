package synthesis.fmInstruments;

import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * An FmInstrument based on the FM 2 oscillators instrument but 
 * with special ranges for the the fm and the modulation factor, which 
 * make a funny noise.
 * @author valeh
 * @see TwoOscFmInstrument
 */
public class TwoOscFmInstrumentBis extends FmInstrument {
	private ParameterAudioBlock fp;
	private ParameterAudioBlock mod;
	private ParameterAudioBlock amp;

	public TwoOscFmInstrumentBis() {
		fp = addParam(new ParamBlock("fp", 20, 1000, 500));
		amp = addParam(new ParamBlock("amp", 0, 120, 100));
		mod = addParam(new ParamBlock("mod", 30, 500, 50));

		SineWaveOscillator osc1 = new SineWaveOscillator(new Constant(1f), mod);
		Adder adder = new Adder(osc1, fp);
		setOut(new SineWaveOscillator(adder, amp));
	}

}
