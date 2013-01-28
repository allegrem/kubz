package synthesis.basicblocks.oneinputblocks;

import synthesis.AudioBlock;
import synthesis.basicblocks.noinputblocks.FixedSineWaveOscillator;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.RequireAudioBlocksException;

public class Vibrato extends OneInputBlock{

	private float gain; 
	private float freq;
	
	public Vibrato(float gain,float freq){
		this.gain = gain;
		this.freq = freq;
	}

	@Override
	protected Float compute(Float t) throws RequireAudioBlocksException {
		AudioBlock osc = new FixedSineWaveOscillator(freq,gain*in.play(t));
		Adder out = new Adder(in,osc);
		return out.play(t);
	}

	@Override
	protected Float computePhi(Float t) throws RequireAudioBlocksException {
		AudioBlock osc = new FixedSineWaveOscillator(freq,gain*in.play(t));
		AudioBlock out = new Adder(in,osc);
		return out.phi(t);
	}
	
	
	

}
