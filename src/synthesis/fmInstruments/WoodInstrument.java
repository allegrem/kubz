package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.ADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;
import synthesis.parameter.GainParamBlock;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This class creates a WoodInstrument (typically violin).
 * @author : valeh 
 */
public class WoodInstrument implements FmInstrument {

	private final ParameterAudioBlock fm;

	private final ParameterAudioBlock amp;

	private final ParameterAudioBlock mod;

	private final ParameterAudioBlock vibrGainFactor;

	private final ParameterAudioBlock vibrFreq;

	private final ParameterAudioBlock a;

	private AudioBlock out;

	private ArrayList<ParameterAudioBlock> paramList;
	
	/**
	 * Constructs a wood instrument with the 
	 * different controllable parameters.
	 */
	public WoodInstrument() {
		super();

		fm = new ParamBlock("fm", 20, 1500, 440);
		amp = new ParamBlock("amp", 0, 120, 100);
		mod = new ParamBlock("mod", 1, 10, 2); // recommended value:2
		vibrGainFactor = new GainParamBlock("vibr amp", 0, 30, 5, 0.001f);
		vibrFreq = new ParamBlock("vibr freq", 0, 10, 5);
		a = new GainParamBlock("attack", 0, 100, 30, 0.01f);

		try {
			out = buildInstrument();
		} catch (TooManyInputsException e) {			
			e.printStackTrace();
		}
		paramList = generateParamList();
	}

	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(fm);
		list.add(amp);
		list.add(mod);
		list.add(vibrGainFactor);
		list.add(vibrFreq);
		list.add(a);
		return list;
	}

	/**
	 * Creates the instrument out of the values of the
	 * different parameters.
	 * @return the resulting AudioBlock
	 */
	private AudioBlock buildInstrument() throws TooManyInputsException {
		//Add vibrato factor
		Adder vibrato = new Adder(fm, new SineWaveOscillator(vibrFreq,
				new Multiplier(fm, vibrGainFactor)));
		Gain fp = new Gain(3f, vibrato);
		SineWaveOscillator osc1 = new SineWaveOscillator(vibrato,
				new Multiplier(mod, fm));
		ADSR env1 = new ADSR(a, new Constant(0.2f), new Constant(0.8f), new Constant(0.1f), 1f, amp);
		return out = new SineWaveOscillator(new Adder(fp, osc1), env1);
	}

	/**
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */	
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return out.play(t);
	}

	/**
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */	
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return out.phi(t);
	}
	
	/**
	 * @see synthesis.FmInstrument#getParameters
	 */
	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {
		return paramList;
	}

}
