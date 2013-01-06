package synthesis.fmInstruments;

import java.util.ArrayList;

import parameter.ParameterAudioBlock;
import synthesis.AudioBlock;

public interface FmInstrument extends AudioBlock {

	public ArrayList<ParameterAudioBlock> getParameters();
	
}
