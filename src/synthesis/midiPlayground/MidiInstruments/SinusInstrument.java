package synthesis.midiPlayground.MidiInstruments;

import synthesis.midiPlayground.MidiAudioBlocks.MidiADSR;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiConstant;
import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;
import synthesis.midiPlayground.MidiAudioBlocks.NoteBlock;

//This is not a TwoOscFm but only the oscillator 

public class SinusInstrument extends MidiInstrument {

	private MidiADSR amp;

	/**
	 * Create a new TwoOscFmInstrument.
	 */
	public SinusInstrument() {
		super();
	}

	
	protected MidiAudioBlock buildInstrument() {
			NoteBlock fm = new NoteBlock();
			amp = new MidiADSR(new MidiConstant(0.3f), new MidiConstant(0.3f), new MidiConstant(0.5f), new MidiConstant(1f));
			return new MidiSineWaveOscillator((MidiAudioBlock) fm, amp);
		
	}

	
	

}
