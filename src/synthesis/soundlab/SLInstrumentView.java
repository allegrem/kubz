package synthesis.soundlab;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import synthesis.fmInstruments.FmInstrumentNParams;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This class is the view for a {@link FmInstrumentNParams}. It retrieves the list of
 * its parameters and shows a controller for each of them. It also contains a
 * toolbar with some action button (play, ...). The FmInstrumentNParams can be changed
 * on the fly with the method
 * {@link SLInstrumentView#setInstrument(FmInstrumentNParams)}.
 * 
 * @author allegrem
 */
public class SLInstrumentView extends JPanel {

	private static final long serialVersionUID = 1L;

	private SLWindow window;

	/**
	 * Create a new {@link SLInstrumentView} with no controls.
	 * 
	 * @param window
	 *            The window where the SLInstrumentView lives in.
	 */
	public SLInstrumentView(SLWindow window) {
		super();
		this.window = window;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		createToolbar();
	}

	/**
	 * Create a new {@link SLInstrumentView} and the parameter controls for the
	 * given instrument.
	 * 
	 * @param window
	 *            The window where the SLInstrumentView lives in.
	 * @param instrument
	 *            The instrument to display.
	 */
	public SLInstrumentView(SLWindow window, FmInstrumentNParams instrument) {
		this(window);
		setInstrument(instrument);
	}

	/**
	 * Create the toolbar for the instrument view. Currently, there is only a
	 * play button which calls the {@link SLWindow#play()} method.
	 */
	private void createToolbar() {
		JToolBar toolBar = new JToolBar();
		add(toolBar);
		toolBar.setFloatable(false);

		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.play();
			}
		});
		toolBar.add(btnPlay);

		Component horizontalGlue_2 = Box.createHorizontalGlue();
		toolBar.add(horizontalGlue_2);

		JLabel lblParameters = new JLabel("Parameters");
		toolBar.add(lblParameters);
	}

	/**
	 * Change the {@link FmInstrumentNParams} of the view. The old controllers are
	 * removed and replaced by the controllers for the given instrument. 
	 * 
	 * @param instrument The new {@link FmInstrumentNParams} to display.
	 */
	public void setInstrument(FmInstrumentNParams instrument) {
		removeAll();
		createToolbar();
		for (ParameterAudioBlock p : instrument.getParameters())
			add(new SLParameterView(p));
		updateUI();
	}

}
