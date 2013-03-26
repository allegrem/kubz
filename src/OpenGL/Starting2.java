package OpenGL;

import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ARBFragmentShader;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;

public class Starting2 implements DisplayableFather {
	
	private boolean do_run = true;
	private int angle;
	private float cote = 90.0f;
	private long waitingTime = 10;
	private long startingTime;
	
	public Starting2(){
		startingTime = System.currentTimeMillis();				
		/*initDisplay();
		initGL();
		
		
		while (do_run){
			if (Display.isCloseRequested())
				do_run = false;			
			clear();
			renderKubz();
			renderCube();
			
			Display.update();
			Display.sync(120);
		}
		
		Display.destroy();
	}
	
	
	private void clear(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	l
	     try{
	            //Creation d'une fenetre permettant de dessiner avec OpenGL        	
	            
	            Display.create();

	            DisplayMode mode = Display.getDesktopDisplayMode();
	            Display.setDisplayModeAndFullscreen(mode);
	        }catch(Exception e){
	            System.out.println("Error setting up display: "+ e.getMessage());
	            System.exit(0);
	        }*/
		
	}
	
	private void renderKubz(){
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);				
		GL11.glTranslatef(200, 100, -90);
		
		GL11.glScalef(90, 90, 1);
		
		if (Textures.ksol1==null)
			Textures.initksol1();
						
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Textures.ksol1.getTextureID());
		
		GL11.glBegin (GL11.GL_QUADS);		
		GL11.glTexCoord2f (0, 0);
		GL11.glVertex3d (0.0, 0.0, 0.0);
		
		GL11.glTexCoord2f (1, 0);
		GL11.glVertex3d (5.0, 0.0, 0.0);
		
		GL11.glTexCoord2f (1, 1);
		GL11.glVertex3d (5.0, 5.0, 0.0);
		
		GL11.glTexCoord2f (0, 1);
		GL11.glVertex3d (0.0, 5.0, 0.0);
		GL11.glEnd();
	}
	private void renderCube(){				     
		GL11.glDisable(GL11.GL_LIGHTING);
	    GL11.glDisable(GL11.GL_COLOR_MATERIAL);		
		
	    GL11.glPushMatrix();
		 
		/*GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GLU.gluPerspective(75.0f,-700/500,1f,10000.0f);*/

		GL11.glLoadIdentity();
		GL11.glEnable(GL11.GL_TEXTURE_2D);	
		//GL11.glScalef(1, 1, (float) 0.1); scales the axes, useless!!
		//GL11.glTranslatef(65, 0, -16);
		GL11.glTranslatef(1000,400,-90);
		GL11.glTranslatef(0.0f, 0.0f, -6.5f);  
      	GL11.glRotatef(angle, 1.0f, 1.0f, 1.0f);	
      	GL11.glTranslatef(0.0f, 0.0f, 6.5f);  
      	
      	
      	GL11.glScalef(1, 1, 30);
      	
      	
      	
      	if (System.currentTimeMillis()-startingTime>=waitingTime){
      		startingTime = System.currentTimeMillis();      	
      		angle++;
      	}     	      
      	
		if(Textures.K==null)
			Textures.initK();
		if(Textures.U==null)
			Textures.initU();
		if(Textures.B==null)
			Textures.initB();
		if(Textures.Z==null)
			Textures.initZ();

		/*GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.K.getTextureID()); 
        GL11.glBegin(GL11.GL_QUADS);	               
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( cote,-cote,-5.0f);  
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(-cote,-cote,-5.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -cote,cote, -5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(cote, cote, -5.0f);	
        GL11.glEnd();*/
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.K.getTextureID()); 
        GL11.glBegin(GL11.GL_QUADS);	      
        //Face 1       
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( cote,-cote,-5.0f);  
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(-cote,-cote,-5.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -cote,cote, -5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(cote, cote, -5.0f);	
        GL11.glEnd();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.U.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);    
        //Face 2
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( cote,-cote,-8.0f);  
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(-cote,-cote,-8.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -cote,cote, -8.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(cote, cote, -8.0f);	
        GL11.glEnd();
       
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.B.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);
        //Face 3
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( -cote,-cote,-5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(-cote, cote, -5.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -cote,cote,-8.0f);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(-cote, -cote, -8.0f);
        GL11.glEnd();
        
        
       GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.Z.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);
        //Face 4                     
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(cote,-cote,-5.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f(cote,cote,-5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f( cote,cote, -8.0f);
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( cote,-cote, -8.0f);
        GL11.glEnd();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.K.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);
        //Face 5        
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f(cote,cote,-5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(cote,cote,-8.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -cote,cote, -8.0f);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(-cote,cote, -5.0f);
        GL11.glEnd();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.B.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);
        //Face 6       
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f(cote,-cote,-5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(cote,-cote,-8.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -cote,-cote, -8.0f);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f( -cote,-cote, -5.0f);  
        GL11.glEnd();
                    
       GL11.glPopMatrix();
	}
	
	/* private void initGL(){        
        GL11.glViewport(0, 0, display_width, display_height);       
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f,ratio,1f,50.0f);
        

       
        GL11.glMatrixMode(GL11.GL_MODELVIEW);        
        GL11.glLoadIdentity();
        
                
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}
	
	public static void main(String[] args) {
		new Starting2();
	}
*/

	@Override
	public void paint() {		
		renderKubz();
		renderCube();
		
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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean collisionCanOccure(Point point, float f) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public ArrayList<DisplayableChild> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addChild(DisplayableChild object) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeChild(DisplayableChild child) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public double getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getSize() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setUnTracked(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
