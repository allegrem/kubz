package synthesis.basicblocks.severalinputsblocks;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/** 
*This class returns the product of its entries.
*@see Multiplier#play(int t) 
*@author valeh
*/

public class Multiplier extends SeveralInputBlock {
	
	/**
	 * Creates a Multiplier AudioBlock with the specified entries.
	 * @param entries the list of input AudioBlocks
	 * @see SeveralInputBlock#SeveralInputBlock(ArrayList)
	 */
	public Multiplier(ArrayList<AudioBlock> entries){
		super(entries);
	}
	

	/**
	 * Returns the product of the entries s(t)=e1(t)*e2(t)*...
	 * @param t the specified instant
	 */
	public Float play(Float t) throws RequireAudioBlocksException{
		Float s = super.play(t);
		for (AudioBlock a : entries)		
				s *= a.play(t);
		return s;
	}

	/**
	 * Returns the phi of the output signal as sum of that of 
	 * the input signals.
	 * @param t instant t
	 * @see SeveralInputBlock#phi(Float)
	 */
	public Float phi(Float t) throws RequireAudioBlocksException {
		
		System.out.println("Not yet implemented.");
		return null;
	}


}
