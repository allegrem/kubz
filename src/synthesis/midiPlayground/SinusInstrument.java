package synthesis.midiPlayground;

public class SinusInstrument extends MidiInstrument {

	private VelocityBlock amp;

	/**
	 * Create a new TwoOscFmInstrument.
	 */
	public SinusInstrument() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		amp = new VelocityBlock();
		return new MidiSineWaveOscillator(fm, amp);
	}

}
