package synthesis.fmInstruments;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * This abstract class defines a FM instrument. It implements an AudioBlock (so
 * it can play sound), and extends Observable. The observers are notified each
 * time a parameter changes.
 * 
 * @author allegrem
 */
public abstract class FmInstrument extends Observable implements AudioBlock,
		Observer {

	private AudioBlock out;

	private final ArrayList<ParameterAudioBlock> paramList;

	/**
	 * Create a new FmInstrument. In the constructor of an instrument,
	 * parameters should be created using
	 * {@link FmInstrument#addParam(ParamBlock)} and the out AudioBlock should
	 * be set with {@link FmInstrument#setOut(AudioBlock)}.
	 */
	public FmInstrument() {
		super();
		paramList = new ArrayList<ParameterAudioBlock>();
	}

	/**
	 * Play the sound of the instrument at the given time t.
	 * 
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public final Float play(Float t) throws RequireAudioBlocksException {
		return out.play(t);
	}

	/**
	 * Return the result of the phi function for the given time t. This method
	 * is provided because FmInstrument implements {@link AudioBlock}, but it
	 * should not be used.
	 * 
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public final Float phi(Float t) throws RequireAudioBlocksException {
		return out.phi(t);
	}

	/**
	 * Return the list of all the parameters used by the instrument. These
	 * parameters are used for modifying the sound.
	 */
	public final ArrayList<ParameterAudioBlock> getParameters() {
		return paramList;
	}

	/**
	 * Set the last AudioBlock of the instrument. This AudioBlock will be used
	 * to play the sound of the instrument. This method should be called in the
	 * constructor of classes extending FmInstrument.
	 */
	protected void setOut(AudioBlock out) {
		this.out = out;
	}

	/**
	 * Add a {@link ParameterAudioBlock} in the parameter list and add the
	 * instrument as an observer. The method also returns the given
	 * {@link ParameterAudioBlock}.
	 */
	protected final ParameterAudioBlock addParam(ParameterAudioBlock p) {
		p.addObserver(this);
		paramList.add(p);
		return p;
	}

	/**
	 * This method should be only called by {@link ParameterAudioBlock} which
	 * the instrument observes. It makes the instrument to notify its own
	 * observers.
	 */
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
		
		System.out.println("fm instrument updated");
	}

}
