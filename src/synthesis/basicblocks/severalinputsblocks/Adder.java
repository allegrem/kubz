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
		Float s = super.play(t);
		for (AudioBlock a : entries)		
				s += a.play(t);
		return s;
	}


	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		// TODO Auto-generated method stub
		System.out.println("Not yet implemented.");
		return null;
	}

	
}
