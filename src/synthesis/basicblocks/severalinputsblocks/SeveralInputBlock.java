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
	 * Creates a SIB AudioBlock with the specified entries.
	 * @param entries the list of input AudioBlocks
	 */
	public SeveralInputBlock(ArrayList<AudioBlock> entries){
		this.entries = entries;
	}
	
	
	public SeveralInputBlock() {
		this.entries = new ArrayList<AudioBlock>();
	}
	
	/**
	 * Plugs out the specified AudioBlock by 
	 * removing it from the entries if it already was in the list.
	 * @param a the AudioBlock to plug out
	 */
	public void plugout(AudioBlock a) {
		if ( entries.contains(a) )
			entries.remove(a);	
	}
	
	/**
	 * Plugs out everything by totally emptying the list.
	 */
	public void plugoutAll() {
		entries=null;
	}
	
	/**
	 * Plugs in the specified AudioBlock by adding it in the list.
	 * @param a the AudioBlock to plug in 
	 */
	public void plugin(AudioBlock a) {
		entries.add(a);	
	}
	
	/**
	 * Returns s(t) (the output signal at t). Raises RequireAudioBlockException 
	 * error if the list is empty. 
	 * @param t the specified instant
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException{
		if (entries == null) 
			throw new RequireAudioBlocksException(this);
		return null;
	}
	
	/**
	 * Calculates the phi of the output signal.
	 * @param t the specified instant
	 * @see AudioBlock#phi(Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException{
		if (entries==null)
			throw new RequireAudioBlocksException(this);
		return null;
	}
	

}
