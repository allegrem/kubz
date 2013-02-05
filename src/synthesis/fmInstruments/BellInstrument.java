package synthesis.fmInstruments;

import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.TooManyInputsException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This class creates the sound of bell.
 * @author valeh
 *
 */
public class BellInstrument extends FmInstrument {
	
	private final float STEPS = 1000;  //lets define the parameters on a scale of 1 to 1000
	private final ParameterAudioBlock fm;
	private final ParameterAudioBlock a;
	private final ParameterAudioBlock d;
	
	/**
	 * Constructs a bell with fm, attack and decay values 
	 * as controllable parameters.
	 */
	public BellInstrument() {
		super();
		
		fm = addParam(new ParamBlock("fm", 70, 500, 280));
		a = addParam(new ParamBlock("a", 0, 50, 2));
		d = addParam(new ParamBlock("d", 800, 1000, 998));
		
		FixedADSR env = new FixedADSR(a.getValue()/STEPS,d.getValue()/STEPS,0.0f,0.0f,3f);
		Gain fp = new Gain((float) (1/Math.sqrt(2))); 
		try {
			fp.plugin(fm);
			env.plugin(new Constant((float) 100));
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
		SineWaveOscillator osc1 = new SineWaveOscillator(fm, new Constant((float) 190));
		Adder add = new Adder(fp, osc1);
		setOut(new SineWaveOscillator(add, env));
	}

}
