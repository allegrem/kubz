package synthesis.midiPlayground.MidiInstruments;

import synthesis.midiPlayground.NoteBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiADSR;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAdder;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiConstant;
import synthesis.midiPlayground.MidiAudioBlocks.MidiMultiplier;
import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;

public class MidiTwoOscInstrument extends MidiInstrument{
	
	private MidiADSR amp;
	private NoteBlock fm;
	private NoteBlock fp;
	private MidiConstant mod;	
	
	public MidiTwoOscInstrument(){
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		MidiSineWaveOscillator osc1 = new MidiSineWaveOscillator(fm, new MidiMultiplier(fm,mod));
		MidiAudioBlock out = new MidiSineWaveOscillator(new MidiAdder(fp,osc1),amp);		
		return out;
	}
	
	
}
