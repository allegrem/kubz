package midisynthesis.audioblocks;

import midisynthesis.midicommand.MidiCommand;

public class EffectBlock implements MidiAudioBlock {

	public static final int DEFAULT_VALUE = 60;
	private final float offset;
	private final float slope;
	private float value;

	/**
	 * @param offset
	 * @param slope
	 */
	public EffectBlock(float offset, float slope) {
		super();
		this.offset = offset;
		this.slope = slope;
		this.value = DEFAULT_VALUE;
	}

	@Override
	public Float play(Float t) {
		return slope * value + offset;
	}

	@Override
	public Float phi(Float t) {
		return (float) (2 * Math.PI * play(t) * t);
	}

	@Override
	public void command(MidiCommand command) {
		if (command.getCommand() == MidiCommand.CONTROL_CHANGE
				&& command.getParam1() == MidiCommand.CONTROL_CHANGE_EFFECT_CONTROL_1) {
			value = command.getParam2();
			System.out.println("param change : " + (slope * value + offset));
		}
	}
}
