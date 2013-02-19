package views.informationViews;

import static org.lwjgl.opengl.GL11.glVertex3d;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.math3.complex.Complex;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;

import player.parameter.Parameter;

import synthesis.Sound;
import utilities.Point;
import views.interfaces.Displayable;
import OpenGL.GLDisplay;


/**
 * Rendu de l'audio
 * @author paul
 *
 */
public class AudioRender implements Displayable,Observer{

	private static int vboVertexHandle;
	private boolean initialized=false;
	
	private Parameter parameter1;
	private Parameter parameter2;


	private int Y_SIZE = 100;

	private  int X_SIZE = 0;
	private int sX_SIZE=0;

	private int angle=0;
	

	private int zoomX = 1000;

	private int offsetX = 0;

	private static final int MANUAL_OFFSET = 0;

	private Sound sound;
	private GLDisplay display;

	private int[] spectrumCache;
	private byte[] soundBytes;
	private Complex[] result;
	private boolean update=true;

	private int shaderProgram;
	private boolean paint=true;
	
	public AudioRender(GLDisplay display,Sound sound,Parameter parameter1,Parameter parameter2){
		this.sound=sound;
		this.display=display;
		this.parameter1=parameter1;
		this.parameter2=parameter2;
		sound.addObserver(this);
		setPosition();
	}
	
	private void initialize(){
		loadShaders();
		vboVertexHandle= GL15.glGenBuffers();
		initialized=true;
		
	}

	private void setPosition() {
		Point p1=new Point(parameter1.getX(),parameter1.getY());
		Point p2=new Point(parameter2.getX(),parameter2.getY());
		X_SIZE=(int) p1.distanceTo(p2);
		angle=(int) Math.toDegrees(Math.atan2(p2.getY()-p1.getY(), p2.getX()-p1.getX()));
		if(X_SIZE!=sX_SIZE)
			paint=false;
		sX_SIZE=X_SIZE;
		
	}

	/**
	 * Chargement des VBO
	 */
	private void VBOLoad() {
		if(!initialized){
			initialize();
		}
		zoomX=X_SIZE*20;
		 int maxX = zoomX - 2;
		 FloatBuffer vertices= reserveData(maxX*6);
		 int xCoord1 = 0, yCoord1 = 0, xCoord2 = 0, yCoord2 = 0;
			for (int x = 0; x < maxX - 1; x++) {
				xCoord2 = x * X_SIZE / zoomX;
				yCoord2 =-(Y_SIZE/2-(soundBytes[offsetX + x] + 127) * Y_SIZE
						/ 255 + MANUAL_OFFSET);
				vertices.put(asFloats(new Vector3f(xCoord1, yCoord1, 100)));
				vertices.put(asFloats(new Vector3f(xCoord2, yCoord2, 100)));
				xCoord1 = xCoord2;
				yCoord1 = yCoord2;
			}
		
			vertices.put(asFloats(new Vector3f(0, -(MANUAL_OFFSET ), 100)));
			vertices.put(asFloats(new Vector3f(X_SIZE,-(MANUAL_OFFSET), 100)));
			 
			
		 vertices.flip();
		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboVertexHandle);
		 GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);

	
		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		 paint=true;
		
	}
	
	
