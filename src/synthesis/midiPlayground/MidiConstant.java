package synthesis.midiPlayground;

public class MidiConstant implements MidiAudioBlock {

	private final float value;
	
	/**
	 * @param value
	 */
	public MidiConstant(float value) {
		super();
		this.value = value;
	}

	@Override
	public Float play(Float t) {
		return value;
	}

	@Override
	public Float phi(Float t) {
		return (float) (2 * Math.PI * value * t);
	}

	@Override
	public void command(MidiCommand command) {
		//does nothing
	}

}
