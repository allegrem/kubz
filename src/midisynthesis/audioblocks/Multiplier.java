package midisynthesis.audioblocks;

import midisynthesis.midicommand.MidiCommand;
/**
 * This class multiplies two entries.
 * @author valeh
 *
 */
public class Multiplier implements MidiAudioBlock{
	
	private MidiAudioBlock block1;
	private MidiAudioBlock block2;
	
	public Multiplier(MidiAudioBlock block1, MidiAudioBlock block2){
		this.block1 = block1;
		this.block2 = block2;		
	}
	
	@Override
	public Float play(Float t) {
		return block1.play(t).floatValue()*block2.play(t).floatValue();
	}
	
	@Override
	public Float phi(Float t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void command(MidiCommand command) {
		block1.command(command);
		block2.command(command);		
	}
	
	

}
