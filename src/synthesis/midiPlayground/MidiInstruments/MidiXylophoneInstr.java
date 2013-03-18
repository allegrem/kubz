package synthesis.midiPlayground.MidiInstruments;

import synthesis.midiPlayground.MidiAudioBlocks.MidiADSR;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAdder;
import synthesis.midiPlayground.MidiAudioBlocks.MidiAudioBlock;
import synthesis.midiPlayground.MidiAudioBlocks.MidiConstant;
import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;
import synthesis.midiPlayground.MidiAudioBlocks.NoteBlock;

/**
 * @author valeh
 */
public class MidiXylophoneInstr extends MidiInstrument{
	//default values for ADSR, epsilon(250),detune(5)
	public MidiXylophoneInstr() {
		super();
	}

	@Override
	protected MidiAudioBlock buildInstrument() {
		
		MidiAudioBlock f0 = new NoteBlock();
		
		MidiADSR env = new MidiADSR(new MidiConstant(0.01f),new MidiConstant(0.1f),new MidiConstant(0.6f),new MidiConstant(0.8f));				
		MidiConstant epsilon = new MidiConstant(250f);
		MidiConstant detune = new MidiConstant(5f);
		
		MidiSineWaveOscillator osc1 = new MidiSineWaveOscillator(new MidiAdder(f0,epsilon),env);
		MidiSineWaveOscillator osc2 = new MidiSineWaveOscillator(new MidiAdder(new MidiConstant(4*400f),epsilon), env);
		
		//OK it's ugly but a far as it works,it doesn't matter XD
		MidiAdder adder = new MidiAdder(osc1,osc2);
		MidiAdder adder2 = new MidiAdder(adder,f0);
		MidiAdder adder3 = new MidiAdder(adder2,new MidiAdder(detune,f0));
					
		MidiSineWaveOscillator oscOut = new MidiSineWaveOscillator(adder3, env);
		return oscOut;
	
	}
	

}
