package synthesis.midiPlayground;

/**
 * This class embodies a MIDI command, with a command code and two parameters.
 * You should take a look at the constants defined in this class, which
 * represents the available MIDI command with a description of the two
 * associated parameters.
 * 
 * @author allegrem
 */
public class MidiCommand {

	/**
	 * Fired when a key is pressed. Param1: note code. Param2: velocity.
	 */
	public final static int NOTE_ON = 144;

	/**
	 * Fired when a key is released. Param1: note code. Param2: velocity.
	 */
	public final static int NOTE_OFF = 128;

	private final int command;

	private final int param1;

	private final int param2;

	/**
	 * Create a new MidiCommand.
	 * 
	 * @param command
	 *            See the constants defined in {@link MidiCommand} to know which
	 *            commands are available.
	 * @param param1
	 *            See the command description to know which parameter it is.
	 * @param param2
	 *            See the command description to know which parameter it is.
	 */
	public MidiCommand(int command, int param1, int param2) {
		super();
		this.command = command;
		this.param1 = param1;
		this.param2 = param2;
	}

	/**
	 * @return the command
	 */
	public int getCommand() {
		return command;
	}

	/**
	 * @return the param1 (see each command for more informations about this
	 *         parameter)
	 */
	public int getParam1() {
		return param1;
	}

	/**
	 * @return the param2 (see each command for more informations about this
	 *         parameter)
	 */
	public int getParam2() {
		return param2;
	}

}
