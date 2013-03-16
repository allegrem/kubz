package synthesis.midiPlayground.MidiInstruments;

import synthesis.midiPlayground.MidiInstruments.MidiInstrument;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAdder;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiConstant;
import synthesis.midiPlayground.MidiAudioBlocks.MidiGain;
import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;
import synthesis.midiPlayground.MidiAudioBlocks.NoteBlock;
import synthesis.midiPlayground.MidiAudioBlocks.VelocityBlock;

//Actually, once we can no more really choose fp, fm and mod separately, this class doesn't seem so useful!
public class MidiTwoOscInstrument extends MidiInstrument {

	// default values for mod(2),fp(2*fm) and amp(100)

	public MidiTwoOscInstrument() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		MidiAudioBlock fm = new NoteBlock();
		MidiSineWaveOscillator osc1 = new MidiSineWaveOscillator(fm,
				new MidiGain(2f, fm));
		MidiAudioBlock out = new MidiSineWaveOscillator(new MidiAdder(
				new MidiGain(2f, fm), osc1), new VelocityBlock());
		return out;
	}
}
