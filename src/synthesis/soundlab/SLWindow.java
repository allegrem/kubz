package synthesis.soundlab;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import synthesis.fmInstruments.BellInstrument;
import synthesis.fmInstruments.FmInstrument;
import synthesis.fmInstruments.PianoInstrument;
import synthesis.fmInstruments.TwoOscFmInstrument;
import synthesis.fmInstruments.WoodInstrument;

import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class SLWindow {

	protected static final String DEFAULT_STATUS_BAR_TEXT = "SoundLab 0.1";
	private JFrame frmSoundlab;
	private JLabel lblSoundlab;
	private SLInstrumentView instrumentView;
	private SLSoundView soundView;
	private SLSpectrumView spectrumView;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the application.
	 */
	public SLWindow() {
		initialize();
		setInstrument(new BellInstrument());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSoundlab = new JFrame();
		frmSoundlab.setResizable(false);
		frmSoundlab.setTitle("Kubz SoundLab");
		frmSoundlab.setBounds(100, 100, 1208, 497);
		frmSoundlab.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmSoundlab.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('f');
		menuBar.add(mnFile);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmSoundlab.dispose();
			}
		});
		
				JMenuItem mntmPlay = new JMenuItem("Play");
				mnFile.add(mntmPlay);
				mntmPlay.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						play();
					}
				});
				mntmPlay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		addStatusBarListeners(mntmQuit, "Quit SoundLab");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmQuit);

		JMenu mnInstrument = new JMenu("Instrument");
		menuBar.add(mnInstrument);

		JRadioButtonMenuItem rdbtnmntmBell = new JRadioButtonMenuItem("Bell");
		rdbtnmntmBell.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0));
		buttonGroup.add(rdbtnmntmBell);
		rdbtnmntmBell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setInstrument(new BellInstrument());
			}
		});
		rdbtnmntmBell.setSelected(true);
		mnInstrument.add(rdbtnmntmBell);

		JRadioButtonMenuItem rdbtnmntmPiano = new JRadioButtonMenuItem("Piano");
		rdbtnmntmPiano.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0));
		buttonGroup.add(rdbtnmntmPiano);
		rdbtnmntmPiano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInstrument(new PianoInstrument());
			}
		});

		JRadioButtonMenuItem rdbtnmntmTwooscfm = new JRadioButtonMenuItem(
				"TwoOscFM");
		rdbtnmntmTwooscfm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0));
		rdbtnmntmTwooscfm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInstrument(new TwoOscFmInstrument());
			}
		});
		buttonGroup.add(rdbtnmntmTwooscfm);
		mnInstrument.add(rdbtnmntmTwooscfm);

		JRadioButtonMenuItem rdbtnmntmWood = new JRadioButtonMenuItem("Wood");
		rdbtnmntmWood.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0));
		rdbtnmntmWood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInstrument(new WoodInstrument());
			}
		});
		buttonGroup.add(rdbtnmntmWood);
		mnInstrument.add(rdbtnmntmWood);
		mnInstrument.add(rdbtnmntmPiano);

		JMenu menu = new JMenu("?");
		menuBar.add(menu);

		JMenuItem mntmAbout = new JMenuItem("About");
		addStatusBarListeners(mntmAbout, "About SoundLab");
		menu.add(mntmAbout);
		frmSoundlab.getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setToolTipText("");
		toolBar_1.setFloatable(false);
		frmSoundlab.getContentPane().add(toolBar_1, BorderLayout.SOUTH);

		lblSoundlab = new JLabel(DEFAULT_STATUS_BAR_TEXT);
		toolBar_1.add(lblSoundlab);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmSoundlab.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(2, 2, 0, 0));

		instrumentView = new SLInstrumentView(this);
		panel.add(instrumentView);

		soundView = new SLSoundView();
		panel.add(soundView);

		JPanel filterView = new SLFilterView();
		panel.add(filterView);

		spectrumView = new SLSpectrumView();
		panel.add(spectrumView);
	}

	protected void play() {
		instrumentView.playSound();
	}

	private void addStatusBarListeners(final JMenuItem menuItem,
			final String message) {
	}

	private void setInstrument(FmInstrument instrument) {
		instrumentView.setInstrument(instrument);
	}

	public void setVisible(boolean b) {
		frmSoundlab.setVisible(b);
	}

	public void updateSound(byte[] sound) {
		soundView.setSound(sound);
		spectrumView.computeSpectrum(sound);
	}

}
