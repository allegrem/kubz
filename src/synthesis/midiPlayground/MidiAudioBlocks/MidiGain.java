package synthesis.midiPlayground.MidiAudioBlocks;

import synthesis.midiPlayground.MidiCommand;

public class MidiGain implements MidiAudioBlock{
	
	private final Float gain;
	private final MidiAudioBlock in;
	
	public MidiGain(Float gain, MidiAudioBlock in) {
		super();
		this.gain = gain;
		this.in = in;
	}
	
	@Override
	public Float play(Float t) {
		return in.play(t) * gain;
	}

	@Override
	public Float phi(Float t) {
		return in.phi(t) * gain;
	}

	@Override
	public void command(MidiCommand command) {
		in.command(command);		
	}
	

}
