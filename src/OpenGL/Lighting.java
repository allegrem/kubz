package OpenGL;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.opengl.GL11;

import utilities.MyBuffer;
import utilities.Point;
/**
 * Classe servant à gérer l'éclairage
 * @author paul
 *
 */
public class Lighting {
	private GLDisplay display;
	

	
	public Lighting(GLDisplay display) {
		this.display = display;
	}

	public void enable(){
		/*
		 * Réglages de l'éclairage
		 */
		//reminder : diffuse,specular,ambient(,emissive)
		glEnable(GL11.GL_LIGHTING);
		glEnable(GL11.GL_LIGHT0);  //one source of light only
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);  //which face will reflect light+ambient(lOff) and diffuse(lOn) preferred to have same value
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT,MyBuffer.newFloatBuffer4(0.8f,0.8f,0.8f,1.0f)); //the amount of global light emitted
		//GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE,GL11.GL_TRUE);  //not necessary since we don't need to see the back of the faces
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION,MyBuffer.newFloatBuffer4(-20.0f,-20.0f,20.0f,1.0f));//Position et type de l'éclairage
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_SPOT_DIRECTION,MyBuffer.newFloatBuffer4(display.getDisplay_width()/2,display.getDisplay_height()/2,0,0));//Direction de l'éclairage
		
	}

	public void rotateLighting(int angle){
		glMatrixMode(GL_MODELVIEW);
		GL11.glPopMatrix();
		
		GL11.glTranslated(display.getDisplay_width() / 2, display.getDisplay_height() / 2, 0);
		GL11.glRotated(angle, 0, 0, 1);
		GL11.glTranslated(-display.getDisplay_width()/ 2, -display.getDisplay_height() / 2, 0);
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION,MyBuffer.newFloatBuffer4(-20.0f,-20.0f,20.0f,1.0f));//Position et type de l'éclairage
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_SPOT_DIRECTION,MyBuffer.newFloatBuffer4(display.getDisplay_width()/2,display.getDisplay_height()/2,0,0));//Direction de l'éclairage
		
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
	}
	
	public void placeLighting(float x,float y, float z){
		glMatrixMode(GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION,MyBuffer.newFloatBuffer4(x,y,z,1.0f));//Position et type de l'éclairage
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
	}

	public void setLightDirection(float x,float y, float z){
		glMatrixMode(GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glLight(GL11.GL_LIGHT0,GL11.GL_SPOT_DIRECTION,MyBuffer.newFloatBuffer4(x,y,z,0));//Direction de l'éclairage
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
	}
}
