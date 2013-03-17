package midisynthesis.audioblocks;

import midisynthesis.midicommand.MidiCommand;

public class Adder implements MidiAudioBlock{
	
	private MidiAudioBlock block1;
	private MidiAudioBlock block2;
	
	
	public Adder(MidiAudioBlock block1, MidiAudioBlock block2){
		this.block1 = block1;
		this.block2 = block2;		
	}
	@Override
	public Float play(Float t) {		
		return block1.play(t).floatValue() + block2.play(t).floatValue() ;
	}

	@Override
	public Float phi(Float t) {
		return block1.phi(t).floatValue() + block2.phi(t).floatValue() ;
	}

	@Override
	public void command(MidiCommand command) {
		block1.command(command);
		block2.command(command);
		
	}
	

}
