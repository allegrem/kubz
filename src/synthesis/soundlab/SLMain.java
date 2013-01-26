/**
 * 
 */
package synthesis.soundlab;

import java.awt.EventQueue;

import synthesis.fmInstruments.BellInstrument;

/**
 * @author allegrem
 *
 */
public class SLMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SLWindow window = new SLWindow();
					window.setVisible(true);
					window.setInstrument(new BellInstrument());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
