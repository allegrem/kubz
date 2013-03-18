/**
 * 
 */
package synthesis.midiPlayground;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import synthesis.midiPlayground.MidiAudioBlocks.MidiSineWaveOscillator;
import synthesis.midiPlayground.MidiInstruments.*;
import synthesis.midiPlayground.MidiPatterns.MidiPattern;
import synthesis.midiPlayground.MidiPatterns.MidiPattern1;
import synthesis.midiPlayground.MidiPatterns.MidiPattern2;
import synthesis.midiPlayground.MidiPatterns.MidiPattern3;

/**
 * @author allegrem
 *
 */
public class Melody extends Thread {

	public int tempo;
	
	public MidiPattern pattern;
	
	public MidiInstrument instrument;
	
	public int tune; //move it to instrument !! ("note de depart?")

	private boolean keepPlaying = false;
	
	
	public Melody() {
		//default parameters
		tempo = 70;
		pattern = new MidiPattern1(); 
		setInstrument(new SinusInstrument()); //instrument + parameter (TODO)
		tune = 65; //F4
		//tune = 77; //F5
	}

	/**
	 * @return the tempo
	 */
	public int getTempo() {
		return tempo;
	}

	/**
	 * @param tempo the tempo to set
	 */
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	/**
	 * @return the pattern
	 */
	public MidiPattern getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(MidiPattern pattern) {		
		this.pattern = pattern;
	}

	/**
	 * @return the instrument
	 */
	public MidiInstrument getInstrument() {
		return instrument;
	}

	/**
	 * @param sinusInstrument 
	 * @param instrument the instrument to set
	 */
	public void setInstrument(MidiInstrument instrument) {
		if (keepPlaying) 
			this.instrument.stopPlaying();
		this.instrument = instrument;
		if (keepPlaying) 
			this.instrument.startPlaying();
	}

	/**
	 * @return the tune
	 */
	public int getTune() {
		return tune;
	}

	/**
	 * @param tune the tune to set
	 */
	public void setTune(int tune) {
		this.tune = tune;
	}
	
	public void run() {
		System.out.println("starting run in Melody");
		
		DelayedMidiCommand c = pattern.getNext();
		DelayedMidiCommand last_c;
		int debug_i = 0;
		
		//initial sleep
		try {
			sleep((long) (c.getDelayInSeconds(tempo)*1000));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		while (keepPlaying) {
			//play the command
			instrument.command(c.getMidiCommand(tune));
			
			//get the next command
			last_c = c;
			c = pattern.getNext();
			
			//sleep until the next command
			try {
				long sleeptime = (long) ((c.getDelayInSeconds(tempo) - last_c.getDelayInSeconds(tempo))*1000);
				if (sleeptime < 0) //if we are back at the beginning of the pattern
					sleeptime = (long) (c.getDelayInSeconds(tempo) * 1000);
				System.out.println("sleeping for "+sleeptime+" ms");
				sleep(sleeptime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			debug_i++;
			//if(debug_i == 11)
				//setInstrument(new SinusInstrument());
			//if(debug_i == 16)
				//setTempo(60);
			if (debug_i == 104)
				stopPlaying();
			
		
		}
		
		System.out.println("stopping run in Melody");
	}

	public void startPlaying() {
		keepPlaying = true;
		instrument.startPlaying();
		start();
	}

	public void stopPlaying() {
		keepPlaying = false;
		instrument.stopPlaying();
	}

	
	
}