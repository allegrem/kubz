package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

public class WoodInstrument implements FmInstrument {
	private final ParameterAudioBlock fm;
	//private final AudioBlock fp;
	private final ParameterAudioBlock amp;
	private final ParameterAudioBlock mod; 
	
	private AudioBlock out;

	private ArrayList<ParameterAudioBlock> paramList;
	
	
	public WoodInstrument() {
		//super();
		
		fm = new ParamBlock("fm", 290, 400, 300); //recommended value:300Hz
		//fp = new Constant((float) (3*fm.getValue())); 
		amp = new ParamBlock("amp", 0, 120, 100);
		mod = new ParamBlock("mod", 1, 3, 2);  //recommended value:2
		
		out = buildInstrument();
		paramList = generateParamList();
	}

	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(fm);
		list.add(amp);
		list.add(mod);
		return list;
	}

	private AudioBlock buildInstrument() {
		AudioBlock fp = new Constant((float) (3*fm.getValue())); 
		
		FixedADSR env = new FixedADSR(0.3f,0.0f,1.0f,0.1f,1f);
		try {
			env.plugin(new Multiplier(mod, fm));
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		SineWaveOscillator osc1 = new SineWaveOscillator(fm, env);
		Adder add = new Adder(fp, osc1);
		//FixedADSR env = new FixedADSR(0.3f,0.0f,1.0f,0.1f,1f);
		try {
			env.plugin(amp);
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		return out = new SineWaveOscillator(add, env);
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
