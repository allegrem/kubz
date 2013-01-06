package synthesis.soundlab;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
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


public class SLWindow {

	protected static final String DEFAULT_STATUS_BAR_TEXT = "SoundLab 0.1";
	private JFrame frmSoundlab;
	private JLabel lblSoundlab;
	private SLInstrumentView instrumentView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SLWindow window = new SLWindow();
					window.frmSoundlab.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		frmSoundlab.setTitle("SoundLab");
		frmSoundlab.setBounds(100, 100, 450, 300);
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
		toolBar.add(btnPlay);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setToolTipText("");
		toolBar_1.setFloatable(false);
		frmSoundlab.getContentPane().add(toolBar_1, BorderLayout.SOUTH);
		
		lblSoundlab = new JLabel(DEFAULT_STATUS_BAR_TEXT);
		toolBar_1.add(lblSoundlab);
		
		JPanel panel = new JPanel();
		frmSoundlab.getContentPane().add(panel, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.add(splitPane);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane.setRightComponent(splitPane_1);
		
		JPanel signal_analysis = new JPanel();
		splitPane_1.setLeftComponent(signal_analysis);
		
		JPanel signal_spectrum = new JPanel();
		splitPane_1.setRightComponent(signal_spectrum);
		
		instrumentView = new SLInstrumentView();
		splitPane.setLeftComponent(instrumentView);
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

}
