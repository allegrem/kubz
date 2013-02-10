package monster;

import synthesis.filters.BandsFilter;

public class DefenceType {
	protected BandsFilter shield;
	
	public DefenceType(){
		this.shield = new BandsFilter(11);
	}

}
