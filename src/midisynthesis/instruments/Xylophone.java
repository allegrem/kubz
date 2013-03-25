package midisynthesis.instruments;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;

/**
 * @author valeh
 */
public class Xylophone extends Instrument{
	//default values for ADSR, epsilon(250),detune(5)
	public Xylophone() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		
		MidiAudioBlock f0 = new NoteBlock();
		
		ADSR env = new ADSR(new Constant(0.01f),new Constant(0.1f),new Constant(0.6f),new Constant(0.8f));				
		Constant epsilon = new Constant(250f);
		Constant detune = new Constant(5f);
		
		SineOscillator osc1 = new SineOscillator(new Adder(f0,epsilon),env);
		SineOscillator osc2 = new SineOscillator(new Adder(new Constant(4*400f),epsilon), env);
		
		//OK it's ugly but as far as it works,it doesn't matter XD
		Adder adder = new Adder(osc1,osc2);
		Adder adder2 = new Adder(adder,f0);
		Adder adder3 = new Adder(adder2,new Adder(detune,f0));
					
		SineOscillator oscOut = new SineOscillator(adder3, env);
		return oscOut;
	
	}
	

}
