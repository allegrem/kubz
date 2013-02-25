package synthesis.midiPlayground;

public class MidiCommand {
	
	public final static int NOTE_ON = 144;
	public final static int NOTE_OFF = 128;

	private final int command;
	
	private final int param1;
	
	private final int param2;

	/**
	 * @param command
	 * @param param1
	 * @param param2
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
	 * @return the param1
	 */
	public int getParam1() {
		return param1;
	}

	/**
	 * @return the param2
	 */
	public int getParam2() {
		return param2;
	}
	
	
	
}
