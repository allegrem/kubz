/**
 * 
 */
package synthesis.fmInstruments;

import java.util.Observable;
import java.util.Observer;

import synthesis.AudioBlock;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.parameter.ParamBlock;
import synthesis.parameter.ParameterAudioBlock;

/**
 * @author allegrem
 * 
 */
public class FmInstruments3Params extends FmInstrument implements
		AudioBlock, Observer {

	private ParameterAudioBlock rot1;
	private ParameterAudioBlock rot2;
	private ParameterAudioBlock distance;
	
	private FmInstrumentNParams instrument;
	
	/**
	 * Create a new FmInstrumentNParams. In the constructor of an instrument,
	 * parameters should be created using
	 * {@link FmInstrumentNParams#addParam(ParamBlock)} and the out AudioBlock should
	 * be set with {@link FmInstrumentNParams#setOut(AudioBlock)}.
	 */
	public FmInstruments3Params(FmInstrumentNParams instrument, ParameterAudioBlock rot1, ParameterAudioBlock rot2, ParameterAudioBlock distance) {
		super();
		
		this.instrument = instrument;
		this.instrument.addObserver(this);
		
		this.rot1 = rot1;
		this.rot2 = rot2;
		this.distance = distance;
	}
	
	
	public void changeParams(int rot1, int rot2, int distance) {
		this.rot1.incrValue(rot1);
		this.rot2.incrValue(rot2);
		this.distance.incrValue(distance);
	}

	/**
	 * Play the sound of the instrument at the given time t.
	 * 
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public final Float play(Float t) throws RequireAudioBlocksException {
		return instrument.play(t);
	}

	/**
	 * Return the result of the phi function for the given time t. This method
	 * is provided because FmInstrumentNParams implements {@link AudioBlock}, but it
	 * should not be used.
	 * 
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public final Float phi(Float t) throws RequireAudioBlocksException {
		return instrument.phi(t);
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
