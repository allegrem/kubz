package midisynthesis.instruments;

import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.EffectBlock;
import midisynthesis.audioblocks.Gain;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;
import midisynthesis.audioblocks.VelocityBlock;
import midisynthesis.instruments.Instrument;

//Actually, once we can no more really choose fp, fm and mod separately, this class doesn't seem so useful!
public class TwoOscInstrument extends Instrument {

	// default values for mod(2),fp(2*fm) and amp(100)

	public TwoOscInstrument() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		MidiAudioBlock fm = new NoteBlock();
		MidiAudioBlock fp = new Gain(new EffectBlock(0, 10f/127f), fm);
		SineOscillator osc1 = new SineOscillator(fm, fp);
		MidiAudioBlock out = new SineOscillator(
				new Adder(fp, osc1), new VelocityBlock());
		return out;
	}
}
