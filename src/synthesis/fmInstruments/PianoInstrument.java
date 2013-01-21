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
	
	
	
	private AudioBlock out; 
	private ArrayList<ParameterAudioBlock> paramList;
	
	public PianoInstrument(){
		f0 = new ParamBlock("f0", 100, 500, 440);
		epsilon = new ParamBlock("epsilon",5,50,10);
		a1 = new ParamBlock("a1", 0,100,0);
		d1 = new ParamBlock("d1",560,1000,580);
		
		a2 = new ParamBlock("a2", 0, 50, 2);
		d2 = new ParamBlock("d", 800, 1000, 998);
		
		amp = new ParamBlock("amp", 0, 120, 100);
		
		out = buildInstrument();
		paramList = generateParamList();
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
		WhiteNoise noise = new WhiteNoise();
		FixedADSR env1 = new FixedADSR(a1.getValue()/STEPS,d1.getValue()/STEPS,0,23/STEPS,1f);
		FixedADSR env2 = new FixedADSR(a2.getValue()/STEPS,d2.getValue()/STEPS,0.09f,0.0f,1f); 
		Gain sigma = new Gain(10.0f);
		
		try {
			env1.plugin(new Constant((float) amp.getValue()));
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		try {
			sigma.plugin(noise);
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		Multiplier plugin1 = new Multiplier(env1,sigma);
		
		try {
			env2.plugin(new Constant((float) amp.getValue()));
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		SineWaveOscillator osc1 = new SineWaveOscillator(new Constant((float) (f0.getValue()+epsilon.getValue())),env2);
		SineWaveOscillator osc2 = new SineWaveOscillator(new Constant((float) (4*f0.getValue()+epsilon.getValue())),env2);
	
		Adder adder = new Adder(osc1,osc2);
		adder.plugin(new Constant((float) f0.getValue()));
		
		
		SineWaveOscillator osc3 = new SineWaveOscillator(adder,env2);
		
		
		return out = new Adder(osc3,plugin1);
	
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
