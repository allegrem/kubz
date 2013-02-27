package synthesis.midiPlayground.MidiAudioBlocks;

import synthesis.midiPlayground.MidiCommand;

public class MidiSineWaveOscillator implements MidiAudioBlock {

	private final MidiAudioBlock f;
	private final MidiAudioBlock a;

	/**
	 * @param f
	 * @param a
	 */
	public MidiSineWaveOscillator(MidiAudioBlock f, MidiAudioBlock a) {
		super();
		this.f = f;
		this.a = a;
	}

	@Override
	public Float play(Float t) {
		return (float) (a.play(t) * Math.cos(f.phi(t)));
	}

	@Override
	public Float phi(Float t) {
		return (float) (a.play(t) / f.play(t) * 
				Math.sin(f.phi(t)));  //a verifier !!!
	}

	@Override
	public void command(MidiCommand command) {
		a.command(command);
		f.command(command);
	}

}
