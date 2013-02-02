package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This class creates the sound of bell.
 * @author valeh
 *
 */
public class BellInstrument implements FmInstrument{
	
	private final float STEPS = 1000;  //lets define the parameters on a scale of 1 to 1000
	
	private final ParameterAudioBlock fm;	
	private final ParameterAudioBlock a; 
	private final ParameterAudioBlock d;
	
	private AudioBlock out;
	private ArrayList<ParameterAudioBlock> paramList;
	
	/**
	 * Constructs a bell with fm, attack and decay values 
	 * as controllable parameters.
	 */
	public BellInstrument() {
		super();
		
		fm = new ParamBlock("fm", 70, 500, 280);
		a = new ParamBlock("a", 0, 50, 2);
		d = new ParamBlock("d", 800, 1000, 998);
		
		out = buildInstrument();
		paramList = generateParamList();
	}

	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(fm);
		list.add(a);
		list.add(d);

		return list;
	}
	
	/**
	 * Creates the bell out of the values of the
	 * different parameters.
	 * @return the resulting AudioBlock
	 */
	private AudioBlock buildInstrument() {
		FixedADSR env = new FixedADSR(a.getValue()/STEPS,d.getValue()/STEPS,0.0f,0.0f,3f);
		Gain fp = new Gain((float) (1/Math.sqrt(2))); 
		try {
			fp.plugin(fm);
		} catch (TooManyInputsException e1) {
			e1.printStackTrace();
		}
		try {
			env.plugin(new Constant((float) 100));
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		SineWaveOscillator osc1 = new SineWaveOscillator(fm, new Constant((float) 190));
		Adder add = new Adder(fp, osc1);
		return out = new SineWaveOscillator(add, env);
	}

	/**
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */	 
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		AudioBlock out = buildInstrument();
		return out.play(t);
	}
	
	/**
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		AudioBlock out = buildInstrument();
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
