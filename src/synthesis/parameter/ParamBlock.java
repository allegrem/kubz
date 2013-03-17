package synthesis.parameter;

import java.util.Observable;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * This class handles a basic parameter block. The value set by the user and the
 * value played are the same.
 * 
 * @author allegrem
 */
public class ParamBlock extends Observable implements ParameterAudioBlock {

	private final String label;

	private final int min;

	private final int max;

	private int value;

	/**
	 * Create a new ParamBlock.
	 * 
	 * @param label
	 *            the name of the parameter
	 * @param min
	 *            the minimal value of the parameter
	 * @param max
	 *            the maximal value of the parameter
	 * @param defaultValue
	 *            the default value of the parameter
	 */
	public ParamBlock(String label, int min, int max, int defaultValue) {
		super();
		this.label = label;
		this.min = min;
		this.max = max;
		this.value = defaultValue;
	}

	/**
	 * Return the value given by the user with no processing.
	 * 
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return (float) value;
	}

	/**
	 * Return s(t) = 2 * Pi * value * t where value is the value given by the
	 * user.
	 * 
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return (float) (2 * Math.PI * value * t);
	}

	/**
	 * Return the label of the parameter.
	 * 
	 * @see synthesis.ParameterAudioBlock#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * Return the minimal value of the parameter.
	 * 
	 * @see synthesis.ParameterAudioBlock#getMin()
	 */
	@Override
	public int getMin() {
		return min;
	}

	/**
	 * Return the maximal value of the parameter.
	 * 
	 * @see synthesis.ParameterAudioBlock#getMax()
	 */
	@Override
	public int getMax() {
		return max;
	}

	/**
	 * Modify the value of the parameter. This value is not processed when
	 * {@link ParamBlock#play(Float)} is called. If the given value is greater
	 * than the max value, then the value is set to the max value (same thing
	 * with the min value). Calling this method also notifies observers.
	 */
	@Override
	public void setValue(int value) {
		if (value > max)
			this.value = max;
		else if (value < min)
			this.value = min;
		else
			this.value = value;
		setChanged();
		notifyObservers();
	}

	/**
	 * Return the value given by the user.
	 */
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void incrValue(int incr) {
		setValue(getValue() + incr);
	}
	
	//choose a value between min and max 
	public void random() {
		setValue((int) (getMin() + Math.random() * (double) (getMax() - getMin())));
	}

}
