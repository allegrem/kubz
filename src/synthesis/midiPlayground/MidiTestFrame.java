package synthesis.midiPlayground;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class MidiTestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Melody melody;

	
	public MidiTestFrame() {
		super("Kubz Audio Test");

		//create melody
		melody = new Melody();

		//keyboard listener
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				switch(e.getKeyChar()) {
				case ' ':
					System.out.println("(un)pause");
					if(melody.isPlaying())
						melody.pause();
					else
						melody.unpause();
					break;
				case 'a':
					melody.setTempo(melody.getTempo()+1);
					System.out.println("tempo up : "+melody.getTempo());
					break;
				case 'q':
					System.out.println("tempo down : "+melody.getTempo());
					melody.setTempo(melody.getTempo()-1);
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		//escape key listener
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("exiting...");
				melody.stopPlaying();
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
		
		//show the window
		setSize(300, 300);
		setVisible(true);
	}
	
}
