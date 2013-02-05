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

import synthesis.FilteredSound;
import synthesis.audiooutput.SpeakersOutput;
import synthesis.audiooutput.WavFileOutput;
import synthesis.exceptions.AudioException;
import synthesis.filters.BandsFilter;
import synthesis.fmInstruments.BellInstrument;
import synthesis.fmInstruments.FmInstrument;
import synthesis.fmInstruments.PianoInstrument;
import synthesis.fmInstruments.TwoOscFmInstrument;
import synthesis.fmInstruments.TwoOscFmInstrumentBis;
import synthesis.fmInstruments.WindInstrument;
import synthesis.fmInstruments.WoodInstrument;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.io.IOException;

public class SLWindow {

	protected static final String DEFAULT_STATUS_BAR_TEXT = "SoundLab 0.2";
	private JFrame frmSoundlab;
	private JLabel lblSoundlab;
	private SLInstrumentView instrumentView;
	private SLSoundView soundView;
	private SLSpectrumView spectrumView;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private SLBandsFilterView filterView;
	private FilteredSound filteredSound;

	/**
	 * Create the application.
	 */
	public SLWindow() {
		filteredSound = new FilteredSound(new BellInstrument(), new BandsFilter(11), 3f);
		initialize();
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

		JMenuItem mntmPlay = new JMenuItem("Play");
		mnFile.add(mntmPlay);
		mntmPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play();
			}
		});
		mntmPlay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmSoundlab.dispose();
			}
		});

		JMenuItem mntmExportAswav = new JMenuItem("Export as .wav");
		mntmExportAswav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveInWavFile();
			}
		});
		mntmExportAswav.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmExportAswav);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		addStatusBarListeners(mntmQuit, "Quit SoundLab");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmQuit);

		JMenu mnInstrument = new JMenu("Instrument");
		menuBar.add(mnInstrument);
		
/*********************************BELL*******************************************************************/
		JRadioButtonMenuItem rdbtnmntmBell = new JRadioButtonMenuItem("Bell");
		buttonGroup.add(rdbtnmntmBell);
		rdbtnmntmBell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setInstrument(new BellInstrument());
			}
		});
		rdbtnmntmBell.setSelected(true);
		mnInstrument.add(rdbtnmntmBell);
		
/*********************************PIANO*******************************************************************/
		JRadioButtonMenuItem rdbtnmntmPiano = new JRadioButtonMenuItem("Piano");
		rdbtnmntmPiano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInstrument(new PianoInstrument());
			}
		});
		buttonGroup.add(rdbtnmntmPiano);
		mnInstrument.add(rdbtnmntmPiano);
		
/*************************************WINDINSTRUMENT***************************************************************/
		JRadioButtonMenuItem rdbtnmntmWindInstr = new JRadioButtonMenuItem(
				"WindInstrument");
		rdbtnmntmWindInstr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInstrument(new WindInstrument());
			}
		});
		buttonGroup.add(rdbtnmntmWindInstr);
		mnInstrument.add(rdbtnmntmWindInstr);	
				
/*****************************************TWOOSCFMINSTRUMENT***********************************************************/
		JRadioButtonMenuItem rdbtnmntmTwooscfm = new JRadioButtonMenuItem(
				"TwoOscFM");
		rdbtnmntmTwooscfm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInstrument(new TwoOscFmInstrument());
			}
		});
		buttonGroup.add(rdbtnmntmTwooscfm);
		mnInstrument.add(rdbtnmntmTwooscfm);
		
/*****************************************TWOOSCFMINSTRUMENTBIS***********************************************************/	
		JRadioButtonMenuItem rdbtnmntmTwooscfmBis = new JRadioButtonMenuItem(
				"TwoOscFM2");
		rdbtnmntmTwooscfmBis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInstrument(new TwoOscFmInstrumentBis());
			}
		});
		buttonGroup.add(rdbtnmntmTwooscfmBis);
		mnInstrument.add(rdbtnmntmTwooscfmBis);
					
/*********************************************WOOD*******************************************************/
		JRadioButtonMenuItem rdbtnmntmWood = new JRadioButtonMenuItem("WoodInstrument");
		rdbtnmntmWood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInstrument(new WoodInstrument());
			}
		});
		buttonGroup.add(rdbtnmntmWood);
		mnInstrument.add(rdbtnmntmWood);
		
/****************************************************************************************************/
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

		instrumentView = new SLInstrumentView(this, filteredSound.getInstrument());
		panel.add(instrumentView);

		soundView = new SLSoundView(filteredSound);
		panel.add(soundView);

		filterView = new SLBandsFilterView(this, filteredSound.getBandsFilter());
		panel.add(filterView);

		spectrumView = new SLSpectrumView(filteredSound);
		panel.add(spectrumView);
	}

	private void addStatusBarListeners(final JMenuItem menuItem,
			final String message) {
	}

	private void setInstrument(FmInstrument instrument) {
		filteredSound.setInstrument(instrument);
		instrumentView.setInstrument(instrument);
	}

	public void setVisible(boolean b) {
		frmSoundlab.setVisible(b);
	}

	private void saveInWavFile() {
		WavFileOutput wavFileOutput1 = new WavFileOutput("fmout.wav");
		wavFileOutput1.open();
		wavFileOutput1.play(filteredSound.getSound());
		try {
			wavFileOutput1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void play() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				SpeakersOutput speakersOutput = new SpeakersOutput();
				try {
					speakersOutput.open();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					speakersOutput.play(filteredSound.getSound());
				} catch (AudioException e) {
					e.printStackTrace();
				}
				speakersOutput.close();
			}
		});
		thread.start();
	}

}
