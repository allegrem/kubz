package synthesis.basicblocks.oneinputblocks;

import synthesis.exceptions.RequireAudioBlocksException;

/**
 * 
 * @author valeh
 */

public class FixedADSR extends OneInputBlock {
	
	private final float a,d,s,r,duration; //slevel is a fraction of the plugged in signal
	
	/**
	 * @param duration The total amount of time during which the envelop will be applied on the signal.
	 * @param a The fraction of duration corresponding to the Attack.
	 * @param d	The fraction of duration corresponding to the Decay.
	 * @param s The fraction of duration corresponding to the Sustain.
	 * @param r The fraction of duration corresponding to the Release.
	 * @param slevel The fraction of the signal corresponding to the level during Sustain.
	 */
	public FixedADSR(float a, float d, float s,float r, float duration) /*throws nonValidproportionException, nonValidslevelException */{  //check in adsr are a partition of duration
																								  //and also check if slevel<=in.getMax()			
		
		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
		this.duration = duration;
	}
	
	public Float play(Float t) throws RequireAudioBlocksException {
		super.play(t);
		
		float previous = in.play(t).floatValue();
		float sPrevious = s*previous;
		float tfloat = t.floatValue();
		float aDur=a*duration, dDur=aDur+d*duration,rDur=duration-r*duration,sDur=duration-(aDur+dDur+rDur);
		
		float aExpr = ( previous/aDur )*tfloat;
		float dExpr = ( (sPrevious-previous)/(d*duration) )*(tfloat-dDur) + sPrevious;
		float sExpr = sPrevious;
		float rExpr = ( (-sPrevious)/r*duration )*(tfloat-sDur) + sPrevious;

		if (tfloat <= aDur )
			return new Float( previous*aExpr );
		if (tfloat>aDur && tfloat<=dDur)
			return new Float( previous*dExpr );
		if (tfloat>dDur && tfloat<=sDur)
			return new Float( previous*sExpr );
		
		return new Float( previous*rExpr );
			
		
	
	}
	

}
