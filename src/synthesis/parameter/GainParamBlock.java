/**
 * 
 */
package synthesis.parameter;


/**
 * This class handles a parameter block with a gain. The value can be modified
 * by the user. The value played will be the value given by the user multiplied
 * by the gain factor.
 * 
 * @author allegrem
 */
public class GainParamBlock extends ParamBlock {

	private float gain;

	/**
	 * Create a new GainParamBlock.
	 * 
	 * @param label
	 *            the name of the parameter
	 * @param min
	 *            the minimal value of the parameter
	 * @param max
	 *            the maximal value of the parameter
	 * @param defaultValue
	 *            the default value of the parameter
	 * @param gain
	 *            the value of the gain which will affect the returned value of
	 *            the parameter
	 */
	public GainParamBlock(String label, int min, int max, int defaultValue,
			float gain) {
		super(label, min, max, defaultValue);
		this.gain = gain;
	}

	/**
	 * Return the value given by the user multiplied by the gain.
	 * 
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) {
		return getValue() * gain;
	}

	/**
	 * Return s(t) = 2 * Pi * value * gain * t where value is the value given by
	 * the user.
	 * 
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) {
		return (float) (2 * Math.PI * getValue() * gain * t);
	}

}
