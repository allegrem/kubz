package midisynthesis.instruments;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.EffectBlock;
import midisynthesis.audioblocks.Gain;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;

public class SpaceGun extends Instrument {

	public SpaceGun() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		MidiAudioBlock f = new NoteBlock();
		SineOscillator osc1 = new SineOscillator(new EffectBlock(0.5f, 1f/127f), new Gain(3f, f));
		MidiAudioBlock adsr = new ADSR(new Constant(0.1f), new Constant(0.1f),
				new Constant(0.1f), new EffectBlock(0.05f, 0.08f/127f));
		MidiAudioBlock out = new SineOscillator(new Adder(f, osc1), adsr);
		return out;
	}

}
