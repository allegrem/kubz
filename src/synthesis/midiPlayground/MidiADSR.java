package synthesis.midiPlayground;

/**
 * This class handles an ADSR envelope. The max output level is given by the
 * velocity of the {@link MidiCommand}. The envelope responds to the MIDI
 * command : it starts when a {@link MidiCommand#NOTE_ON} command is received,
 * and the release starts when a {@link MidiCommand#NOTE_OFF} command is
 * received. The four parameters of the envelope (attack, decay, sustain,
 * release) are controlled by {@link MidiAudioBlock}, but it is highly
 * recommended to plug a {@link MidiConstant} or a {@link MidiParam} in (since
 * their value won't change).
 * 
 * @author allegrem
 */
public class MidiADSR implements MidiAudioBlock {

	private final MidiAudioBlock a; // attack
	private final MidiAudioBlock d; // decay
	private final MidiAudioBlock s; // sustain
	private final MidiAudioBlock r; // release

	private float amplitude; // amplitude given by the NOTE_ON command
	private float releaseTime = 0; // time when the release started
	private boolean releasing = false; // has the release started?
	private float releaseAmp = 0; // current amplitude when the release started

	/**
	 * Create a new MidiADSR object. The four parameters should be
	 * {@link MidiConstant} or {@link MidiParam} (since their value won't
	 * change).
	 * 
	 * @param a
	 *            attack time in seconds
	 * @param d
	 *            decay time in seconds
	 * @param s
	 *            sustain level between 0 and 1
	 * @param r
	 *            release time in seconds
	 */
	public MidiADSR(MidiAudioBlock a, MidiAudioBlock d, MidiAudioBlock s,
			MidiAudioBlock r) {
		super();
		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
	}

	/**
	 * Return the value of the envelope according to the given time. The output
	 * is between 0 and the amplitude (given in the MIDI command). When the
	 * release phase is finished, return 0.
	 */
	@Override
	public Float play(Float t) {
		float a_ = a.play(t), s_ = s.play(t), d_ = d.play(t), r_ = r.play(t);
		float result;

		// here the max output level is 1
		if (releasing) {
			if (t < releaseTime + r_) // release phase
				result = -releaseAmp / r_ * t + releaseAmp + releaseAmp
						* releaseTime / r_;
			else
				// after release phase
				result = 0;
		} else if (t < a_ && a_ > 0) { // attack phase
			result = t / a_;
		} else if (t < a_ + d_ && d_ > 0) { // decay phase
			result = t * (s_ - 1f) / d_ + 1f + (1f - s_) / d_ * a_;
		} else { // sustain phase
			result = s_;
		}

		// save the amplitude and the time until the release phase starts
		if (!releasing) {
			releaseAmp = result;
			releaseTime = t;
		}

		// now the max output level is amplitude
		result *= amplitude;
		return result;
	}

	@Override
	public Float phi(Float t) {
		// FIXME not implemented
		System.out.println("ERROR! phi function not implemented in MidiADSR!!");
		return null;
	}

	/**
	 * When a {@link MidiCommand#NOTE_ON} command is received, update the
	 * amplitude with the given amplitude. When a {@link MidiCommand#NOTE_OFF}
	 * command is received, start the release phase.
	 */
	@Override
	public void command(MidiCommand command) {
		if (command.getCommand() == MidiCommand.NOTE_ON) {
			amplitude = (float) command.getParam2();
			releasing = false;
		} else if (command.getCommand() == MidiCommand.NOTE_OFF)
			releasing = true;
	}

}
