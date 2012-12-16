package synthesis;

import java.util.ArrayList;

import synthesis.basicblocks.noinputblocks.Constant;
import synthesis.basicblocks.noinputblocks.SineWaveOscillator;
import synthesis.basicblocks.oneinputblocks.Offset;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * This main is used to test the synthesis engine. This should not be used
 * in the final project.
 * @author allegrem
 */
public class MainSynthesis {

	/**
	 * WARNING! This main should not be used in the final project!!
	 * @param args
	 * @throws TooManyInputsException 
	 * @throws RequireAudioBlocksException 
	 */
	public static void main(String[] args) throws TooManyInputsException, 
	RequireAudioBlocksException {
		SineWaveOscillator osc = 
				new SineWaveOscillator(new Float(440), new Float(10));
		
		Constant c = new Constant((float) 12);
		Offset off = new Offset((float) 3);
		off.plugin(c);
		off.plugout();
		off.plugin(osc);
		
		System.out.println(play(0, 1, osc));
	}
	
	
	public static ArrayList<Integer> play(double start, double length, AudioBlock a) 
			throws RequireAudioBlocksException {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		for(int i = 0; i<length*AudioBlock.sampleRate ; i++) {
			float f = a.play((int) (start + i/AudioBlock.sampleRate));
			arr.add((int) f);
		}
		
		return arr;
	}

}
