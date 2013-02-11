package views.informationViews;

import static org.lwjgl.opengl.GL11.glVertex3d;

import org.apache.commons.math3.complex.Complex;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;

import OpenGL.GLDisplay;

import synthesis.Sound;



public class AudioRender {
	private int Y_SIZE = 0;

	private  int X_SIZE = 0;
	
	private int ymax;
	private int ymin;

	private int zoomX = 5000;

	private int currentSoundLength = 0;

	private int offsetX = 0;

	private static final int MANUAL_OFFSET = 0;

	private Sound sound;
	private GLDisplay display;

	private int[] spectrumCache;
	
	public AudioRender(GLDisplay display,Sound sound){
		this.sound=sound;
		this.display=display;
		X_SIZE=display.getmapDisplay_width()/3;
		Y_SIZE=display.getDisplay_height()-display.getmapDisplay_height();
		ymax=display.getDisplay_height();
		ymin=display.getmapDisplay_height();
	}

	public void renderAudioView() {
		if(!GLDisplay.getMode3D()){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3ub((byte)ReadableColor.LTGREY.getRed(),(byte)ReadableColor.LTGREY.getGreen(),(byte)ReadableColor.LTGREY.getBlue());
		GL11.glBegin(GL11.GL_QUADS);
		glVertex3d(0,ymax, 100);
		glVertex3d(X_SIZE, ymax, 100);
		glVertex3d(X_SIZE,ymin, 100);
		glVertex3d(0,ymin, 100);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3ub((byte)ReadableColor.BLACK.getRed(),(byte)ReadableColor.BLACK.getGreen(),(byte)ReadableColor.BLACK.getBlue());
		if (sound != null) {
			byte[] soundBytes = sound.getSound();
			int maxX = zoomX - 2;
			int xCoord1 = 0, yCoord1 = ymax, xCoord2 = 0, yCoord2 = ymax;
			for (int x = 0; x < maxX - 1; x++) {
				xCoord2 = x * X_SIZE / zoomX;
				yCoord2 = ymax-(Y_SIZE - (soundBytes[offsetX + x] + 127) * (Y_SIZE)
						/ 255 + MANUAL_OFFSET);
				GL11.glVertex3i(xCoord1, yCoord1, 100);
				GL11.glVertex3i(xCoord2, yCoord2, 100);
				xCoord1 = xCoord2;
				yCoord1 = yCoord2;
			}
			
			GL11.glVertex3i(0,  ymax-(MANUAL_OFFSET + Y_SIZE/ 2), 100);
			GL11.glVertex3i(X_SIZE,ymax-(MANUAL_OFFSET
					+ Y_SIZE / 2), 100);
		}
		}

		GL11.glEnd();
	}
		
	
	
public void renderSpectrumView(){
	if(!GLDisplay.getMode3D()){
	GL11.glDisable(GL11.GL_TEXTURE_2D);
	
	GL11.glColor3ub((byte)ReadableColor.LTGREY.getRed(),(byte)ReadableColor.LTGREY.getGreen(),(byte)ReadableColor.LTGREY.getBlue());
	GL11.glBegin(GL11.GL_QUADS);
	glVertex3d(2*X_SIZE,ymax, 100);
	glVertex3d(3*X_SIZE, ymax, 100);
	glVertex3d(3*X_SIZE,ymin, 100);
	glVertex3d(2*X_SIZE,ymin, 100);
	GL11.glEnd();
	
	if(sound!=null){
	GL11.glBegin(GL11.GL_LINES);
	GL11.glColor3ub((byte)ReadableColor.BLACK.getRed(),(byte)ReadableColor.BLACK.getGreen(),(byte)ReadableColor.BLACK.getBlue());
	
	updateSpectrum();
	
	if (spectrumCache != null && spectrumCache.length > 0) {
		for (int x = 0; x < X_SIZE; x++){
			glVertex3d(x+2*X_SIZE,ymax,100);
			glVertex3d(x+2*X_SIZE,spectrumCache[x]+display.getmapDisplay_height(),100);
			
		}
		}
	
	GL11.glEnd();
	}
	}
}

public void setSound(Sound sound) {
	this.sound=sound;
	
}


public void updateSpectrum() {
	Complex[] result = sound.getSpectrum();
	spectrumCache = new int[X_SIZE];
	for (int x = 0; x < result.length / 4; x++) {
		int x_coord = x * X_SIZE * 4 / result.length;
		int y1 = (int) (Y_SIZE + 55 - Math.abs(Math.log10(0.0001 + result[x]
				.abs())) * Y_SIZE / 5);
		int y2 = (int) (Y_SIZE + 55 - Math.abs(Math
				.log10(0.0001 + result[result.length / 4 + x].abs()))
				* Y_SIZE / 5);
		if (y2 < y1) // keep the higher value
			y1 = y2;
		if (spectrumCache[x_coord] == 0 || y1 < spectrumCache[x_coord])
			spectrumCache[x_coord] = y1; // save the result in cache
	}
}



}
