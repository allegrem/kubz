package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

public class TwoOscFmInstrumentBis implements FmInstrument{
	 private ParameterAudioBlock fp; 
	 private ParameterAudioBlock mod;
	 private ParameterAudioBlock amp;
	 
	 private AudioBlock out;
	 private ArrayList<ParameterAudioBlock> paramList;
	 
	 public TwoOscFmInstrumentBis(){
		fp = new ParamBlock("fp",20,1000,500);
		amp = new ParamBlock("amp", 0, 120, 100);
		mod = new ParamBlock("mod",30,500,50);
		
		out = buildInstrument();
		paramList = generateParamList();
	 }
	 
	 private ArrayList<ParameterAudioBlock> generateParamList(){
		 ArrayList<ParameterAudioBlock> paramList = new ArrayList<ParameterAudioBlock>();
		 paramList.add(fp);
		 paramList.add(mod);
		 paramList.add(amp);
		 return paramList;
	 }
	 
	 private AudioBlock buildInstrument(){
		 SineWaveOscillator osc1 = new SineWaveOscillator(new Constant(1f),mod);
		 Adder adder = new Adder(osc1,fp);
		 out = new SineWaveOscillator(adder,amp);
		 return out;
	 }
	

	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return out.play(t);
	}

	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {	
		return out.phi(t);
	}

	@Override
	public ArrayList<ParameterAudioBlock> getParameters() {		
		return paramList;
	}
	 
	
	 
	

}
