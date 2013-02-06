/**
 * 
 */
package synthesis.basicblocks.orderedinputsblocks;

import synthesis.AudioBlock;
import synthesis.basicblocks.oneinputblocks.FixedADSR;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * @author allegrem
 * 
 */
public class ADSR implements AudioBlock {

	/**
	 * The attack input of the ADSR AudioBlock.
	 */
	public static final int ATTACK_IN = 0;

	/**
	 * The decay input of the ADSR AudioBlock.
	 */
	public static final int DECAY_IN = 1;

	/**
	 * The sustain input of the ADSR AudioBlock.
	 */
	public static final int SUSTAIN_IN = 2;

	/**
	 * The release input of the ADSR AudioBlock.
	 */
	public static final int RELEASE_IN = 3;

	private AudioBlock a = null;
	private AudioBlock d = null;
	private AudioBlock s = null;
	private AudioBlock r = null;
	private float duration;
	private AudioBlock in;

	/**
	 * Create a new ADSR envelope AudioBlock.
	 * 
	 * @param a
	 *            the attack value (must be between 0 and 1)
	 * @param d
	 *            the decay value (must be between 0 and 1)
	 * @param s
	 *            the sustain value (must be between 0 and 1)
	 * @param r
	 *            the release value (must be between 0 and 1)
	 */
	public ADSR(AudioBlock a, AudioBlock d, AudioBlock s, AudioBlock r,
			float duration, AudioBlock in) {
		super();
		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
		this.duration = duration;
		this.in = in;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		FixedADSR env = new FixedADSR(a.play(t), d.play(t), s.play(t),
				r.play(t), duration);
		try {
			env.plugin(in);
		} catch (TooManyInputsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return env.play(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		// TODO Auto-generated method stub
		return null;
	}

}
