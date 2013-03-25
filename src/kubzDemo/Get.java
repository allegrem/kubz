package kubzDemo;

public class Get extends Thread{
	private Fenetre fenetre;
	private boolean taped=false;
	private long startingTime;
	private long pause =500;
	private boolean tap=false;
	
	
	public Get(Fenetre fenetre){
		this.fenetre=fenetre;
	}
	
	@Override
	public void run(){
		while(true){
			tap=fenetre.getCube().getTap();
			if(tap){
				taped=true;
				startingTime=System.currentTimeMillis()	;
			}else if(!tap&& System.currentTimeMillis()-startingTime>pause ){
				taped=false;
			}
			
		
		if(taped){
			fenetre.setLabel2("Tap !");	
		}else{
			fenetre.setLabel2("     ..........");	
		}
		
		fenetre.setLabel1("Angle= "+ fenetre.getCube().getAngle());
		
		}
	}

}
