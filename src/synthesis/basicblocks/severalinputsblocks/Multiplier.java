package synthesis.basicblocks.severalinputsblocks;

import java.util.ArrayList;

import synthesis.AudioBlock;

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
	
	
	public Multiplier() {
		super();
	}
	
	
	/**
	 * Method used to create a Multiplier with only two entries (faster to implement!).
	 * @param block1
	 * @param block2
	 */
	public Multiplier(AudioBlock block1, AudioBlock block2) {
		super();
		plugin(block1);
		plugin(block2);
	}
	

	/**
	 * Returns the product of the entries s(t)=e1(t)*e2(t)*...
	 * @param t the specified instant
	 */
	@Override
	public Float play(Float t){
		float s = 1f;
		for (AudioBlock a : entries)		
				s *= a.play(t);
		return new Float(s);
	}

	/**
	 * Returns the phi of the output signal as sum of that of 
	 * the input signals.
	 * @param t instant t
	 * @see SeveralInputBlock#phi(Float)
	 */
	@Override
	public Float phi(Float t) {
		System.out.println("ERROR.Not yet implemented.");
		return null;
	}


}
