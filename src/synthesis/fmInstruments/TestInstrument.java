/**
 * 
 */
package synthesis.fmInstruments;

import java.util.ArrayList;
import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * @author allegrem
 * 
 */
public class TestInstrument implements FmInstrument {

	private final float STEPS = 100;

	private final ParameterAudioBlock a;

	private final ParameterAudioBlock d;

	private final ParameterAudioBlock s;

	private final ParameterAudioBlock r;
	
	private final ParameterAudioBlock f;

	private ArrayList<ParameterAudioBlock> paramList;

	public TestInstrument() {
		super();

		a = new ParamBlock("a", 0, (int) STEPS, 2);
		d = new ParamBlock("d", 0, (int) STEPS, 2);
		s = new ParamBlock("s", 0, (int) STEPS, 2);
		r = new ParamBlock("r", 0, (int) STEPS, 2);
		f = new ParamBlock("f", 50, 2000, 440);

		paramList = generateParamList();
	}

	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(a);
		list.add(d);
		list.add(s);
		list.add(r);
		list.add(f);
		return list;
	}

	private AudioBlock buildInstrument() {
		FixedSineWaveOscillator c = new FixedSineWaveOscillator((float) f.getValue(),120f);
		FixedADSR env = new FixedADSR(a.getValue() / STEPS, d.getValue()
				/ STEPS, s.getValue() / STEPS, r.getValue() / STEPS, 1f);
		try {
			env.plugin(c);
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		return env;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		AudioBlock out = buildInstrument();
		return out.play(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		AudioBlock out = buildInstrument();
		return out.phi(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.fmInstruments.FmInstrument#getParameters()
	 */
	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {
		return paramList;
	}

}
