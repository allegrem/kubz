package synthesis.basicblocks.oneinputblocks;

public class FixedADSR extends OneInputBlock {
	
	/*private final float a,d,s,r;
	private final float slevel;*/
	
	
	/*public FixedADSR(int a, int d, int s,int r, float slevel) throws nonValidproportionException, nonValidslevelException{  //check in adsr are a partition of duration
																								  //and also check if slevel<=in.getMax()			
		this.slevel = slevel;
		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
	}
	public Float play(Float t) throws RequiredAudioBlockException {
		super.play(t);
		float previous = in.play(t).floatValue();
		float previousMax=in.getMax();
		float tfloat = t.floatValue();
		
		float aDur=a*duration, dDur=aDur+d*duration,sDur=dDur+s*duration,rDur=sDur+r*duration;
		
		switch( tfloat ){
		case (tfloat <= aDur ):
			return new Float(previous*(previousMax/aDur)*tfloat);
			break:
		case (tfloat>a && tfloat<=dDur):
			return new Float(previous*((slevel-previousMax)/d*duration)*tfloat);
			break;
		case (tfloat>dDur && tfloat<=sDur):
			return new Float(previous*slevel);
			break;
		default:
			return new Float(previous*(-slevel/(r*duration))*tfloat);
			break;
		}
		
	}*/
	

}
