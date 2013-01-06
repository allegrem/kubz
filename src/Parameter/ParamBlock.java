package Parameter;

import java.util.Observable;


import synthesis.exceptions.RequireAudioBlocksException;

/**
 * @author allegrem
 * 
 */
public class ParamBlock extends Observable implements ParameterAudioBlock {

	private final String label;

	private final int min;

	private final int max;
	
	private int value;

	/**
	 * @param audioBlock
	 * @param label
	 * @param min
	 * @param max
	 */
	public ParamBlock(String label, int min, int max, int defaultValue) {
		super();
		this.label = label;
		this.min = min;
		this.max = max;
		this.value = defaultValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		return (float) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		return (float) (2 * Math.PI * value * t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.ParameterAudioBlock#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.ParameterAudioBlock#getMin()
	 */
	@Override
	public int getMin() {
		return min;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synthesis.ParameterAudioBlock#getMax()
	 */
	@Override
	public int getMax() {
		return max;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
		setChanged();
		notifyObservers();
	}
	
	@Override
	public int getValue() {
		return value;
	}

}
