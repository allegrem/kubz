package synthesis.basicblocks.oneinputblocks;


/**
 * This class produces an ADSR envelop with fixed parameters.
 * @author valeh
 */

public class FixedADSR extends OneInputBlock {
	
	private final float a,d,s,r,duration; 
	
	/**
	 * @param duration The total amount of time during which the envelop will be applied on the signal.
	 * @param a The fraction of duration corresponding to the Attack.
	 * @param d	The fraction of duration corresponding to the Decay.
	 * @param s The fraction of the input signal corresponding to the Sustain level.
	 * @param r The fraction of duration corresponding to the Release.	
	 */

	public FixedADSR(float a, float d, float s,float r, float duration) {  		

		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
		this.duration = duration;

	}
	
	@Override
	public Float compute(Float t) {
		
		float previous = in.play(t).floatValue();
		float sPrevious = s*previous;
		float tfloat = t.floatValue();
		//define the floats corresponding to the duration of each section
		float aDur=a*duration, dDur=aDur+d*duration,sDur=duration-r*duration,rDur=duration;  
				
		float aExpr = ( previous/aDur )*tfloat;
		float dExpr = ( (sPrevious-previous)/(d*duration) )*(tfloat-dDur) + sPrevious;
		float sExpr = sPrevious;
		float rExpr = ( (-sPrevious)/(r*duration) )*(tfloat-sDur) + sPrevious;
		
		//the next lines correspond to the defintition of the 
		//envelop (definie par morceaux)
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
	protected Float computePhi(Float t) {
		return null;
	}
	



}
