package synthesis.fmInstruments;

import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.WhiteNoise;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.basicblocks.severalinputsblocks.Multiplier;
import synthesis.exceptions.TooManyInputsException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This class creates the sound of piano.
 * @author valeh
 *
 */
public class PianoInstrument extends FmInstrument{
	private static final int STEPS = 1000; //lets define the parameters on a scale of 1 to 1000
	//different params
	private final ParameterAudioBlock f0;
	private final ParameterAudioBlock epsilon;
	private final ParameterAudioBlock a1;
	private final ParameterAudioBlock d1;
	private final ParameterAudioBlock a2;
	private final ParameterAudioBlock d2;
	private final ParameterAudioBlock amp;
	
	/**
	 * Constructs a bell with the different 
	 * controllable parameters.
	 */
	public PianoInstrument() {
		f0 = addParam(new ParamBlock("f0", 100, 1000, 440));
		epsilon = addParam(new ParamBlock("epsilon",1,10,2));
		a1 = addParam(new ParamBlock("a1", 0,100,0));
		d1 = addParam(new ParamBlock("d1",560,1000,580));
		a2 = addParam(new ParamBlock("a2", 0, 50, 2));
		d2 = addParam(new ParamBlock("d", 800, 1000, 998));
		amp = addParam(new ParamBlock("amp", 0, 120, 100));

		WhiteNoise noise = new WhiteNoise();
		FixedADSR env1 = new FixedADSR(a1.getValue()/STEPS,d1.getValue()/STEPS,0,0,3f);
		FixedADSR env2 = new FixedADSR(a2.getValue()/STEPS,d2.getValue()/STEPS,0.9f,0,3f); 
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
		
		setOut(new Adder(osc3,plugin1));
	}

}
