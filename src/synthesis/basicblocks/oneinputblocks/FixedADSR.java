package synthesis.basicblocks.oneinputblocks;


import synthesis.exceptions.RequireAudioBlocksException;

/**
 * 
 * @author valeh
 */

public class FixedADSR extends OneInputBlock {
	
	private final float a,d,s,r,duration; 
	
	/**
	 * @param duration The total amount of time during which the envelop will be applied on the signal.
	 * @param a The fraction of duration corresponding to the Attack.
	 * @param d	The fraction of duration corresponding to the Decay.
	 * @param s The fraction of duration corresponding to the Sustain.
	 * @param r The fraction of duration corresponding to the Release.
	 * @param slevel The fraction of the signal corresponding to the level during Sustain.
	 */
	public FixedADSR(float a, float d, float s,float r, float duration) {  
																								  			
		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
		this.duration = duration;
		
	}
	
	public Float compute(Float t) throws RequireAudioBlocksException {
		super.play(t);
		
		float previous = in.play(t).floatValue();
		float sPrevious = s*previous;
		float tfloat = t.floatValue();
		float aDur=a*duration, dDur=aDur+d*duration,sDur=duration-r*duration,rDur=duration;
		
		float aExpr = ( previous/aDur )*tfloat;
		float dExpr = ( (sPrevious-previous)/(d*duration) )*(tfloat-dDur) + sPrevious;
		float sExpr = sPrevious;
		float rExpr = ( (-sPrevious)/r*duration )*(tfloat-sDur) + sPrevious;

		if (tfloat <= aDur )
			return new Float( aExpr );
		if (tfloat>aDur && tfloat<=dDur)
			return new Float( dExpr );
		if (tfloat>dDur && tfloat<=sDur)
			return new Float( sExpr );
		if (tfloat>sDur && tfloat<=rDur)
			return new Float( rExpr );
		
			return new Float(0);
			
		
	
	}

	

	@Override
	protected Float computePhi(Float t) throws RequireAudioBlocksException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
