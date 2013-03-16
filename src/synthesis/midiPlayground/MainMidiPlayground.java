package synthesis.midiPlayground;


public class MainMidiPlayground extends Thread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//The Keyboard 
		//MidiKeyboard keyboard = new MidiKeyboard();
		
		//==========================
		
		
//		Melody melody = new Melody();
//		melody.startPlaying();
//		try {
//			sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		melody.stopPlaying();
		
		new MidiTestFrame();
	}

}
