package synthesis.soundlab;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;


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
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		createToolbar();
	}
	
	
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


	public void setInstrument(FmInstrument instrument) {
		this.instrument = instrument;
		removeAll();
		createToolbar();
		for(ParameterAudioBlock p : instrument.getParameters()) {
			add(new SLParameterView(p));
			p.addObserver(this);
		}
		updateUI();
		window.updateSound();
	}


	public byte[] computeSound() {
		byte[] sound = null;
		try {
			sound  = SynthesisUtilities.computeSound(0f, 3f, instrument);
		} catch (RequireAudioBlocksException e) {
			e.printStackTrace();
		}
		return sound;
	}

	
	@Override
	public void update(Observable o, Object arg) {
		window.updateSound();
	}

}
