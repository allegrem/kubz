package synthesis.fmInstruments;

import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.ADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.parameter.GainParamBlock;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * @author allegrem
 * 
 */
public class PianoInstrument2 extends FmInstrument {

	private ParameterAudioBlock a;
	private ParameterAudioBlock f0;
	private ParameterAudioBlock epsilon;
	private ParameterAudioBlock amp;
	private ParameterAudioBlock d;
	private ParameterAudioBlock s;
	private ParameterAudioBlock r;

	/**
	 * 
	 */
	public PianoInstrument2() {
		super();

		a = addParam(new GainParamBlock("attack", 0, 100, 1, 0.01f));
		d = addParam(new GainParamBlock("decay", 0, 100, 10, 0.01f));
		s = addParam(new GainParamBlock("sustain", 0, 100, 60, 0.01f));
		r = addParam(new GainParamBlock("release", 0, 100, 80, 0.01f));
		f0 = addParam(new ParamBlock("f0", 20, 5000, 440));
		epsilon = addParam(new GainParamBlock("epsilon", 0, 100, 10, 0.1f));
		amp = addParam(new ParamBlock("amp", 0, 125, 100));

		ADSR env = new ADSR(a, d, s, r, 3f, amp);
		
		SineWaveOscillator osc1 = new SineWaveOscillator(
				new Adder(f0, epsilon), env);
		SineWaveOscillator osc2 = new SineWaveOscillator(new Adder(new Gain(4f,
				f0), epsilon), env);
		
		Adder add = new Adder();
		add.plugin(osc1);
		add.plugin(osc2);
		add.plugin(f0);
		
		SineWaveOscillator oscOut = new SineWaveOscillator(add, env);
		
		setOut(oscOut);
	}

}
