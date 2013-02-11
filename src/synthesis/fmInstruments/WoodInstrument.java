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

/**
 * This class creates the sound of a WindInstrument (typically violin) with
 * controllable vibrato factor.
 * 
 * @author valeh
 * 
 */
public class WoodInstrument extends FmInstrumentNParams {

	private final ParameterAudioBlock fm;
	private final ParameterAudioBlock amp;
	private final ParameterAudioBlock mod;
	private final ParameterAudioBlock vibrGainFactor;
	private final ParameterAudioBlock vibrFreq;
	private final ParameterAudioBlock a;

	/**
	 * Constructs a wood instrument with the different controllable parameters.
	 */
	public WoodInstrument() {
		super();

		fm = addParam(new ParamBlock("fm", 20, 1500, 440));
		amp = addParam(new ParamBlock("amp", 0, 120, 100));
		mod = addParam(new ParamBlock("mod", 1, 10, 2));
		vibrGainFactor = addParam(new GainParamBlock("vibr amp", 0, 30, 5,
				0.001f));
		vibrFreq = addParam(new ParamBlock("vibr freq", 0, 10, 5));
		a = addParam(new GainParamBlock("attack", 0, 100, 30, 0.01f));

		Adder vibrato = new Adder(getFm(), new SineWaveOscillator(
				getVibrFreq(), new Multiplier(getFm(), vibrGainFactor)));
		// Vibrato vibrato = new Vibrato((float)
		// (vibrGainFactor.getValue()*0.001),vibrFreq.getValue());
		// vibrato.plugin(fm);
		Gain fp = new Gain(3f, getFm());
		SineWaveOscillator osc1 = new SineWaveOscillator(vibrato,
				new Multiplier(getMod(), getFm()));

		// modulating the amplitude with a vibrato synchronized with the one for
		// the frequency,the result's odd!
		// Adder ampVibrato = new Adder(amp, new SineWaveOscillator(vibrFreq,new
		// Multiplier(amp,vibrGainFactor)));
		ADSR env1 = new ADSR(a, new Constant(0.2f), new Constant(0.8f),
				new Constant(0.1f), 3f, amp);
		setOut(new SineWaveOscillator(new Adder(fp, osc1), env1));
	}

	public static FmInstruments3Params getFmInstruments3Params() {
		WoodInstrument instrument = new WoodInstrument();
		return new FmInstruments3Params(instrument, instrument.getFm(),
				instrument.getVibrFreq(), instrument.getMod());
	}

	public ParameterAudioBlock getFm() {
		return fm;
	}

	public ParameterAudioBlock getMod() {
		return mod;
	}

	public ParameterAudioBlock getVibrFreq() {
		return vibrFreq;
	}

}
