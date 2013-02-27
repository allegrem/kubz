package synthesis.midiPlayground.MidiAudioBlocks;

import synthesis.midiPlayground.MidiCommand;

public class MidiMultiplier implements MidiAudioBlock{
	
	private MidiAudioBlock block1;
	private MidiAudioBlock block2;
	
	public MidiMultiplier(MidiAudioBlock block1, MidiAudioBlock block2){
		block1 = block1;
		block2 = block2;		
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
