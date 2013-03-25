package midisynthesis.instruments;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Adder;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.EffectBlock;
import midisynthesis.audioblocks.Gain;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;

//This is not a TwoOscFm but only the oscillator 

public class GhostSinus extends Instrument {

	private ADSR amp;

	/**
	 * Create a new TwoOscFmInstrument.
	 */
	public GhostSinus() {
		super();
	}

	protected MidiAudioBlock buildInstrument() {
		MidiAudioBlock fm = new NoteBlock();
		MidiAudioBlock vibr = new SineOscillator(new EffectBlock(0.01f,
				20f / 127f), new Gain(0.01f, fm));
		amp = new ADSR(new Constant(0.3f), new Constant(0.3f), new Constant(
				0.5f), new Constant(1f));
		return new SineOscillator(new Adder(fm, vibr), amp);

	}

}
