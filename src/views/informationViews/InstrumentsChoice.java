package views.informationViews;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;
import org.lwjgl.util.glu.Disk;
import org.newdawn.slick.Color;

import OpenGL.Textures;

/**
 * Affichage de cercles autour de l'unite pour le choix des instruments.
 * Lorsqu'un cercle est selectionne, il s'agrandit
 * @author berthier
 *
 */
public class InstrumentsChoice implements DisplayableChild{
	private DisplayableFather father;
	private ArrayList<ReadableColor> colors=new ArrayList<ReadableColor>();
	private double distance=60;
	private Disk disk;
	private int rayon=20;
	private ReadableColor chosen;
	
	public InstrumentsChoice(){
		disk=new Disk();
		addInstrument(ReadableColor.BLUE);
		//addInstrument(ReadableColor.RED);
		//addInstrument(ReadableColor.GREEN);
		addInstrument(ReadableColor.ORANGE);
		addInstrument(ReadableColor.PURPLE);
		addInstrument(ReadableColor.YELLOW);
		chosen=colors.get(0);
		
	}

	@Override
	public synchronized void paint() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);			
		double posx=father.getX();
		double posy=father.getY();
		double x;
		double y;
		double angle =0;		
		double dAngle=2*Math.PI/(colors.size());
		for (ReadableColor color : colors){
			GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());
			x=distance*Math.cos(angle);
			y=distance*Math.sin(angle);			
			glMatrixMode(GL_MODELVIEW);
			GL11.glPushMatrix();
			GL11.glTranslated(posx+x, posy+y,father.getHeight()+10 );
			if(color==chosen){
			disk.draw(0,rayon+10, 50, 1);
			
			}else{
				disk.draw(0,rayon, 50, 1);		
			}
			GL11.glLoadIdentity();
			GL11.glPopMatrix();
			angle+=dAngle;
		}
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCharac() {
	
		return "InstrumentsChoice";
	}

	@Override
	public void setFather(DisplayableFather father) {
		this.father=father;
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Ajout d'un nouvel instrument
	 * @param color Sa couleur
	 */
	public void addInstrument(ReadableColor color){
		colors.add(color);
		
	}

	/**
	 * Retirer un instrument
	 * @param num Son numero
	 */
	public synchronized void removeInstrument(int num){
		colors.remove(num);
		
	}
	
	/**
	 * Marquer un instrument comme choisi
	 * @param num Son numero
	 */
	public void setChosen(int num){
			chosen=colors.get(num);		
	}
	
}
