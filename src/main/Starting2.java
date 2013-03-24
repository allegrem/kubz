package main;



import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ARBFragmentShader;

import OpenGL.Textures;



public class Starting2 {
	
	private boolean do_run = true;
	private int display_width = 640;
	private int display_height = 480;
	private float ratio = (float) display_width/display_height;
	private int angle;
	
	public Starting2(){
		initDisplay();
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
	
	private void initDisplay(){
	     try{
	            //Creation d'une fenetre permettant de dessiner avec OpenGL        	
	            Display.setDisplayModeAndFullscreen(new DisplayMode(display_width, display_height) );
	            Display.create();

	            DisplayMode mode = Display.getDisplayMode();
	        }catch(Exception e){
	            System.out.println("Error setting up display: "+ e.getMessage());
	            System.exit(0);
	        }
		
	}
	
	private void renderKubz(){
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		if (Textures.ksol1==null)
			Textures.initksol1();
		
		GL11.glLoadIdentity();
		GL11.glTranslatef(-4,-1,-13);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Textures.ksol1.getTextureID());

		
		GL11.glBegin (GL11.GL_QUADS);		
		GL11.glTexCoord2f (0, 1);
		GL11.glVertex3d (0.0, 0.0, 0.0);
		
		GL11.glTexCoord2f (1, 1);
		GL11.glVertex3d (5.0, 0.0, 0.0);
		
		GL11.glTexCoord2f (1, 0);
		GL11.glVertex3d (5.0, 5.0, 0.0);
		
		GL11.glTexCoord2f (0, 0);
		GL11.glVertex3d (0.0, 5.0, 0.0);
		GL11.glEnd();
	}
	private void renderCube(){				     
		
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		//GL11.glScalef(1, 1, (float) 0.1); scales the axes, useless!!
		GL11.glTranslatef(5, 0, -16);
    	GL11.glTranslatef(0.0f, 0.0f, -6.5f);  
      	GL11.glRotatef(angle, 1.0f, 1.0f, 1.0f);	
      	GL11.glTranslatef(0.0f, 0.0f, 6.5f);    
      	
      	angle++;
      	
      	
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		
		if(Textures.K==null)
			Textures.initK();
		if(Textures.U==null)
			Textures.initU();
		if(Textures.B==null)
			Textures.initB();
		if(Textures.Z==null)
			Textures.initZ();
		
		
						
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.K.getTextureID()); 
        GL11.glBegin(GL11.GL_QUADS);										
        //Face 1       
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( 1.0f,-1.0f,-5.0f);  
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(-1.0f,-1.0f,-5.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -1.0f,1.0f, -5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(1.0f, 1.0f, -5.0f);	
        GL11.glEnd();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.U.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);    
        //Face 2
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( 1.0f,-1.0f,-8.0f);  
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(-1.0f,-1.0f,-8.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -1.0f,1.0f, -8.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(1.0f, 1.0f, -8.0f);	
        GL11.glEnd();
       
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.B.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);
        //Face 3
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( -1.0f,-1.0f,-5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(-1.0f, 1.0f, -5.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -1.0f,1.0f,-8.0f);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(-1.0f, -1.0f, -8.0f);
        GL11.glEnd();
        
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.Z.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);
        //Face 4                     
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(1.0f,-1.0f,-5.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f(1.0f,1.0f,-5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f( 1.0f,1.0f, -8.0f);
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f( 1.0f,-1.0f, -8.0f);
        GL11.glEnd();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.B.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);
        //Face 5        
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f(1.0f,1.0f,-5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(1.0f,1.0f,-8.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -1.0f,1.0f, -8.0f);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f( -1.0f,1.0f, -5.0f);
        GL11.glEnd();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,Textures.B.getTextureID());
        GL11.glBegin(GL11.GL_QUADS);
        //Face 6       
        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f(1.0f,-1.0f,-5.0f);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(1.0f,-1.0f,-8.0f);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f( -1.0f,-1.0f, -8.0f);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f( -1.0f,-1.0f, -5.0f);  
        GL11.glEnd();
                    
       GL11.glPopMatrix();
	}
	
	private void initGL(){
        /*GL viewport: nous utilisons toute la fenetre pour dessiner*/
        GL11.glViewport(0, 0, display_width, display_height);
        /*Matrice de projection (3D vers 3D): utilisationd'une projection perspective*/
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f,ratio,1f,50.0f);
        

        /*Matrice de modele (e.g. positionnement initial de la "camera" )*/
        GL11.glMatrixMode(GL11.GL_MODELVIEW);        
        GL11.glLoadIdentity();
        
        
        /*Diverses options OpenGL*/
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
}
