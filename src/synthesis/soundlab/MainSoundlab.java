package synthesis.soundlab;

/**
 * @author allegrem
 *
 */
public class MainSoundlab {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SLWindow slWindow = new SLWindow();
		slWindow.addControl("freq", 20, 2000);
		slWindow.addControl("amp", 0, 120);
		slWindow.addControl("vibrato", 0, 5);
	}

}
