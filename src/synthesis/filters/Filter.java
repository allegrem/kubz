package synthesis.filters;

import java.util.ArrayList;
import synthesis.basicblocks.oneinputblocks.OneInputBlock;
import synthesis.exceptions.RequireAudioBlocksException;

/**
 * @author allegrem
 *
 */
public class Filter extends OneInputBlock {
	
	private final ArrayList<Float> feedbackwards;
	
	private final ArrayList<Float> feedforwards;
	

	/**
	 * @param feedbackwards
	 * @param feedforwards
	 */
	public Filter(ArrayList<Float> feedbackwards, ArrayList<Float> feedforwards) {
		super();
		this.feedbackwards = feedbackwards;
		this.feedforwards = feedforwards;
	}
	

	/* (non-Javadoc)
	 * @see synthesis.AudioBlock#play(java.lang.Float)
	 */
	@Override
	public Float play(Float t) throws RequireAudioBlocksException {
		super.play(t);
		
		Float result = 0f;
		
		if (t > 0) {
			for (int i=0; i<feedforwards.size(); i++)
				result += feedforwards.get(i) * in.play(t - i / SAMPLE_RATE);
			
			for (int i=1; i<feedbackwards.size(); i++)
				result -= feedbackwards.get(i) * play(t - i / SAMPLE_RATE);
					
			if (feedbackwards.size() > 0)
				result /= feedbackwards.get(0);
		}
		
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
