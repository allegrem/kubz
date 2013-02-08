package synthesis.basicblocks.oneinputblocks;

import synthesis.AudioBlock;
import synthesis.basicblocks.orderedinputsblocks.SineWaveOscillator;
import synthesis.basicblocks.noinputblocks.Constant;
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
		AudioBlock osc = new SineWaveOscillator(new Constant(freq),new Gain(gain,in));
		Adder out = new Adder(in,osc);
		return out.play(t);
	}

	@Override
	protected Float computePhi(Float t) throws RequireAudioBlocksException {
		AudioBlock osc = new SineWaveOscillator(new Constant(freq),new Gain(gain,in));
		AudioBlock out = new Adder(in,osc);
		return out.phi(t);
	}
	
	
	

}
