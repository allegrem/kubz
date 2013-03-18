package synthesis.midiPlayground.MidiInstruments;

import synthesis.midiPlayground.MidiInstruments.MidiInstrument;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAdder;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiConstant;
import synthesis.midiPlayground.MidiAudioBlocks.MidiGain;
import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;
import synthesis.midiPlayground.MidiAudioBlocks.NoteBlock;


public class MidiTwoOscInstrument extends MidiInstrument {

	// default values for mod(2),fp(2*fm) and amp(100)

	public MidiTwoOscInstrument() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		MidiAudioBlock fp = new MidiGain(2f, (MidiAudioBlock) fm);
		MidiSineWaveOscillator osc1 = new MidiSineWaveOscillator(
				(MidiAudioBlock) fm, new MidiGain(2f, (MidiAudioBlock) fm));		
		MidiAudioBlock out = new MidiSineWaveOscillator(new MidiAdder(
				fp, osc1), new MidiConstant(
				100f));
		/*MidiSineWaveOscillator osc1 = new MidiSineWaveOscillator(new MidiConstant(1f), new MidiConstant(50f));
		MidiSineWaveOscillator out = new MidiSineWaveOscillator(new MidiAdder(fp, osc1), new MidiConstant(100f));*/
		return out;
	}

}
