package kubzDemo;

public class Angle extends Thread{
	Fenetre fenetre;
	
	public Angle(Fenetre fenetre){
		this.fenetre=fenetre;
	}
	
	@Override
	public void run(){
		while(true){
		fenetre.setLabel("Angle= "+ fenetre.getCube().getAngle());
		}
	}

}
