/**
 * 
 */
package parameter;

import java.util.Observer;

import synthesis.AudioBlock;

/**
 * @author allegrem
 *
 */
public interface ParameterAudioBlock extends AudioBlock {
	
	public String getLabel();
	
	public int getMin();
	
	public int getMax();
	
	public void setValue(int value);
	
	public void addObserver(Observer o);

	public int getValue();

}