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

public class BellInstrument implements FmInstrument{
	
	private final float STEPS = 1000;
	
	private final ParameterAudioBlock fm;
	//private final Gain fp; //=1.4*fm
	private final ParameterAudioBlock a; 
	private final ParameterAudioBlock d;
	
	private AudioBlock out;
	private ArrayList<ParameterAudioBlock> paramList;

	public BellInstrument() {
		//super();
		
		fm = new ParamBlock("fm", 70, 500, 280);
		//fp = new ParamBlock("fp", (int) (1.4*fm.getValue()), 1.410000, 600);
		a = new ParamBlock("a", 0, 50, 2);
		d = new ParamBlock("d", 800, 1000, 998);
		
		out = buildInstrument();
		paramList = generateParamList();
	}

	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(fm);
		//list.add(fp);
		list.add(a);
		list.add(d);

		return list;
	}

	private AudioBlock buildInstrument() {
		FixedADSR env = new FixedADSR(a.getValue()/STEPS,d.getValue()/STEPS,0.0f,0.0f,1f);
		Gain fp = new Gain((float) (1/Math.sqrt(2))); 
		try {
			fp.plugin(fm);
		} catch (TooManyInputsException e1) {
			// TODO Auto-generated catch block
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

	
	
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		AudioBlock out = buildInstrument();
		return out.play(t);
	}

	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		AudioBlock out = buildInstrument();
		return out.phi(t);
	}

	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {
		return paramList;
	}
	

}
