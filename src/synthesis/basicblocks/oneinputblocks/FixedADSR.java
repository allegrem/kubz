package synthesis.basicblocks.oneinputblocks;

import synthesis.exceptions.RequireAudioBlocksException;

/**
 * 
 * @author valeh
 *
 */

public class FixedADSR extends OneInputBlock {
	
	private final float a,d,s,r;
	private final float slevel, duration; //slevel is a fraction of the plugged in signal
	
	
	public FixedADSR(float a, float d, float s,float r, int duration, float slevel) /*throws nonValidproportionException, nonValidslevelException */{  //check in adsr are a partition of duration
																								  //and also check if slevel<=in.getMax()			
		this.slevel = slevel;
		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
		this.duration = duration;
	}
	
	public Float play(Float t) throws RequireAudioBlocksException {
		super.play(t);
		
		float previous = in.play(t).floatValue();
		float sPrevious = slevel*previous;
		float tfloat = t.floatValue();
		float aDur=a*duration, dDur=aDur+d*duration,sDur=dDur+s*duration,rDur=sDur+r*duration;
		
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
