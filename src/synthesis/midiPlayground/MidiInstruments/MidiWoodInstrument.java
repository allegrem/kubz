package synthesis.midiPlayground.MidiInstruments;

import synthesis.midiPlayground.NoteBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiADSR;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAdder;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiConstant;
import synthesis.midiPlayground.MidiAudioBlocks.MidiGain;
import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;

public class MidiWoodInstrument extends MidiInstrument {
	//default values for vibrato(a=0.5%,fv=7Hz)
	//for ADSR, for mod(2)
	
	public MidiWoodInstrument() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		NoteBlock fm = new NoteBlock();
		MidiAudioBlock fp = new MidiGain(3f, (MidiAudioBlock) fm);
		//vibrato
		MidiAdder vibrato = new MidiAdder((MidiAudioBlock) fm,
				new MidiSineWaveOscillator(new MidiConstant(7f), new MidiGain(0.005f,
						(MidiAudioBlock) fm)));

		MidiSineWaveOscillator osc1 = new MidiSineWaveOscillator(new MidiGain(2f,(MidiAudioBlock) fm),vibrato);
		//values imposed this time since the user cannot choose them anymore
		MidiADSR amp = new MidiADSR(new MidiConstant(0.3f),new MidiConstant(0.2f),new MidiConstant(0.8f),new MidiConstant(0.1f));
		MidiAudioBlock out = new MidiSineWaveOscillator(new MidiAdder(fp,osc1),amp);
		return out;
		
	}

}
