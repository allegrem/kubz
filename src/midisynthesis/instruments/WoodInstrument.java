package midisynthesis.instruments;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.Gain;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;
import midisynthesis.instruments.Instrument;

public class WoodInstrument extends Instrument {
	//default values for vibrato(a=0.5%,fv=7Hz)
	//for ADSR and for mod(2)
	
	public WoodInstrument() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		MidiAudioBlock fp = new Gain(3f, (MidiAudioBlock) fm);
		//vibrato
		Adder vibrato = new Adder((MidiAudioBlock) fm,
				new SineOscillator(new Constant(7f), new Gain(0.005f,
						(MidiAudioBlock) fm)));

		SineOscillator osc1 = new SineOscillator(new Gain(2f,(MidiAudioBlock) fm),vibrato);
		//values imposed this time since the user cannot choose them anymore
		ADSR amp = new ADSR(new Constant(0.3f),new Constant(0.2f),new Constant(0.8f),new Constant(0.1f));
		MidiAudioBlock out = new SineOscillator(new Adder(fp,osc1),amp);
		return out;
		
	}

}
