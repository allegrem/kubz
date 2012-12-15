package synthesis.complexeBlocks;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
*This class returns the sum of its entries.
*@see Adder#play(int t) 
*@author valeh
*/


public class Adder extends SeveralInputBlocks {
	
	public Adder(ArrayList<AudioBlock> entries){
		super(entries);
	}
	

	@Override
	public void plugin(AudioBlock a, int i) {
		entries.add(a);	
	}
	
	@Override
	public void plugout(int i) {
		
	}
	
	public Float play(int t) throws RequireAudioBlocksException{
		Float s = super.play(t);
		for (AudioBlock a : entries)		
				s += a.play(t);
		return s;
	}
	
	
}
