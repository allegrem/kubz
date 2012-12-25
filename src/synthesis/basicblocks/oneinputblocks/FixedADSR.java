package synthesis.basicblocks.oneinputblocks;

import synthesis.exceptions.RequireAudioBlocksException;

public class FixedADSR extends OneInputBlock {
	
	private final float a,d,s,r;
	private final float slevel, duration;
	
	
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
		float tfloat = t.floatValue();
		float aDur=a*duration, dDur=aDur+d*duration,sDur=dDur+s*duration,rDur=sDur+r*duration;
		
		float aExpr=(previous/aDur)*tfloat;
		float dExpr = ( (slevel-previous)/(d*duration) )*(tfloat-dDur) + slevel;
		float sExpr = slevel;
		float rExpr = ( (-slevel)/r*duration )*(tfloat-sDur) + slevel;

		if (tfloat <= aDur )
			return new Float( previous*aExpr );
		if (tfloat>aDur && tfloat<=dDur)
			return new Float( previous*dExpr );
		if (tfloat>dDur && tfloat<=sDur)
			return new Float( previous*sExpr );
		
		return new Float( previous*rExpr );
			
		
	
	}
	

}
