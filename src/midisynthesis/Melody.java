/**
 * 
 */
package midisynthesis;

import midisynthesis.audioblocks.EffectBlock;
import midisynthesis.instruments.Instrument;
import midisynthesis.instruments.WoodInstrument;
import midisynthesis.midicommand.DelayedMidiCommand;
import midisynthesis.midicommand.MidiCommand;
import midisynthesis.patterns.MidiPattern;
import midisynthesis.patterns.MidiPattern1;

/**
 * @author allegrem
 * 
 */
public class Melody extends Thread {

	private static final int TEMPO_MIN = 40;
	private static final int TEMPO_MAX = 160;

	private static final int TUNE_MIN = 40;
	private static final int TUNE_MAX = 100;

	private int tempo;

	private MidiPattern pattern;

	private Instrument instrument;

	private int parameter;

	private int tune; // move it to instrument !! ("note de depart?")

	private boolean keepPlaying = true; // can be true and false only once

	private boolean pause = false; // can switch as many times as needed

	public Melody() {
		// default parameters
		tempo = 80;
		pattern = new MidiPattern1();
		instrument = new WoodInstrument();
		tune = 60;
		parameter = EffectBlock.DEFAULT_VALUE;

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

		if (this.tempo > TEMPO_MAX)
			this.tempo = TEMPO_MAX;
		else if (this.tempo < TEMPO_MIN)
			this.tempo = TEMPO_MIN;
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
		allSoundOff();
		interrupt(); // force reloading the next command
	}

	/**
	 * @return the instrument
	 */
	public Instrument getInstrument() {
		return instrument;
	}

	/**
	 * @param sinusInstrument
	 * @param instrument
	 *            the instrument to set
	 */
	public void setInstrument(Instrument instrument) {
		if (keepPlaying)
			this.instrument.stopPlaying();
		this.instrument = instrument;
		setParameter(getParameter()); // resend the parameter
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

		if (this.tune > TUNE_MAX)
			this.tune = TUNE_MAX;
		else if (this.tune < TUNE_MIN)
			this.tune = TUNE_MIN;
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
					sleep(sleeptime);
				} catch (InterruptedException e) {
					// if the sleep is interrupted, reload the next command
					c = pattern.getNext();
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

		if (this.parameter < 0)
			this.parameter = 0;
		else if (this.parameter > 127)
			this.parameter = 127;

		instrument.command(new MidiCommand(MidiCommand.CONTROL_CHANGE,
				MidiCommand.CONTROL_CHANGE_EFFECT_CONTROL_1, this.parameter));
	}

	public int getParameter() {
		return parameter;
	}

}