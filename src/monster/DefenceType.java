package monster;

import midisynthesis.Melody;
public class DefenceType {
	protected Melody melody;
	
	public DefenceType(){
		this.melody = new Melody();
		
	}

	public Melody getMelody() {
		return melody;
	}

	public void setMelody(Melody melody) {
		this.melody = melody;
	}

}