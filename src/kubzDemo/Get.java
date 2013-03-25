package kubzDemo;

public class Get extends Thread{
	private Fenetre fenetre;
	private boolean taped=false;
	private long startingTime;
	private long pause =100;
	
	
	public Get(Fenetre fenetre){
		this.fenetre=fenetre;
	}
	
	@Override
	public void run(){
		while(!isInterrupted()){
			if(fenetre.getCube().getTap()){
				taped=true;
				startingTime=System.currentTimeMillis()	;
			}else if(!fenetre.getCube().getTap()&& System.currentTimeMillis()-startingTime>pause ){
				taped=false;
			}
			
		
		if(taped){
			fenetre.setLabel2("Tap !");	
		}else{
			fenetre.setLabel2("..........");	
		}
		
		fenetre.setLabel1("Angle= "+ fenetre.getCube().getAngle());
		
		}
	}

}
