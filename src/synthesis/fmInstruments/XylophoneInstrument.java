package synthesis.fmInstruments;

import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.ADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.parameter.GainParamBlock;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This class creates the sound of an xylophone.
 * 
 * @author allegrem
 * 
 */
public class XylophoneInstrument extends FmInstrumentNParams {

	private final ParameterAudioBlock a;
	private final ParameterAudioBlock f0;
	private final ParameterAudioBlock epsilon;
	private final ParameterAudioBlock amp;
	private final ParameterAudioBlock d;
	private final ParameterAudioBlock s;
	private final ParameterAudioBlock r;
	private final ParameterAudioBlock detune;

	public XylophoneInstrument() {
		super();

		a = addParam(new GainParamBlock("attack", 0, 100, 1, 0.01f));
		d = addParam(new GainParamBlock("decay", 0, 100, 10, 0.01f));
		s = addParam(new GainParamBlock("sustain", 0, 100, 60, 0.01f));
		r = addParam(new GainParamBlock("release", 0, 100, 80, 0.01f));
		f0 = addParam(new ParamBlock("f0", 20, 5000, 440));
		epsilon = addParam(new GainParamBlock("epsilon", 0, 1000, 250, 1f));
		amp = addParam(new ParamBlock("amp", 0, 125, 100));
		detune = addParam(new GainParamBlock("detune", 0, 100, 50, 0.1f));

		ADSR env = new ADSR(a, d, s, r, 3f, amp);

		SineWaveOscillator osc1 = new SineWaveOscillator(new Adder(getF0(),
				getEpsilon()), env);
		SineWaveOscillator osc2 = new SineWaveOscillator(new Adder(new Gain(4f,
				getF0()), getEpsilon()), env);

		Adder add = new Adder();
		add.plugin(osc1);
		add.plugin(osc2);
		add.plugin(getF0());
		add.plugin(new Adder(getDetune(), getF0()));

		SineWaveOscillator oscOut = new SineWaveOscillator(add, env);

		setOut(oscOut);
	}

	public static FmInstruments3Params getFmInstruments3Params() {
		XylophoneInstrument instrument = new XylophoneInstrument();
		return new FmInstruments3Params(instrument, instrument.getF0(),
				instrument.getDetune(), instrument.getEpsilon());
	}

	public ParameterAudioBlock getF0() {
		return f0;
	}

	public ParameterAudioBlock getEpsilon() {
		return epsilon;
	}

	public ParameterAudioBlock getDetune() {
		return detune;
	}

}
