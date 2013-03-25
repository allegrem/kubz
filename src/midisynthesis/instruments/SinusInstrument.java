package midisynthesis.instruments;

import midisynthesis.audioblocks.ADSR;
import midisynthesis.audioblocks.Constant;
import midisynthesis.audioblocks.MidiAudioBlock;
import midisynthesis.audioblocks.NoteBlock;
import midisynthesis.audioblocks.SineOscillator;

public class SinusInstrument extends Instrument {

	private ADSR amp;

	/**
	 * Create a new TwoOscFmInstrument.
	 */
	public SinusInstrument() {
		super();
	}

	
	protected MidiAudioBlock buildInstrument() {
			NoteBlock fm = new NoteBlock();
			amp = new ADSR(new Constant(0.3f), new Constant(0.3f), new Constant(0.5f), new Constant(1f));
			return new SineOscillator((MidiAudioBlock) fm, amp);
		
	}

	
	

}
