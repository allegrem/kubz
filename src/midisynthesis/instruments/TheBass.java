package midisynthesis.instruments;

import org.apache.log4j.ConsoleAppender;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.Gain;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;

public class TheBass extends Instrument {

	public TheBass() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		MidiAudioBlock f = new Gain(0.25f, new NoteBlock());
		MidiAudioBlock vibr = new SineOscillator(new Constant(10f), new Gain(0.01f, f));
		
		MidiAudioBlock fp = new Gain(3f, f);
		SineOscillator osc1 = new SineOscillator(new Adder(f,vibr), fp);
		ADSR adsr = new ADSR(new Constant(0.01f), new Constant(0.1f),
				new Constant(0.8f), new Constant(0.3f));
		MidiAudioBlock out = new SineOscillator(new Adder(fp, osc1),
				adsr);
		return out;
	}

}
