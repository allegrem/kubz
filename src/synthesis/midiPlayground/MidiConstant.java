package synthesis.midiPlayground;

/**
 * This block returns a constant value all the time.
 * 
 * @author allegrem
 */
public class MidiConstant implements MidiAudioBlock {

	private final float value;
	
	/**
	 * Create a new MidiConstant with the value.
	 */
	public MidiConstant(float value) {
		super();
		this.value = value;
	}

	/**
	 * Return the constant value.
	 */
	@Override
	public Float play(Float t) {
		return value;
	}

	/**
	 * Return 2 * PI * value * t.
	 */
	@Override
	public Float phi(Float t) {
		return (float) (2 * Math.PI * value * t);
	}

	/**
	 * Do not respond to any MIDI command.
	 */
	@Override
	public void command(MidiCommand command) {
	}

}
