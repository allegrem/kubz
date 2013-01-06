package synthesis.soundlab;

import java.io.IOException;

import javax.swing.JPanel;

import parameter.ParameterAudioBlock;

import synthesis.SynthesisUtilities;
import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.exceptions.AudioException;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.fmInstruments.FmInstrument;

/**
 * @author allegrem
 *
 */
public class SLInstrumentView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private FmInstrument instrument;

	/**
	 * @param instrument
	 */
	public SLInstrumentView() {
		super();
	}
	
	
	public void setInstrument(FmInstrument instrument) {
		this.instrument = instrument;
		for(ParameterAudioBlock p : instrument.getParameters()) {
			add(new SLParameterView(p));
		}
	}


	public void play() {
		//compute sound
		byte[] output = null;
		try {
			output = SynthesisUtilities.computeSound(0f, 1f, instrument);
		} catch (RequireAudioBlocksException e) {
			e.printStackTrace();
		}
		
		//playing sound
		SpeakersOutput speakersOutput = new SpeakersOutput();
		try {
			speakersOutput.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			speakersOutput.play(output);
		} catch (AudioException e) {
			e.printStackTrace();
		}
		speakersOutput.close();
		
		//save to wav 1
		WavFileOutput wavFileOutput1 = new WavFileOutput("fmout.wav");
		wavFileOutput1.open();
		wavFileOutput1.play(output);
		try {
			wavFileOutput1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
