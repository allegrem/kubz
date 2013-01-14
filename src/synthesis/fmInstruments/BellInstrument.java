package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

public class BellInstrument implements FmInstrument{
	
	private final float STEPS = 1000;
	
	private final ParameterAudioBlock fm;
	private final AudioBlock fp; //=1.4*fm
	private final ParameterAudioBlock a; 
	private final ParameterAudioBlock d;
	
	private AudioBlock out;
	private ArrayList<ParameterAudioBlock> paramList;

	public BellInstrument() {
		//super();
		
		fm = new ParamBlock("fm", 200, 700, 280);
		//fp = new ParamBlock("fp", (int) (1.4*fm.getValue()), 1.410000, 600);
		fp = new Constant((float) fm.getValue());
		a = new ParamBlock("a", 0, 50, 2);
		d = new ParamBlock("d", 800, 1000, 998);
		
		out = buildInstrument();
		paramList = generateParamList();
	}

	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(fm);
		//list.add(fp);
		list.add(amp);
		
		return list;
	}

	private AudioBlock buildInstrument() {
		SineWaveOscillator osc1 = new SineWaveOscillator(fm, new Multiplier(mod, fm));
		Adder add = new Adder(fp, osc1);
		return out = new SineWaveOscillator(add, amp);
	}

	
	
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
