package synthesis.filters;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;

import synthesis.basicblocks.oneinputblocks.OneInputBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * @author allegrem
 *
 */
public class Filter extends OneInputBlock {
	
	private final ArrayList<Float> feedbackwards;
	
	private final ArrayList<Float> feedforwards;
	
	private ConcurrentSkipListMap<Float,Float> cache;
	

	/**
	 * @param feedbackwards
	 * @param feedforwards
	 */
	public Filter(ArrayList<Float> feedbackwards, ArrayList<Float> feedforwards) {
		super();
		this.feedbackwards = feedbackwards;
		this.feedforwards = feedforwards;
		this.cache = new ConcurrentSkipListMap<Float,Float>();
	}
	

	/* (non-Javadoc)
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float compute(Float t) throws RequireAudioBlocksException {
		//does not compute time before 0
		if (t < 0)
			return 0f;
		
		//searches if this time has already been computed
		Float topKey = cache.ceilingKey(t);
		if (topKey != null  &&  topKey - t < 2 / SAMPLE_RATE) {
			return cache.get(topKey);
		}
		
		Float bottomKey = cache.floorKey(t);
		if (bottomKey != null && t - bottomKey < 2 / SAMPLE_RATE) {
			return cache.get(bottomKey);
		}
			
		//if it has never been computed, computes it	
		Float result = 0f;
		for (int i=0; i<feedforwards.size(); i++)
			result += feedforwards.get(i) * in.play(t - i / SAMPLE_RATE);		
		for (int i=1; i<feedbackwards.size(); i++)
			result -= feedbackwards.get(i) * play(t - i / SAMPLE_RATE);				
		if (feedbackwards.size() > 0)
			result /= feedbackwards.get(0);
		cache.put(t, result);
		return result;
	}

	
	/* (non-Javadoc)
	 * @see synthesis.AudioBlock#phi(java.lang.Float)
	 */
	@Override
	public Float phi(Float t) throws RequireAudioBlocksException {
		// TODO Auto-generated method stub
		System.out.println("ERROR! Not yet implemented");
		return null;
	}

}
