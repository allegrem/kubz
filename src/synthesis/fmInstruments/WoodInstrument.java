package synthesis.fmInstruments;

import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.ADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.parameter.GainParamBlock;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

public class WoodInstrument extends FmInstrument {

	private final ParameterAudioBlock fm;
	private final ParameterAudioBlock amp;
	private final ParameterAudioBlock mod;
	private final ParameterAudioBlock vibrGainFactor;
	private final ParameterAudioBlock vibrFreq;
	private final ParameterAudioBlock a;

	public WoodInstrument() {
		super();

		fm = addParam(new ParamBlock("fm", 20, 1500, 440));
		amp = addParam(new ParamBlock("amp", 0, 120, 100));
		mod = addParam(new ParamBlock("mod", 1, 10, 2));
		vibrGainFactor = addParam(new GainParamBlock("vibr amp", 0, 30, 5,
				0.001f));
		vibrFreq = addParam(new ParamBlock("vibr freq", 0, 10, 5));
		a = addParam(new GainParamBlock("attack", 0, 100, 30, 0.01f));

		Adder vibrato = new Adder(fm, new SineWaveOscillator(vibrFreq,
				new Multiplier(fm, vibrGainFactor)));
		Gain fp = new Gain(3f, vibrato);
		SineWaveOscillator osc1 = new SineWaveOscillator(vibrato,
				new Multiplier(mod, fm));
		ADSR env1 = new ADSR(a, new Constant(0.2f), new Constant(0.8f),
				new Constant(0.1f), 1f, amp);
		setOut(new SineWaveOscillator(new Adder(fp, osc1), env1));
	}

}
