package synthesis.soundlab;

import java.awt.EventQueue;

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
import javax.swing.JSplitPane;

import synthesis.fmInstruments.TwoOscFmInstrument;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


public class SLWindow {

	protected static final String DEFAULT_STATUS_BAR_TEXT = "SoundLab 0.1";
	private JFrame frmSoundlab;
	private JLabel lblSoundlab;
	private SLInstrumentView instrumentView;
	private SLSoundView soundView;
	private SLSpectrumView spectrumView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SLWindow window = new SLWindow();
					window.frmSoundlab.setVisible(true);
					window.setInstrument(new TwoOscFmInstrument());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void setInstrument(TwoOscFmInstrument instrument) {
		instrumentView.setInstrument(instrument);
	}

	/**
	 * Create the application.
	 */
	public SLWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSoundlab = new JFrame();
		frmSoundlab.setTitle("Kubz SoundLab");
		frmSoundlab.setBounds(100, 100, 560, 599);
		frmSoundlab.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmSoundlab.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('f');
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		addStatusBarListeners(mntmNew, "New instrument");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		addStatusBarListeners(mntmSave, "Save instrument");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		addStatusBarListeners(mntmSaveAs, "Save instrument as");
		mntmSaveAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		addStatusBarListeners(mntmLoad, "Load instrument");
		mntmLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmLoad);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		addStatusBarListeners(mntmQuit, "Quit SoundLab");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnFile.add(mntmQuit);
		
		JMenu mnPlay = new JMenu("Play");
		menuBar.add(mnPlay);
		
		JMenuItem mntmPlay = new JMenuItem("Play");
		mntmPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play();
			}
		});
		mntmPlay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
		mnPlay.add(mntmPlay);
		
		JMenu menu = new JMenu("?");
		menuBar.add(menu);
		
		JMenuItem mntmHel = new JMenuItem("Help");
		addStatusBarListeners(mntmHel, "Show help for SoundLab");
		mntmHel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menu.add(mntmHel);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		addStatusBarListeners(mntmAbout, "About SoundLab");
		menu.add(mntmAbout);
		frmSoundlab.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		frmSoundlab.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				play();
			}
		});
		toolBar.add(btnPlay);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		toolBar.add(horizontalGlue_2);
		
		JLabel lblParameters = new JLabel("Parameters");
		toolBar.add(lblParameters);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setToolTipText("");
		toolBar_1.setFloatable(false);
		frmSoundlab.getContentPane().add(toolBar_1, BorderLayout.SOUTH);
		
		lblSoundlab = new JLabel(DEFAULT_STATUS_BAR_TEXT);
		toolBar_1.add(lblSoundlab);
		
		JPanel panel = new JPanel();
		frmSoundlab.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		instrumentView = new SLInstrumentView();
		panel.add(instrumentView);
		instrumentView.setLayout(new BoxLayout(instrumentView, BoxLayout.Y_AXIS));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		panel.add(separator_1);
		
		soundView = new SLSoundView(this);
		soundView.setMinimumSize(new Dimension(600, 220));
		soundView.setSize(new Dimension(600, 220));
		panel.add(soundView);
		soundView.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setFloatable(false);
		soundView.add(toolBar_2, BorderLayout.NORTH);
		
		JButton btnZoomIn = new JButton("Zoom in");
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				soundView.zoomIn();
			}
		});
		toolBar_2.add(btnZoomIn);
		
		JButton btnZoomOut = new JButton("Zoom out");
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundView.zoomOut();
			}
		});
		toolBar_2.add(btnZoomOut);
		
		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundView.toLeft();
			}
		});
		toolBar_2.add(button);
		
		JButton button_1 = new JButton(">");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundView.toRight();
			}
		});
		toolBar_2.add(button_1);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		toolBar_2.add(horizontalGlue);
		
		JLabel lblSignalView = new JLabel("Signal View");
		toolBar_2.add(lblSignalView);
		
		JSeparator separator_2 = new JSeparator();
		panel.add(separator_2);
		separator_2.setForeground(Color.BLACK);
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		spectrumView = new SLSpectrumView(this);
		spectrumView.setMinimumSize(new Dimension(600, 200));
		panel.add(spectrumView);
		spectrumView.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_3 = new JToolBar();
		toolBar_3.setFloatable(false);
		spectrumView.add(toolBar_3, BorderLayout.NORTH);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		toolBar_3.add(horizontalGlue_1);
		
		JLabel lblSpectrumView = new JLabel("Spectrum View");
		toolBar_3.add(lblSpectrumView);
	}

	protected void play() {
		instrumentView.play();
		soundView.zoomAll(instrumentView.getLastSound().length);
		soundView.updateUI();
		spectrumView.updateUI();
	}

	private void addStatusBarListeners(final JMenuItem menuItem, final String message) {
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setStatusBarText(message);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setStatusBarText(DEFAULT_STATUS_BAR_TEXT);
			}
		});
	}

	protected void setStatusBarText(final String text) {
		lblSoundlab.setText(text);
	}

	public byte[] getLastSound() {
		if (instrumentView != null)
			return instrumentView.getLastSound();
		else
			return new byte[0];
	}

}
