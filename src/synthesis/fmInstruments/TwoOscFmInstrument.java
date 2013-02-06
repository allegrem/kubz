package synthesis.fmInstruments;

import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * A basic FM instrument with two oscillators. Parameters are the carrier
 * frequency, the modulation frequency, the amplitude, and the modulation index. <br />
 * The sound produced is given by : s(t) = amp * cos(2 * pi * fp * t + mod *
 * sin(2 * pi * fm * t))
 * 
 * @author allegrem
 */
public class TwoOscFmInstrument extends FmInstrument {

	private final ParameterAudioBlock fm;
	private final ParameterAudioBlock fp;
	private final ParameterAudioBlock amp;
	private final ParameterAudioBlock mod;

	/**
	 * Create a new TwoOscFmInstrument.
	 */
	public TwoOscFmInstrument() {
		super();

		fm = addParam(new ParamBlock("fm", 20, 11000, 440));
		fp = addParam(new ParamBlock("fp", 20, 11000, 880));
		amp = addParam(new ParamBlock("amp", 0, 120, 100));
		mod = addParam(new ParamBlock("mod", 0, 100, 2));

		SineWaveOscillator osc1 = new SineWaveOscillator(fm, new Multiplier(
				mod, fm));
		Adder add = new Adder(fp, osc1);
		setOut(new SineWaveOscillator(add, amp));
	}

}