/**
 * Chargement des shaders
 * 
 */
	private void loadShaders() {
		   shaderProgram = GL20.glCreateProgram();
		   int  vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
	       int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	        StringBuilder vertexShaderSource = new StringBuilder();
	        StringBuilder fragmentShaderSource = new StringBuilder();
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader("shaders/shader.vert"));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                vertexShaderSource.append(line).append('\n');
	            }
	        } catch (IOException e) {
	            System.err.println("Vertex shader wasn't loaded properly.");
	            e.printStackTrace();
	            System.exit(1);
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        BufferedReader reader2 = null;
	        try {
	            reader2 = new BufferedReader(new FileReader("shaders/shader.frag"));
	            String line;
	            while ((line = reader2.readLine()) != null) {
	                fragmentShaderSource.append(line).append('\n');
	            }
	        } catch (IOException e) {
	            System.err.println("Fragment shader wasn't loaded properly.");
	            System.exit(1);
	        } finally {
	            if (reader2 != null) {
	                try {
	                    reader2.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        GL20.glShaderSource(vertexShader, vertexShaderSource);
	        GL20.glCompileShader(vertexShader);
	        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
	            System.err.println("Vertex shader wasn't able to be compiled correctly.");
	        }
	        GL20.glShaderSource(fragmentShader, fragmentShaderSource);
	        GL20.glCompileShader(fragmentShader);
	        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
	            System.err.println("Fragment shader wasn't able to be compiled correctly.");
	        }
	        GL20.glAttachShader(shaderProgram, vertexShader);
	        GL20.glAttachShader(shaderProgram, fragmentShader);
	        GL20.glLinkProgram(shaderProgram);
	        GL20.glValidateProgram(shaderProgram);
	}

//
//	/**
//	 * Chargement des VBO pour l'audio
//	 */
//	public void VBOLoadAudio(){
//		 int maxX = zoomX - 2;
//		 FloatBuffer vertices= reserveData(maxX*6);
//		 int xCoord1 = 0, yCoord1 = ymax, xCoord2 = 0, yCoord2 = ymax;
//			for (int x = 0; x < maxX - 1; x++) {
//				xCoord2 = x * X_SIZE / zoomX;
//				yCoord2 = ymax-(Y_SIZE - (soundBytes[offsetX + x] + 127) * (Y_SIZE)
//						/ 255 + MANUAL_OFFSET);
//				vertices.put(asFloats(new Vector3f(xCoord1, yCoord1, 100)));
//				vertices.put(asFloats(new Vector3f(xCoord2, yCoord2, 100)));
//				xCoord1 = xCoord2;
//				yCoord1 = yCoord2;
//			}
//		
//			vertices.put(asFloats(new Vector3f(0,  ymax-(MANUAL_OFFSET + Y_SIZE/ 2), 100)));
//			vertices.put(asFloats(new Vector3f(X_SIZE,ymax-(MANUAL_OFFSET
//					+ Y_SIZE / 2), 100)));
//			 
//			
//		 vertices.flip();
//		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboVertexHandleA);
//		 GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);
//
//	
//		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//	}
//	
//	/**
//	 * Chargement des VBO pour le spectre
//	 */
//
//	public void VBOLoadSpectrum(){
//		 FloatBuffer vertices= reserveData((X_SIZE)*6);
//		 for (int x = 0; x < X_SIZE; x++){
//			 vertices.put(asFloats(new Vector3f(x+2*X_SIZE,ymax,100)));
//			 vertices.put(asFloats(new Vector3f(x+2*X_SIZE,spectrumCache[x]+display.getmapDisplay_height(),100)));
//				
//			}
//		 vertices.flip();
//
//		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboVertexHandleS);
//		 GL15.glBufferData(GL15.GL_ARRAY_BUFFER,vertices, GL15.GL_DYNAMIC_DRAW);
//	
//		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//	}
//	
//	
//	/**
//	 * Rendu de l'audio
//	 */
//	public void renderAudioView() {
//		if(update)
//			update();
//		
//		if(!GLDisplay.getMode3D()){
//		GL11.glDisable(GL11.GL_TEXTURE_2D);
//		
//		GL11.glColor3ub((byte)ReadableColor.LTGREY.getRed(),(byte)ReadableColor.LTGREY.getGreen(),(byte)ReadableColor.LTGREY.getBlue());
//		GL11.glBegin(GL11.GL_QUADS);
//		glVertex3d(0,ymax, 100);
//		glVertex3d(X_SIZE, ymax, 100);
//		glVertex3d(X_SIZE,ymin, 100);
//		glVertex3d(0,ymin, 100);
//		GL11.glEnd();
//		
//		if(sound!=null){
//			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboVertexHandleA);
//			GL11.glVertexPointer(3,GL11.GL_FLOAT,0,0L);
//			GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
//			GL11.glColor3ub((byte)ReadableColor.BLACK.getRed(),(byte)ReadableColor.BLACK.getGreen(),(byte)ReadableColor.BLACK.getBlue());
//			GL11.glDrawArrays(GL11.GL_LINES,0,(zoomX-2)*2);
//			GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
//			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//
//		}
//		}
//	}
//	
//	/**
//	 * Rendu du spectre
//	 */
//	public void renderSpectrumView() {
//		if(update)
//			update();
//		
//		if(!GLDisplay.getMode3D()){
//			GL11.glDisable(GL11.GL_TEXTURE_2D);
//			
//			GL11.glColor3ub((byte)ReadableColor.LTGREY.getRed(),(byte)ReadableColor.LTGREY.getGreen(),(byte)ReadableColor.LTGREY.getBlue());
//			GL11.glBegin(GL11.GL_QUADS);
//			glVertex3d(2*X_SIZE,ymax, 100);
//			glVertex3d(3*X_SIZE, ymax, 100);
//			glVertex3d(3*X_SIZE,ymin, 100);
//			glVertex3d(2*X_SIZE,ymin, 100);
//			GL11.glEnd();
//			
//		
//		if(sound!=null){
//			GL20.glUseProgram(shaderProgram);
//			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboVertexHandleS);
//			GL11.glVertexPointer(3,GL11.GL_FLOAT,0,0L);
//			GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
//			GL11.glColor3ub((byte)ReadableColor.BLACK.getRed(),(byte)ReadableColor.BLACK.getGreen(),(byte)ReadableColor.BLACK.getBlue());
//			GL11.glDrawArrays(GL11.GL_LINES,0,(X_SIZE)*2);
//			GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
//			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//			GL20.glUseProgram(0);
//
//		}
//		}
//	}

	
/**
 * 	
 * @param sound Le son a afficher
 */
public void setSound(Sound sound) {
	this.sound=sound;
	sound.addObserver(this);
	update=true;
	
}

/**
 * 
 * @param v
 * @return vector3f to float[]
 */
private static float[] asFloats(Vector3f v) {
	
	return new float[]{v.x,v.y,v.z};
}

/**
 * Reserve un FloatBuffer de la taille donnee
 * @param size
 * @return
 */
private static FloatBuffer reserveData(int size) {
	FloatBuffer data= BufferUtils.createFloatBuffer(size);
	return data;
}

/**
 * Mise a jour du spectre
 */
public void updateSpectrum() {
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



@Override
public void update(Observable arg0, Object arg1) {
	update=true;
	
}

/**
 * Mise a jour de tout le son
 */
public void update(){
		soundBytes = sound.getSound();
		result = sound.getSpectrum();
		updateSpectrum();
		VBOLoad();
		update=false;
		
	
}



@Override
public void paint() {
	if(update)
		update();
	setPosition();
	if(paint){
	if(!GLDisplay.getMode3D()){
	GL11.glDisable(GL11.GL_TEXTURE_2D);

	if(sound!=null){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboVertexHandle);
		GL11.glVertexPointer(3,GL11.GL_FLOAT,0,0L);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glColor3ub((byte)ReadableColor.BLACK.getRed(),(byte)ReadableColor.BLACK.getGreen(),(byte)ReadableColor.BLACK.getBlue());
		GL11.glTranslated(parameter1.getX(),parameter1.getY(),0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glDrawArrays(GL11.GL_LINES,0,(zoomX-2)*2);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	}
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
	
	return "Audio";
}

private void setparameters(Parameter parameter1,Parameter parameter2){
	this.parameter1=parameter1;
	this.parameter2=parameter2;
}

@Override
public boolean collisionCanOccure(Point point, float f) {
	// TODO Auto-generated method stub
	return false;
}

}
