package traitementVideo;

import utilities.*;

public class VirtualPixel {
	
	private boolean brightness;
	private int groupeConnexe;
	private final Point pos;
	
	public Point getPos(){
		return this.pos;
	}

	
	public VirtualPixel(boolean brightness, int groupeConnexe, Point pos) {
		this.brightness = brightness;
		this.groupeConnexe = groupeConnexe;
		this.pos = pos;
	}

	public void setGroupeConnexe(int gr){
		this.groupeConnexe = gr;
	}

	public boolean isBrightness() {
		return brightness;
	}

	public int getGroupeConnexe() {
		return groupeConnexe;
	}

	public void setBrightness(boolean brightness) {
		this.brightness = brightness;
	}

}
