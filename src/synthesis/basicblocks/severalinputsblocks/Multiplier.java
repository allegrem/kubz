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
	
	
	public Multiplier() {
		super();
	}
	
	
	public Multiplier(AudioBlock block1, AudioBlock block2) {
		super();
		plugin(block1);
		plugin(block2);
	}
	

	/**
	 * Returns the product of the entries s(t)=e1(t)*e2(t)*...
	 * @param t the specified instant
	 */
	public Float play(Float t) throws RequireAudioBlocksException{
		super.play(t);
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
	public Float phi(Float t) throws RequireAudioBlocksException {
		
		Float phi = super.phi(t);    
		for (AudioBlock e : entries)
			phi += e.phi(t);
		return phi;
		
	}


}
