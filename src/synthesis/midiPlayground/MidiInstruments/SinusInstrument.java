package synthesis.midiPlayground.MidiInstruments;

import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.midiPlayground.MidiADSR;
import synthesis.midiPlayground.MidiAudioBlock;
import synthesis.midiPlayground.MidiConstant;
import synthesis.midiPlayground.MidiInstrument;
import synthesis.midiPlayground.MidiSineWaveOscillator;
import synthesis.midiPlayground.NoteBlock;

public class SinusInstrument extends MidiInstrument {

	private MidiADSR amp;

	/**
	 * Create a new TwoOscFmInstrument.
	 */
	public SinusInstrument() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		amp = new MidiADSR(new MidiConstant(0.3f), new MidiConstant(0.3f), new MidiConstant(0.5f), new MidiConstant(1f));
		return new MidiSineWaveOscillator(fm, amp);
	}

}
