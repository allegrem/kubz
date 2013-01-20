package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This interface defines a FM instrument. It extends an AudioBlock (so it can
 * play sound), and it adds a method returning the list of the parameters of
 * the instrument.
 * @author allegrem
 *
 */
public interface FmInstrument extends AudioBlock {

	/**
	 * Return the list of the parameters used by the instrument. These 
	 * parameters are used for modifying the sound.
	 */
	public ArrayList<ParameterAudioBlock> getParameters();
	
}
