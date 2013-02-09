package views.attacks;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import map2.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.PartialDisk;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

/**
 * Affiche le cone d'attaque servant a
 * definir la direction et l'ouverture de l'attaque
 * @author paul
 *
 */
public class AttackConeView implements DisplayableChild {
	private DisplayableFather father;
	private double aperture;
	private double direction;
	private double idirection;
	private double fatherAngle=0;
	private int power;
	private int start=0;
	private ReadableColor color=Color.DKGREY;
	private long pause=30;// Temps de pause pour le deplacement du signal
	private long startingTime=0;
	private boolean dead=false;
	
	/**
	 * Creation d'un cone d'atatque
	 * @param aperture L'angle d'ouverture du cone
	 * @param direction La direction du cone autour de z
	 * Le 0 correspond a l'axe y (vers le bas)
	 * @param power La "puissance" du cone
	 * Plus power est grand, plus la longueur du cone sera importante
	 */
	public AttackConeView(double aperture, double direction, int power,DisplayableFather father){
		this.aperture=aperture;
		this.idirection=direction;
		this.power=power;
		this.father=father;
		fatherAngle=father.getAngle();
	}
	
	@Override
	public void setFather( DisplayableFather father){
		this.father=father;
	}
	
	@Override
	public void paint(){
		direction=idirection;
		idirection%=360;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int alpha;
		boolean reflected =false;
		double beta=0;
		double x=0;
		double y=0;
		int fin=power-5;
		/*
		 * Activation de la transparence
		 */
		GL11.glEnable (GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);    
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		/*
		 * Gère les "collisions" entre le cône et les autres objets
		 * 
		 */
		collision : for(float i=15;i<=power;i+=10){
		if(!reflected){
		 for (DisplayableFather object: Map.getMap().getObjects()){
			if (object !=father && object.collisionCanOccure(new Point(father.getX(),father.getY()),i+5)){
				beta=direction-aperture/2;
			while(beta<=direction+aperture/2 ){	
				y=father.getY()+i*Math.cos(Math.PI/180*beta);
				x=father.getX()+i*Math.sin(Math.PI/180*beta);
				if (object.isInZone(new Point(x,y))){
					reflected=true;
					fin=Math.round(i-15);
					break collision;
				}
				beta+=10;	
			}
			}
		}
		}
		}
		
		
		/*
		 * Affichage du cone
		 */
		for(float i=start;i<=fin;i+=10){
			alpha=Math.round((fin-i)/fin*255);
		GL11.glColor4ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue(),(byte)alpha);
		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		
		GL11.glTranslated(father.getX(), father.getY(),father.getHeight()/2 );
		new PartialDisk().draw(i,i+5, 50,1,(float)(direction-aperture/2),(float) aperture);
		
		glMatrixMode(GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glPopMatrix();
		}
		
		/*
		 * Pause pour que le signal ne se deplace
		 * pas trop vite
		 */
		if(System.currentTimeMillis()-startingTime>pause){
		start++;
		start %=10;
		startingTime=System.currentTimeMillis();
		}
		
		/*
		 * Desactivation de la transparence
		 */
		GL11.glDisable (GL11.GL_BLEND); 
		GL11.glDisable(GL11.GL_ALPHA_TEST);  
	}

	/**
	 * Modification de la direction du cone
	 * @param direction
	 */
	public void setDirection( long direction){
		this.direction=direction;
	}
	
	/**
	 * Modification de l'aperture d'ouverture du cone
	 * @param Aperture
	 */
	public void setAperture(double aperture){
		this.aperture=aperture;
	}
	
	/**
	 * Modification de la puissance du cone
	 * @param power
	 */
	public void setPower(int power){
		this.power=power;
	}
	

	@Override
	public int getTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTimeOut(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInZone(Point mousePoint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColor(ReadableColor color) {
	this.color=color;
		
	}

	@Override
	public String getCharac() {
		
		return "AttackCone";
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	

}
