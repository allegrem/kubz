package synthesis.main;

import java.io.IOException;

import synthesis.Sound;
import synthesis.audiooutput.AudioOutput;
import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.exceptions.AudioException;
import synthesis.filters.BandsFilter;
import synthesis.fmInstruments.FmInstrument;
import synthesis.fmInstruments.PianoInstrument2;

public class MainFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FmInstrument instr = new PianoInstrument2();
		Sound sound = new Sound(instr,1f);
		//byte[] soundTab = sound.getSound();
		BandsFilter makeFilter = new BandsFilter(11);
		for (int i=0;i<11;i++)
			makeFilter.setBar(i, 100);		
		
		
		byte[] soundTab = sound.filter(makeFilter).getSound();
		
		
		SpeakersOutput output = new SpeakersOutput();
		try {
			output.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.play(soundTab);
		} catch (AudioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output.close();
		
		WavFileOutput wav = new WavFileOutput("filter.wav");
		wav.open();
		wav.play(soundTab);
		try {
			wav.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
