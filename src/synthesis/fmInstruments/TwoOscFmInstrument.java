package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.RequireAudioBlocksException;
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
public class TwoOscFmInstrument implements FmInstrument {

	private final ParameterAudioBlock fm;

	private final ParameterAudioBlock fp;

	private final ParameterAudioBlock amp;

	private final ParameterAudioBlock mod;

	private AudioBlock out;

	private ArrayList<ParameterAudioBlock> paramList;

	/**
	 * Create a new TwoOscFmInstrument.
	 */
	public TwoOscFmInstrument() {
		super();

		fm = new ParamBlock("fm", 1, 10000, 440);
		fp = new ParamBlock("fp", 20, 10000, 600);
		amp = new ParamBlock("amp", 0, 120, 100);
		mod = new ParamBlock("mod", 0, 500, 10);

		out = buildInstrument();
		paramList = generateParamList();
	}

	/**
	 * Generate the list of the parameters (should be called only once in the
	 * constructor).
	 */
	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(fm);
		list.add(fp);
		list.add(amp);
		list.add(mod);
		return list;
	}

	/**
	 * Build the instrument (should be called only once in the constructor).
	 * 
	 * @return the out AudioBlock (which should be called to play the sound)
	 */
	private AudioBlock buildInstrument() {
		SineWaveOscillator osc1 = new SineWaveOscillator(fm, new Multiplier(
				mod, fm));
		Adder add = new Adder(fp, osc1);
		return out = new SineWaveOscillator(add, amp);
	}

	/**
	 * Play the sound of the instrument. The sound produced is given by : s(t) =
	 * amp * cos(2 * pi * fp * t + mod * sin(2 * pi * fm * t))
	 * 
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return out.play(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return out.phi(t);
	}

	/**
	 * Return the list of the parameters : the carrier frequency (fp), the
	 * modulation frequency (fm), the amplitude (amp), and the modulation index
	 * (mod).
	 */
	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {
		return paramList;
	}

}
