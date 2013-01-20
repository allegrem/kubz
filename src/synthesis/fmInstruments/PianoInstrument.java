package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.WhiteNoise;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.oneinputblocks.OneInputBlock;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

public class PianoInstrument implements FmInstrument{
	private static final int STEPS = 1000;
	private final ParameterAudioBlock f0;
	private final ParameterAudioBlock epsilon;
	private final ParameterAudioBlock a1;
	private final ParameterAudioBlock d1;
	private final ParameterAudioBlock a2;
	private final ParameterAudioBlock d2;
	private final ParameterAudioBlock amp;
	
	
	private final AudioBlock noise;
	private final AudioBlock env1;
	private final AudioBlock env2;
	private final AudioBlock sigma;
	
	
	private AudioBlock out; 
	private ArrayList<ParameterAudioBlock> paramList;
	
	public PianoInstrument(){
		f0 = new ParamBlock("f0", 100, 300, 110);
		epsilon = new ParamBlock("epsilon",35,50,48);
		a1 = new ParamBlock("a1", 0,100,0);
		d1 = new ParamBlock("d1",560,1000,580);
		
		a2 = new ParamBlock("a2", 0, 50, 2);
		d2 = new ParamBlock("d", 800, 1000, 998);
		
		amp = new ParamBlock("amp", 0, 120, 100);
		sigma = new Gain(10.0f);
		
		noise = new WhiteNoise();
		env1 = new FixedADSR(a1.getValue()/STEPS,d1.getValue()/STEPS,0,23/STEPS,1f);
		env2 = new FixedADSR(a2.getValue()/STEPS,d2.getValue()/STEPS,0.0f,0.0f,1f); 
		out = buildInstrument();
	}
	
	private ArrayList<ParameterAudioBlock> generateParamList(){
		ArrayList<ParameterAudioBlock> list = new ArrayList<ParameterAudioBlock>();
		
		list.add(f0);
		list.add(a1);
		list.add(d1);
		list.add(a2);
		list.add(d2);
		list.add(epsilon);
		list.add(amp);

		return list;
		
	}
	
	private AudioBlock buildInstrument(){
		try {
			((OneInputBlock) env1).plugin((AudioBlock)amp);
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		try {
			((OneInputBlock) sigma).plugin(noise);
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		AudioBlock plugin1 = new Multiplier(env1,sigma);
		
		try {
			((OneInputBlock) env2).plugin(amp);
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		SineWaveOscillator osc1 = new SineWaveOscillator(env2,new Constant((float) (f0.getValue()+epsilon.getValue())));
		SineWaveOscillator osc2 = new SineWaveOscillator(env2,new Constant((float) (4*f0.getValue()+epsilon.getValue())));
		Adder adder = new Adder(osc1,osc2);
		adder.plugin(new Constant((float) f0.getValue()));
		
		SineWaveOscillator osc3 = new SineWaveOscillator(adder,env2);
		
		return new Adder(osc3,plugin1);
	
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
