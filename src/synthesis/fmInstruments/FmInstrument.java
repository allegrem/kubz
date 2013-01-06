package synthesis.fmInstruments;

import java.util.ArrayList;

import synthesis.AudioBlock;
import synthesis.parameter.ParameterAudioBlock;

public interface FmInstrument extends AudioBlock {

	public ArrayList<ParameterAudioBlock> getParameters();
	
}
