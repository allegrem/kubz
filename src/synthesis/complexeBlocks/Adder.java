package synthesis.complexeBlocks;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * Cette classe permet de faire la somme d’un nombre quelconque de signaux.
 * @author valeh	
 */

public class Adder implements AudioBlock {
	
	private ArrayList<AudioBlock> entries;
	
	public ArrayList<AudioBlock> getEntries(){
		return entries;
	}
	public void setEntries( ArrayList<AudioBlock> entries ){
		this.entries=entries;
	}
	
	public Float play(int t) {
		Float s=null;
		for (AudioBlock a : entries){
			try{
				s += a.play(t);
			}catch(RequireAudioBlocksException e){}
		}
		return s;
	}
	
	public void plugin(AudioBlock a, int i) {
		entries.add(a);	
	}
	
	public void plugout(AudioBlock a) {
		entries.remove(a);
		
	}
	
	public void plugout(int i) {
		
	}
	
	public void plugoutAll() {
		entries=null;
	}
	
	
}
