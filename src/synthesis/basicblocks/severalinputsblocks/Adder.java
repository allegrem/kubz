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
