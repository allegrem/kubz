package synthesis.midiPlayground.MidiInstruments;

import synthesis.midiPlayground.NoteBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiADSR;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAdder;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiConstant;
import synthesis.midiPlayground.MidiAudioBlocks.MidiGain;
import synthesis.midiPlayground.MidiAudioBlocks.MidiMultiplier;
import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;

//Actually, sonce we can no more really choose fp, fm and mod separately, this class doesn't seem so useful!
public class MidiTwoOscInstrument extends MidiInstrument {

	// default values for mod(2),fp(2*fm) and amp(100)

	public MidiTwoOscInstrument() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		MidiSineWaveOscillator osc1 = new MidiSineWaveOscillator(
				(MidiAudioBlock) fm, new MidiGain(2f, (MidiAudioBlock) fm));
		MidiAudioBlock out = new MidiSineWaveOscillator(new MidiAdder(
				new MidiGain(2f, (MidiAudioBlock) fm), osc1), new MidiConstant(
				100f));
		return out;
	}

}
