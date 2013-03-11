package synthesis.midiPlayground.MidiInstruments;

import synthesis.midiPlayground.MidiAudioBlocks.MidiADSR;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAdder;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiConstant;
import synthesis.midiPlayground.MidiAudioBlocks.MidiGain;
import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;
import synthesis.midiPlayground.MidiAudioBlocks.NoteBlock;

public class MidiBellInstrument extends MidiInstrument {
	
	//default values for the ADSR
	public MidiBellInstrument(){
		super();
	}
	
	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		MidiADSR amp = new MidiADSR(new MidiConstant(0.002f),new MidiConstant(0.998f),new MidiConstant(0f),new MidiConstant(0f));
		MidiSineWaveOscillator osc1 = new MidiSineWaveOscillator((MidiAudioBlock) fm, new MidiConstant(190));
		MidiAudioBlock fp = new MidiGain((float) (1/Math.sqrt(2)),(MidiAudioBlock) fm);
		MidiAdder adder = new MidiAdder(osc1,fp);
		return new MidiSineWaveOscillator(adder,amp);

	}
	
	

}
