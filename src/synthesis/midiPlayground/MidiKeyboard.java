package synthesis.midiPlayground;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.exceptions.AudioException;

public class MidiKeyboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private SinusInstrument instr;
	private SpeakersOutput speakersOutput;

	public MidiKeyboard() {
		super("Kubz Keyboard");

		//create instrument
		instr = new SinusInstrument();

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

		// =============
		// instr.command(new MidiCommand(MidiCommand.NOTE_ON, 69, 33));
		// instr.command(new MidiCommand(MidiCommand.NOTE_ON, 73, 33));
		// instr.command(new MidiCommand(MidiCommand.NOTE_ON, 76, 33));
		// byte[] result = instr.play(44100, 44100);
		// ==============
		
		instr.start();

		//show the window
		setSize(300, 300);
		setVisible(true);
	}

	protected int charToNote(char keyChar) {
		switch (keyChar) {
		case 'q':
			return 60;
		case 's':
			return 61;
		case 'd':
			return 62;
		case 'f':
			return 63;
		case 'g':
			return 64;
		case 'h':
			return 65;
		case 'j':
			return 66;
		case 'k':
			return 67;
		case 'l':
			return 68;
		case 'm':
			return 69;
		default:
			return 0;
		}
	}

}
