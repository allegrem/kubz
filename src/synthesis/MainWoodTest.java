package synthesis;

import java.io.IOException;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.basicblocks.oneinputblocks.Gain;
import synthesis.basicblocks.severalinputsblocks.Adder;
import synthesis.exceptions.AudioException;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.fmInstruments.WoodInstrument;
import synthesis.parameter.ParameterAudioBlock;

public class MainWoodTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WoodInstrument wood1 = new WoodInstrument();

		// getting note parameter
		ParameterAudioBlock note = null;
		for (ParameterAudioBlock a : wood1.getParameters()) {
			if (a.getLabel() == "fm")
				note = a;
		}

		// opening output
		SpeakersOutput speakersOutput = new SpeakersOutput();
		try {
			speakersOutput.open();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// playing some nice music
		byte[] sound = null;
		try {
			 note.setValue(440);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(440);
			 sound = SynthesisUtilities.computeSound(0f, 0.5f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(494);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(440);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(587);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(554);
			 sound = SynthesisUtilities.computeSound(0f, 2f, wood1);
			 speakersOutput.play(sound);
			
			
			 note.setValue(440);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(440);
			 sound = SynthesisUtilities.computeSound(0f, 0.5f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(494);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(440);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(659);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(587);
			 sound = SynthesisUtilities.computeSound(0f, 2f, wood1);
			 speakersOutput.play(sound);
			
			
			 note.setValue(440);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(440);
			 sound = SynthesisUtilities.computeSound(0f, 0.5f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(880);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(740);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(587);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(554);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(494);
			 sound = SynthesisUtilities.computeSound(0f, 0.5f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(494);
			 sound = SynthesisUtilities.computeSound(0f, 2f, wood1);
			 speakersOutput.play(sound);
			
			
			 note.setValue(784);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(784);
			 sound = SynthesisUtilities.computeSound(0f, 0.5f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(740);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(587);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
			 note.setValue(659);
			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
			 speakersOutput.play(sound);
			
//			 note.setValue(587);
//			 sound = SynthesisUtilities.computeSound(0f, 1f, wood1);
//			 speakersOutput.play(sound);

			WoodInstrument wood2 = new WoodInstrument();
			ParameterAudioBlock note2 = null;
			for (ParameterAudioBlock a : wood2.getParameters()) {
				if (a.getLabel() == "fm")
					note2 = a;
			}

			note.setValue(587);
			note2.setValue(440);
			sound = SynthesisUtilities.computeSound(0f, 2f, new Adder(new Gain(
					0.5f, wood1), new Gain(0.5f, wood2)));
			speakersOutput.play(sound);

		} catch (RequireAudioBlocksException e) {
			e.printStackTrace();
		} catch (AudioException e) {
			e.printStackTrace();
		}

		// closing output
		speakersOutput.close();
	}
}
