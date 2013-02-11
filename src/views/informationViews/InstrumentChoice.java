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
import org.newdawn.slick.opengl.Texture;

import OpenGL.InstrumentTextures;
import OpenGL.Textures;


public class InstrumentChoice implements DisplayableChild{
	private DisplayableFather father;
	private int[] instrList = new int[6];
	private double distance=90;	
	
	int chosen = 0;

	@Override
	public synchronized void paint() {
		//GL11.glDisable(GL11.GL_TEXTURE_2D);			
		double posx=father.getX();
		double posy=father.getY();
		double x;
		double y;
		double angle =0;		
		double dAngle=2*Math.PI/(instrList.length);
		int sideLength = 20;  //cote du carre pour les instuements
		for (int i=0;i<instrList.length;i++){
			//GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());
			x=distance*Math.cos(angle);
			y=distance*Math.sin(angle);			
			glMatrixMode(GL_MODELVIEW);
			GL11.glPushMatrix();
			GL11.glTranslated(posx+x, posy+y,father.getHeight()+10 );
			
			if(i==chosen)
				sideLength = 30;
			else sideLength = 20;
					
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				//Texture texturei = InstrumentTextures.textureList[i];
				if (InstrumentTextures.textureList[i] == null)
					InstrumentTextures.initTexture(i);					
				/*if (Textures.textureTry == null)
					Textures.initTextureTry();*/						
				GL11.glBindTexture(GL11.GL_TEXTURE_2D,InstrumentTextures.textureList[i].getTextureID());
				GL11.glColor3f(1.0f, 1.0f, (float) 1.0);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2d(1, 0);
				GL11.glVertex3d(sideLength,-sideLength,father.getHeight()+10);
				GL11.glTexCoord2d(1,1);
				GL11.glVertex3d(sideLength, sideLength, father.getHeight()+10);
				GL11.glTexCoord2d(0,1);				
				GL11.glVertex3d(-sideLength,sideLength,father.getHeight()+10);
				GL11.glTexCoord2d(0,0);
				GL11.glVertex3d(-sideLength, -sideLength, father.getHeight()+10);
				GL11.glEnd();
				
				GL11.glDisable(GL11.GL_TEXTURE_2D);
			
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
	
		return "InstrumentChoice";
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
		//colors.add(color);
		
	}

	/**
	 * Retirer un instrument
	 * @param num Son numero
	 */
	public synchronized void removeInstrument(int num){
		//colors.remove(num);
		
	}
	
	/**
	 * Marquer un instrument comme choisi
	 * @param num Son numero
	 */
	public void setChosen(int num){
			chosen=num;		
	}
	
}
