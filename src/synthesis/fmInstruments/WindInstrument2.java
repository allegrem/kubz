package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.ADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

public class WindInstrument2 implements FmInstrument{
	
	
	private final ParameterAudioBlock fm;
	private final ParameterAudioBlock amp;
	private final ParameterAudioBlock mod; 
	
	private AudioBlock out;
	private ArrayList<ParameterAudioBlock> paramList;
	
	public WindInstrument2(){
		fm = new ParamBlock("fm",300,900,600); //RECOM val = 600Hz
		amp = new ParamBlock("amp",0,120,100);
		mod = new ParamBlock("mod",2,10,4); //RECOM val=4		
		
		out = buildInstrument();
		paramList = generateParamList();
	}	

	private AudioBlock buildInstrument(){
		ADSR env1 = new ADSR(new Constant(0.3f), new Constant(0.2f), new Constant(0.8f), new Constant(0.1f), 1f, amp);		
		Gain fp = new Gain(1.5f, fm);
		Adder adder1 = new Adder(new Gain((float) mod.getValue(),fm),env1);
		SineWaveOscillator osc1 = new SineWaveOscillator(fm,adder1);
		Adder adder2 = new Adder(fp,osc1);
		SineWaveOscillator out = new SineWaveOscillator(adder2,env1);
		return out;
	}
	
	private ArrayList<ParameterAudioBlock> generateParamList() {
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		list.add(fm);
		list.add(mod);
		list.add(amp);
		return list;		
	}

	
	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {
		return paramList;
	}
	
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return out.play(t);
	}

	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return out.phi(t);
	}


	

}
