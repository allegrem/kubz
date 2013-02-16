package monster;

import synthesis.filters.BandsFilter;

public class DefenceType {
	protected BandsFilter shield;
	
	public DefenceType(){
		this.shield = new BandsFilter(11);
		shield.random();
	}

	public BandsFilter getShield() {
		return shield;
	}
	public void setShield(BandsFilter shield) {
		this.shield = shield;
	}
}