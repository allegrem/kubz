package synthesis.fmInstruments;

import java.util.ArrayList;
import Parameter.ParameterAudioBlock;
import synthesis.AudioBlock;

public interface FmInstrument extends AudioBlock {

	public ArrayList<ParameterAudioBlock> getParameters();
	
}
