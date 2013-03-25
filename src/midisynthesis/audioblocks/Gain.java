package midisynthesis.audioblocks;

import midisynthesis.midicommand.MidiCommand;

/**
 * This class multiplies the block plugged in by a constant float.
 * @author valeh
 *
 */
public class Gain implements MidiAudioBlock {

	private final MidiAudioBlock gain;
	private final MidiAudioBlock in;

	public Gain(Float gain, MidiAudioBlock in) {
		this(new Constant(gain), in);
	}

	public Gain(MidiAudioBlock gain, MidiAudioBlock in) {
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
