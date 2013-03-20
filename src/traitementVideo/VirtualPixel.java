package traitementVideo;

import utilities.*;

public class VirtualPixel {
	
	private boolean brightness;
	private int groupeConnexe;
	private final Point pos;
	private int intensite;
	
	public Point getPos(){
		return this.pos;
	}

	
	public VirtualPixel(boolean brightness, int groupeConnexe, Point pos, int intensite) {
		this.brightness = brightness;
		this.groupeConnexe = groupeConnexe;
		this.pos = pos;
		this.intensite = intensite;
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

	public int getIntensite() {
		return intensite;
	}

	public void setIntensite(int intensite) {
		this.intensite = intensite;
	}
	
}
