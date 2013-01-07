package synthesis.basicblocks.severalinputsblocks;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
*This class returns the sum of its entries.
*@see Adder#play(int t) 
*@author valeh
*/


public class Adder extends SeveralInputBlock {
	/**
	 * Creates an Adder AudioBlock with the specified entries.
	 * @param entries the list of input AudioBlocks 
	 * @see SeveralInputBlock#SeveralInputBlock(ArrayList)
	 */
	public Adder(ArrayList<AudioBlock> entries){
		super(entries);
	}
	
	/**
	 * Returns the sum of the entries s(t)=e1(t)+e2(t)+...
	 * @param t the specified instant
	 */
	public Float play(Float t) throws RequireAudioBlocksException{
		super.play(t);
		float s = 0.0f;
		for (AudioBlock a : entries)		
			s += a.play(t).floatValue();
		return new Float(s);
	}


	/**
	 * Returns the phi of the output signal as sum of that of 
	 * the input signals.
	 * @param t instant t
	 * @see SeveralInputBlock#phi(Float)
	 */
	public Float phi(Float t) throws RequireAudioBlocksException {
		super.phi(t);
		Float phi = 0f;
		for (AudioBlock e : entries)
			phi += e.phi(t);
		return phi;
	}

	
}
