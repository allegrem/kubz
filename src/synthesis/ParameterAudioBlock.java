/**
 * 
 */
package synthesis;

/**
 * @author allegrem
 *
 */
public interface ParameterAudioBlock extends AudioBlock {
	
	public String getLabel();
	
	public int getMin();
	
	public int getMax();
	
	public void setValue(int value);
	
}
