package synthesis.midiPlayground;

public class MidiADSR implements MidiAudioBlock {

	private final MidiAudioBlock a;
	private final MidiAudioBlock d;
	private final MidiAudioBlock s;
	private final MidiAudioBlock r;
	private float amplitude;
	private float releaseTime = 0;
	private boolean releasing = false;
	private float releaseAmp = 0;

	/**
	 * @param a
	 *            time in seconds
	 * @param d
	 *            time in seconds
	 * @param s
	 *            between 0 and 1
	 * @param r
	 *            time in seconds
	 */
	public MidiADSR(MidiAudioBlock a, MidiAudioBlock d, MidiAudioBlock s,
			MidiAudioBlock r) {
		super();
		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
	}

	//return a value between 0 and amplitude
	@Override
	public Float play(Float t) {
		float a_ = a.play(t), s_ = s.play(t), d_ = d.play(t), r_ = r.play(t);
		float result;
		
		if (releasing) {
			if (t < releaseTime + r_)
				result = - releaseAmp / r_ * t + releaseAmp + releaseAmp * releaseTime / r_;
			else
				result = 0;
		}
		else if (t < a_ && a_ > 0) {
			result = t / a_;
		} else if (t < a_ + d_  &&  d_ > 0) {
			result = t * (s_ - 1f) / d_ + 1f + (1f - s_) / d_ * a_;
		} else {
			result = s_;
		}
		
		if (!releasing) {
			releaseAmp = result;
			releaseTime = t;
		}
		
		result *= amplitude;
		return result;
	}

	@Override
	public Float phi(Float t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void command(MidiCommand command) {
		if (command.getCommand() == MidiCommand.NOTE_ON) {
			amplitude = (float)command.getParam2();
			releasing = false;
		}
		else if (command.getCommand() == MidiCommand.NOTE_OFF)
			releasing = true;
	}

}
