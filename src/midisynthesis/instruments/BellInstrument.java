package midisynthesis.instruments;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.Gain;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;

public class BellInstrument extends Instrument {
	
	//default values for the ADSR
	public BellInstrument(){
		super();
	}
	
	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		ADSR amp = new ADSR(new Constant(0.002f),new Constant(0.998f),new Constant(0f),new Constant(0f));
		SineOscillator osc1 = new SineOscillator((MidiAudioBlock) fm, new Constant(190));
		MidiAudioBlock fp = new Gain((float) (1/Math.sqrt(2)),(MidiAudioBlock) fm);
		Adder adder = new Adder(osc1,fp);
		return new SineOscillator(adder,amp);

	}

}
