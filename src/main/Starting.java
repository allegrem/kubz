package main;


import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Quadric;
import org.lwjgl.util.glu.Sphere;

//Exercice 1.2

public class Starting2 {
	
	  private boolean do_run=true; 
	    private int display_width = 480;
	    private int display_height = 480;
	    private int ratio = display_width/display_height;
	    
	    public Starting2(){
	        initDisplay();
	        initGL();
	        
	        while(do_run){
	            if(Display.isCloseRequested())
	              do_run=false;
	            render();
	            Display.update();
	        }
	        Display.destroy();
	    }
	    
	    private void render(){
	        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	          
	        // un carré:
	        /*GL11.glBegin(GL11.GL_QUADS);
	        GL11.glColor3f(1.0f,0.0f,0.0f); 
	        GL11.glVertex3f( 1.0f,-1.0f,-5.0f);   
	        GL11.glVertex3f(-1.0f,-1.0f,-5.0f);
	        GL11.glVertex3f( -1.0f,1.0f, -5.0f);
	        GL11.glVertex3f(1.0f, 1.0f, -5.0f);	   
	        GL11.glEnd();*/
	        
	        // un carré utilisant triangle-fan:
	        /*GL11.glBegin(GL11.GL_TRIANGLE_FAN);
	        GL11.glColor3f(1.0f,1.0f,1.0f);
	        GL11.glVertex3f(-1.0f,-1.0f,-5.0f);
	        GL11.glVertex3f(-1.0f,1.0f,-5.0f);
	        GL11.glVertex3f(1.0f, 1.0f, -5.0f);
	        GL11.glVertex3f(1.0f, -1.0f, -5.0f);
	        GL11.glEnd();*/
	        
	        // un carré utilisant triangle-strip
	        /*GL11.glBegin(GL11.GL_TRIANGLE_STRIP);	        
	        GL11.glColor3f(1.0f,0.0f,1.0f);
	        GL11.glVertex3f(-1.0f,1.0f,-5.0f);
	        GL11.glVertex3f(-1.0f, -1.0f, -5.0f);
	        GL11.glVertex3f(1.0f, 1.0f,-5.0f);
	        GL11.glVertex3f(1.0f, -1.0f, -5.0f);
	        GL11.glEnd();*/
	        
	        //un cube 
	        GL11.glBegin(GL11.GL_QUADS);
	        //Face 1
	        GL11.glColor3f(1.0f,0.0f,0.0f);  
	        GL11.glVertex3f( 1.0f,-1.0f,-5.0f);   
	        GL11.glVertex3f(-1.0f,-1.0f,-5.0f);
	        GL11.glVertex3f( -1.0f,1.0f, -5.0f);
	        GL11.glVertex3f(1.0f, 1.0f, -5.0f);	   
	        
	        //Face 2
	        GL11.glColor3f(1.0f,0.0f,1.0f);
	        GL11.glVertex3f( 1.0f,-1.0f,-8.0f);   
	        GL11.glVertex3f(-1.0f,-1.0f,-8.0f);
	        GL11.glVertex3f( -1.0f,1.0f, -8.0f);
	        GL11.glVertex3f(1.0f, 1.0f, -8.0f);	 
	        
	        //Face 3
	        GL11.glColor3f(1.0f,1.0f,1.0f);
	        GL11.glVertex3f( -1.0f,-1.0f,-5.0f);
	        GL11.glVertex3f(-1.0f, 1.0f, -5.0f);	   
	        GL11.glVertex3f( -1.0f,1.0f,-8.0f); 
	        GL11.glVertex3f(-1.0f, -1.0f, -8.0f);
	        
	        //Face 4
	        GL11.glColor3f(1.0f,1.0f,0.0f);
	        GL11.glVertex3f(1.0f,-1.0f,-5.0f);
	        GL11.glVertex3f(1.0f,1.0f,-5.0f);
	        GL11.glVertex3f( 1.0f,1.0f, -8.0f);
	        GL11.glVertex3f( 1.0f,-1.0f, -8.0f);
	        
	        //Face 5
	        GL11.glColor3f(0.0f,1.0f,0.0f);
	        GL11.glVertex3f(1.0f,1.0f,-5.0f);
	        GL11.glVertex3f(1.0f,1.0f,-8.0f);
	        GL11.glVertex3f( -1.0f,1.0f, -8.0f);
	        GL11.glVertex3f( -1.0f,1.0f, -5.0f);
	        
	        //Face 6
	        GL11.glColor3f(0.0f,1.0f,1.0f);
	        GL11.glVertex3f(1.0f,-1.0f,-5.0f);
	        GL11.glVertex3f(1.0f,-1.0f,-8.0f);
	        GL11.glVertex3f( -1.0f,-1.0f, -8.0f);
	        GL11.glVertex3f( -1.0f,-1.0f, -5.0f);
	        GL11.glEnd();
	      
	        
	       
	        //Sphere avec GLUSphere
	         GL11.glColor3f(0.5f, 0.9f, 0.7f);
	         Sphere sphere = new Sphere();
	         sphere.draw (0.4f,75,75);
	         GL11.glLoadIdentity();
	         GL11.glTranslatef(1.5f, -1.50f, -5.0f );
	        
	            
	        
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
	    
	    private void initGL(){    
	        /*GL viewport: nous utilisons toute la fenetre pour dessiner*/
	        GL11.glViewport(0, 0, display_width, display_height);
	        /*Matrice de projection*/
	        GL11.glMatrixMode(GL11.GL_PROJECTION);
	        GL11.glLoadIdentity();
	        GLU.gluPerspective(45.0f,(float)(ratio),0f,20.0f);
	        

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
