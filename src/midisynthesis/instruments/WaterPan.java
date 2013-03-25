package midisynthesis.instruments;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.EffectBlock;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;

public class WaterPan extends Instrument {

	public WaterPan() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		MidiAudioBlock f = new NoteBlock();
		SineOscillator osc1 = new SineOscillator(new EffectBlock(8f, 13f/127f),
				new Constant(100f));
		MidiAudioBlock adsr = new ADSR(new Constant(0.1f), new Constant(0.1f),
				new Constant(0.5f), new Constant(0.2f));
		MidiAudioBlock out = new SineOscillator(new Adder(f, osc1),
				adsr);
		return out;
	}

}
