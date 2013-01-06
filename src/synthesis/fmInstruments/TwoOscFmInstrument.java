package synthesis.fmInstruments;

import java.util.ArrayList;
import Parameter.ParamBlock;
import Parameter.ParameterAudioBlock;
import synthesis.AudioBlock;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * @author allegrem
 *
 */
public class TwoOscFmInstrument implements FmInstrument {
	
	private final ParameterAudioBlock fm;
	
	private final ParameterAudioBlock fp;
	
	private final ParameterAudioBlock amp;
	
	private final ParameterAudioBlock mod;
	
	private AudioBlock out;

	private ArrayList<ParameterAudioBlock> paramList;
	
	
	public TwoOscFmInstrument() {
		super();
		
		fm = new ParamBlock("fm", 20, 10000, 440);
		fp = new ParamBlock("fp", 20, 10000, 600);
		amp = new ParamBlock("amp", 0, 120, 100);
		mod = new ParamBlock("mod", 0, 100, 10);
		
		out = buildInstrument();
		paramList = generateParamList();
	}

	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(fm);
		list.add(fp);
		list.add(amp);
		list.add(mod);
		return list;
	}

	private AudioBlock buildInstrument() {
		SineWaveOscillator osc1 = new SineWaveOscillator(fm, mod);
		Adder add = new Adder(fp, osc1);
		return out = new SineWaveOscillator(add, amp);
	}

	/* (non-Javadoc)
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return out.play(t);
	}

	/* (non-Javadoc)
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return out.phi(t);
	}

	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {
		return paramList;
	}

}
