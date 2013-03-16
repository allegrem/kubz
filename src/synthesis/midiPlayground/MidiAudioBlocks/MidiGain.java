package synthesis.midiPlayground.MidiAudioBlocks;

import synthesis.midiPlayground.MidiCommand;

public class MidiGain implements MidiAudioBlock {

	private final MidiAudioBlock gain;
	private final MidiAudioBlock in;

	public MidiGain(Float gain, MidiAudioBlock in) {
		this(new MidiConstant(gain), in);
	}

	public MidiGain(MidiAudioBlock gain, MidiAudioBlock in) {
		super();
		this.gain = gain;
		this.in = in;
	}

	@Override
	public Float play(Float t) {
		return in.play(t) * gain.play(t);
	}

	@Override
	public Float phi(Float t) {
		return in.phi(t) * gain.play(t);
	}

	@Override
	public void command(MidiCommand command) {
		in.command(command);
		gain.command(command);
	}

}
