/**
 * 
 */
package synthesis.parameter;

import java.util.Observer;

import synthesis.AudioBlock;
import synthesis.fmInstruments.FmInstrumentNParams;

/**
 * This interface defines a Parameter used in a {@link FmInstrumentNParams}. A
 * parameter has a label, a min and a max value, its value can be modified and
 * accessed.<br />
 * WARNING: the value of a parameter is an integer! If the parameter is between
 * 0 and 1 for example, you should ask for a parameter between 0 and 1000, and
 * then divide it by 1000 when playing.<br />
 * This shows the difference between {@link ParameterAudioBlock#play(Float)}
 * (from {@link AudioBlock}) and {@link ParameterAudioBlock#getValue()} : play
 * returns the real value of the parameter, whereas getValue returns the value
 * which is given by the user. It allows to do some processing with the value
 * given by the user.
 * 
 * @author allegrem
 */
public interface ParameterAudioBlock extends AudioBlock {

	/**
	 * Return the label of the parameter.
	 */
	public String getLabel();

	/**
	 * Return the minimum value of the parameter.<br />
	 * WARNING: it returns an integer value! If the parameter is between 0 and 1
	 * for example, you should ask for a parameter between 0 and 1000, and then
	 * divide it by 1000 when returning the value.
	 */
	public int getMin();

	/**
	 * Return the maximum value of the parameter.<br />
	 * WARNING: it returns an integer value! If the parameter is between 0 and 1
	 * for example, you should ask for a parameter between 0 and 1000, and then
	 * divide it by 1000 when returning the value.
	 */
	public int getMax();

	/**
	 * Set the value of the parameter given by the user.<br />
	 * WARNING: this is not necessarily the same value than the play method.
	 */
	public void setValue(int value);
	
	public void incrValue(int incr);

	public void addObserver(Observer o); // useless??

	/**
	 * Return the value of the parameter given by the user.
	 */
	public int getValue();

}