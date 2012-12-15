package synthesis.complexeBlocks;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * This abstract class handles the plugin and plugout methods
 * for AudioBlocks with several inputs.
 * @author valeh
 *
 */
public abstract class SeveralInputBlocks implements AudioBlock {
	
	protected ArrayList<AudioBlock> entries;
	
	public SeveralInputBlocks(ArrayList<AudioBlock> entries){
		this.entries = entries;
	}
	
	public void plugout(AudioBlock a) {
		entries.remove(a);	
	}
	
	public void plugoutAll() {
		entries=null;
	}
	
	public abstract void plugin(AudioBlock a, int i);
	public abstract void plugout(int i);
	
	
	public Float play(int t) throws RequireAudioBlocksException{
		if (entries == null) 
			throw new RequireAudioBlocksException();
		return null;
	}

}
