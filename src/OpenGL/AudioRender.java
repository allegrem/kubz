package OpenGL;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;

import synthesis.Sound;



public class AudioRender {
	private int Y_SIZE = 0;

	private  int X_SIZE = 0;

	private int zoomX = 0;

	private int currentSoundLength = 0;

	private int offsetX = 0;

	private static final int MANUAL_OFFSET = 25;

	private Sound sound;
	private GLDisplay display;
	
	public AudioRender(GLDisplay display,Sound sound){
		this.sound=sound;
		this.display=display;
		X_SIZE=(int)(display.getDisplay_width()/3);
		X_SIZE=display.getDisplay_height()-display.getmapDisplay_height();
	}

	public void renderAudioView() {
		int ymax=display.getDisplay_height();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3ub((byte)ReadableColor.GREY.getRed(),(byte)ReadableColor.GREY.getGreen(),(byte)ReadableColor.GREY.getBlue());
		if (sound != null) {
			byte[] soundBytes = sound.getSound();
			int maxX = zoomX - 2;
			int xCoord1 = 0, yCoord1 = ymax, xCoord2 = 0, yCoord2 = ymax;
			for (int x = 0; x < maxX - 1; x++) {
				xCoord2 = x * X_SIZE / zoomX;
				yCoord2 = ymax-(Y_SIZE - (soundBytes[offsetX + x] + 127) * Y_SIZE
						/ 255 + MANUAL_OFFSET);
				GL11.glVertex3i(xCoord1, yCoord1, 0);
				GL11.glVertex3i(xCoord2, yCoord2, 0);
				xCoord1 = xCoord2;
				yCoord1 = yCoord2;
			}
			
			GL11.glVertex3i(0,  ymax-(MANUAL_OFFSET + Y_SIZE / 2), 0);
			GL11.glVertex3i(X_SIZE,ymax-(MANUAL_OFFSET
					+ Y_SIZE / 2), 0);
			
		}

		GL11.glEnd();
	}
		
	
	
public void renderSpectrumView(){
	
}

public void setSound(Sound sound) {
	this.sound=sound;
	
}


}
