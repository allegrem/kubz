package midisynthesis.audioblocks;

import midisynthesis.midicommand.MidiCommand;

/**
 * This class handles a sine wave oscillator. The frequency and the amplitude
 * are {@link AudioBlock}.
 * 
 * @author allegrem
 */
public class SineOscillator implements MidiAudioBlock {

	// the frequency
	private final MidiAudioBlock f;

	// the amplitude
	private final MidiAudioBlock a;

	/**
	 * Create a new SineOscillator.
	 * 
	 * @param f
	 *            the frequency of the sinus.
	 * @param a
	 *            the amplitude of the sinus.
	 */
	public SineOscillator(MidiAudioBlock f, MidiAudioBlock a) {
		super();
		this.f = f;
		this.a = a;
	}

	/**
	 * Return a(t) * cos(phi(f(t))), where a is the amplitude AudioBlock and f
	 * is the frequency AudioBlock.
	 */
	@Override
	public Float play(Float t) {
		return (float) (a.play(t) * Math.cos(f.phi(t)));
	}

	@Override
	public Float phi(Float t) {
		return (float) (a.play(t) / f.play(t) * Math.sin(f.phi(t))); // a
																		// verifier
																		// !!!
	}

	/**
	 * No command is handled by this AudioBlock, but they are broadcasted to the
	 * the amplitude and frequency blocks.
	 */
	@Override
	public void command(MidiCommand command) {
		a.command(command);
		f.command(command);
	}

}
