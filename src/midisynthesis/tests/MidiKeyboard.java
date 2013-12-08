package midisynthesis.tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import midisynthesis.instruments.GhostSinus;
import midisynthesis.midicommand.MidiCommand;


public class MidiKeyboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private GhostSinus instr;

	public MidiKeyboard() {
		super("Kubz Keyboard");

		//create instrument
		instr = new GhostSinus();

		//keyboard listener
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("released " + e.getKeyChar());
				instr.command(new MidiCommand(MidiCommand.NOTE_OFF,
						charToNote(e.getKeyChar()), 100));
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("pressed " + e.getKeyChar());
				instr.command(new MidiCommand(MidiCommand.NOTE_ON, charToNote(e
						.getKeyChar()), 100));
			}
		});
		
		//escape key listener
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("exiting...");
				instr.stopPlaying();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		};
		JRootPane rootPane = new JRootPane();
		rootPane.registerKeyboardAction(action, stroke,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		this.setRootPane(rootPane); 
		
//		instr.start();

		//show the window
		setSize(300, 300);
		setVisible(true);
	}

	protected int charToNote(char keyChar) {
		switch (keyChar) {
		case 'q':
			return 60; //do3
		case 'z':
			return 61;
		case 's':
			return 62; //re3
		case 'e':
			return 63;
		case 'd':
			return 64; //mi3
		case 'f':
			return 65; //fa3
		case 't':
			return 66;
		case 'g':
			return 67; //sol3
		case 'y':
			return 68;
		case 'h':
			return 69; //la3
		case 'u':
			return 70;
		case 'j':
			return 71; //si3
		case 'k':
			return 72; //do4
		case 'o':
			return 73;
		case 'l':
			return 74; //re4
		case 'p':
			return 75;
		case 'm':
			return 76; //mi4
		default:
			return 0;
		}
	}

}
