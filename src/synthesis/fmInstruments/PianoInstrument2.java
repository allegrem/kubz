package synthesis.fmInstruments;

import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.ADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.parameter.GainParamBlock;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This class creates the sound of a piano (without the WhiteNoise).
 * 
 * @author allegrem
 * 
 */
public class PianoInstrument2 extends FmInstrumentNParams {

	private ParameterAudioBlock a;
	private ParameterAudioBlock f0;
	private ParameterAudioBlock epsilon;
	private ParameterAudioBlock amp;
	private ParameterAudioBlock d;
	private ParameterAudioBlock s;
	private ParameterAudioBlock r;
	private ParameterAudioBlock detune;

	public PianoInstrument2() {
		super();

		a = addParam(new GainParamBlock("attack", 0, 100, 1, 0.01f));
		d = addParam(new GainParamBlock("decay", 0, 100, 10, 0.01f));
		s = addParam(new GainParamBlock("sustain", 0, 100, 60, 0.01f));
		r = addParam(new GainParamBlock("release", 0, 100, 80, 0.01f));
		f0 = addParam(new ParamBlock("f0", 20, 5000, 440));
		epsilon = addParam(new GainParamBlock("epsilon", 0, 100, 10, 0.1f));
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
		add.plugin(new Adder(getDetune(), getF0()));

		SineWaveOscillator oscOut = new SineWaveOscillator(add, env);

		setOut(oscOut);
	}

	public static FmInstruments3Params getFmInstruments3Params() {
		PianoInstrument2 instrument = new PianoInstrument2();
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
