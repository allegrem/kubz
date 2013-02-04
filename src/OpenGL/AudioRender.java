package OpenGL;

import org.lwjgl.opengl.GL11;



public class AudioRender {
	private static final int Y_SIZE = 200;

	private static final int X_SIZE = 600;

	private int zoomX = 0;

	private int currentSoundLength = 0;

	private int offsetX = 0;

	private static final int MANUAL_OFFSET = 25;

	private Sound sound;
	private GLDisplay display;
	
	public AudioRender(GLDisplay display,Sound sound){
		this.sound=sound;
		this.display=display;
	}

	public void renderAudioView() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_LINES);
		if (sound != null) {
			byte[] soundBytes = sound.getSound();
			int maxX = zoomX - 2;
			int xCoord1 = 0, yCoord1 = 0, xCoord2 = 0, yCoord2 = 0;
			for (int x = 0; x < maxX - 1; x++) {
				xCoord2 = x * X_SIZE / zoomX;
				yCoord2 = Y_SIZE - (soundBytes[offsetX + x] + 127) * Y_SIZE
						/ 255 + MANUAL_OFFSET;
				GL11.glVertex3i(xCoord1, yCoord1, 0);
				GL11.glVertex3i(xCoord2, yCoord2, 0);
				xCoord1 = xCoord2;
				yCoord1 = yCoord2;
			}
			
			GL11.glVertex3i(0,  MANUAL_OFFSET + Y_SIZE / 2, 0);
			GL11.glVertex3i(X_SIZE,MANUAL_OFFSET
					+ Y_SIZE / 2, 0);
			
		}

		GL11.glEnd();
	}
		
	
	
public void renderSpectrumView(){
	
}
}
