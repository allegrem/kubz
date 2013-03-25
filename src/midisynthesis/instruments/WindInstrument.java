package midisynthesis.instruments;

import midisynthesis.audioblocks.*;

public class WindInstrument extends Instrument {
	
	public WindInstrument(){
		super();	
	}
	
	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		MidiAudioBlock fp = new Gain(1.5f, fm);
		
		ADSR env1 = new ADSR(new Constant(0.3f), new Constant(0.2f), new Constant(0.8f), new Constant(0.1f));
		
		Adder adder = new Adder(env1,new Gain(4f,fm));
		SineOscillator osc1 = new SineOscillator(fm, adder);
		SineOscillator out = new SineOscillator(new Adder(fp,osc1), env1);			

	
		return out;
	}

	
}

