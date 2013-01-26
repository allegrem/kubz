package synthesis.soundlab;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


import synthesis.SynthesisUtilities;
import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.exceptions.AudioException;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.fmInstruments.FmInstrument;
import synthesis.parameter.ParameterAudioBlock;

/**
 * @author allegrem
 *
 */
public class SLInstrumentView extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;

	private FmInstrument instrument;

	private SLWindow window;
	
	/**
	 * @param instrument
	 */
	public SLInstrumentView(SLWindow window) {
		super();
		this.window = window;
	}
	
	
	public void setInstrument(FmInstrument instrument) {
		this.instrument = instrument;
		removeAll();
		for(ParameterAudioBlock p : instrument.getParameters()) {
			add(new SLParameterView(p));
			p.addObserver(this);
		}
		updateUI();
		window.updateSound(computeSound());
	}


	private byte[] computeSound() {
		byte[] sound = null;
		try {
			sound  = SynthesisUtilities.computeSound(0f, 1f, instrument);
		} catch (RequireAudioBlocksException e) {
			e.printStackTrace();
		}
		return sound;
	}


	private void saveInWavFile() {
		WavFileOutput wavFileOutput1 = new WavFileOutput("fmout.wav");
		wavFileOutput1.open();
		wavFileOutput1.play(computeSound());
		try {
			wavFileOutput1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void playSound() {
		SpeakersOutput speakersOutput = new SpeakersOutput();
		try {
			speakersOutput.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			speakersOutput.play(computeSound());
		} catch (AudioException e) {
			e.printStackTrace();
		}
		speakersOutput.close();
	}


	@Override
	public void update(Observable o, Object arg) {
		window.updateSound(computeSound());
	}

}
