package synthesis.basicblocks.severalinputsblocks;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * This abstract class handles the plugin and plugout methods
 * for AudioBlocks with several inputs.
 * @author valeh
 *
 */
public abstract class SeveralInputBlock implements AudioBlock {
	
	protected ArrayList<AudioBlock> entries;
	
	/**
	 * creates a SIB AudioBlock with the specified entries.
	 * @param entries
	 */
	public SeveralInputBlock(ArrayList<AudioBlock> entries){
		this.entries = entries;
	}
	
	/**
	 * Plugs out the specified AudioBlock by 
	 * removing it from the entries if it already was in the list.
	 * @param a
	 */
	public void plugout(AudioBlock a) {
		if ( entries.contains(a) )
			entries.remove(a);	
	}
	
	public void plugoutAll() {
		entries=null;
	}
	
	public abstract void plugin(AudioBlock a, int i);
	public abstract void plugout(int i);
	
	
	public Float play(Float t) throws RequireAudioBlocksException{
		if (entries == null) 
			throw new RequireAudioBlocksException(this);
		return null;
	}
	
	@Override
	public abstract Float phi(Float t) throws RequireAudioBlocksException;
	

}
