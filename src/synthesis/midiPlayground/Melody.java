/**
 * 
 */
package synthesis.midiPlayground;

import synthesis.midiPlayground.MidiAudioBlocks.MidiEffectBlock;
import synthesis.midiPlayground.MidiInstruments.MidiInstrument;
import synthesis.midiPlayground.MidiInstruments.MidiWoodInstrument;
import synthesis.midiPlayground.MidiPatterns.MidiPattern;
import synthesis.midiPlayground.MidiPatterns.MidiPattern3;

/**
 * @author allegrem
 * 
 */
public class Melody extends Thread {

	public int tempo;

	public MidiPattern pattern;

	public MidiInstrument instrument;

	public int parameter;

	public int tune; // move it to instrument !! ("note de depart?")

	private boolean keepPlaying = true; // can be true and false only once

	private boolean pause = false; // can switch as many times as needed

	public Melody() {
		// default parameters
		tempo = 60;
		pattern = new MidiPattern3();
		this.instrument = new MidiWoodInstrument(); // instrument + parameter
													// (TODO)
		tune = 77; // F5
		
		parameter = MidiEffectBlock.DEFAULT_VALUE;

		// start playing
		start();
	}

	/**
	 * @return the tempo
	 */
	public int getTempo() {
		return tempo;
	}

	/**
	 * @param tempo
	 *            the tempo to set
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
	 * @param pattern
	 *            the pattern to set
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
	 * @param instrument
	 *            the instrument to set
	 */
	public void setInstrument(MidiInstrument instrument) {
		if (keepPlaying)
			this.instrument.stopPlaying();
		this.instrument = instrument;
		setParameter(getParameter()); //resend the parameter
	}

	/**
	 * @return the tune
	 */
	public int getTune() {
		return tune;
	}

	/**
	 * @param tune
	 *            the tune to set
	 */
	public void setTune(int tune) {
		allSoundOff();
		this.tune = tune;
	}

	public void run() {
		System.out.println("starting run in Melody");

		DelayedMidiCommand c = pattern.getNext();
		DelayedMidiCommand last_c;

		// initial sleep
		try {
			sleep((long) (c.getDelayInSeconds(tempo) * 1000));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		while (keepPlaying) {
			if (pause) {
				try {
					sleep(100); // longer sleep time when paused
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			else {
				// play the command
				instrument.command(c.getMidiCommand(tune));

				// get the next command
				last_c = c;
				c = pattern.getNext();

				// sleep until the next command
				try {
					long sleeptime = (long) ((c.getDelayInSeconds(tempo) - last_c
							.getDelayInSeconds(tempo)) * 1000);
					if (sleeptime < 0) // if we are back at the beginning of the
										// pattern
						sleeptime = (long) (c.getDelayInSeconds(tempo) * 1000);
					// System.out.println("sleeping for " + sleeptime + " ms");
					sleep(sleeptime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("stopping run in Melody");
	}

	// stop the melody
	// /!\ IT CANNOT BE RESTARTED
	public void stopPlaying() {
		if (keepPlaying) {
			keepPlaying = false;
			instrument.stopPlaying();
		}
	}

	// pause the melody
	public void pause() {
		allSoundOff();
		pause = true;
	}

	private void allSoundOff() {
		instrument.command(new MidiCommand(MidiCommand.CHANNEL_MODE_MESSAGE,
				MidiCommand.CHANNEL_MODE_MESSAGE_ALL_NOTES_OFF, 0));
	}

	// resume the melody
	public void unpause() {
		pause = false;
	}

	// true if the melody is not paused
	public boolean isPlaying() {
		return !pause;
	}

	public void setParameter(int parameter) {
		this.parameter = parameter;
		instrument.command(new MidiCommand(MidiCommand.CONTROL_CHANGE,
				MidiCommand.CONTROL_CHANGE_EFFECT_CONTROL_1, parameter));
	}

	public int getParameter() {
		return parameter;
	}

}