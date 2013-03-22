package midisynthesis.instruments;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.EffectBlock;
import midisynthesis.audioblocks.Gain;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;

public class Bell extends Instrument {

	public Bell() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		MidiAudioBlock fm = new NoteBlock();
		MidiAudioBlock amp = new Gain(0.8f, new ADSR(new Constant(0.002f),
				new Constant(0.02f), new EffectBlock(0.1f, 0.8f / 127f),
				new Constant(1f)));
		SineOscillator osc1 = new SineOscillator(fm, amp);
		MidiAudioBlock fp = new Gain((float) (1 / Math.sqrt(2)), fm);
		Adder adder = new Adder(osc1, fp);
		return new SineOscillator(adder, amp);
	}
}
