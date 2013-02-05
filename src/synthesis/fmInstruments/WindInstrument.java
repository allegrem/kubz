package synthesis.fmInstruments;

import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.ADSR;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

public class WindInstrument extends FmInstrument{
	
	private final ParameterAudioBlock fm;
	private final ParameterAudioBlock amp;
	private final ParameterAudioBlock mod; 
	
	public WindInstrument(){
		fm = addParam(new ParamBlock("fm",300,900,600)); //RECOM val = 600Hz
		amp = addParam(new ParamBlock("amp",0,120,100));
		mod = addParam(new ParamBlock("mod",2,10,4)); //RECOM val=4		
		
		ADSR env1 = new ADSR(new Constant(0.3f), new Constant(0.2f), new Constant(0.8f), new Constant(0.1f), 1f, amp);		
		Gain fp = new Gain(1.5f, fm);
		Adder adder1 = new Adder(new Gain((float) mod.getValue(),fm),env1);
		SineWaveOscillator osc1 = new SineWaveOscillator(fm,adder1);
		Adder adder2 = new Adder(fp,osc1);
		SineWaveOscillator out = new SineWaveOscillator(adder2,env1);
		setOut(out);
	}

}
