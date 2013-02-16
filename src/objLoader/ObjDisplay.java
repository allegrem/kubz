package objLoader;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;

public class ObjDisplay {
	public static final Objet ROUNDED_CUBE=Objet.ROUNDED_CUBE;
	
	
	public static enum Objet
	{
		ROUNDED_CUBE("objets/roundedCube.obj");
	
	    // Membres :
	  
	    private boolean initialized;
	    private final String address;
	    private  int objectDisplayList;
		private  int shaderProgram;
		private  int vboVertexHandle;
		private  int vboNormalHandle;
		private int vertexShader;
		private  int fragmentShader;
		private  Model  m;
	 
	  private Objet(String address){
		  initialized=false;
		  this.address=address;
	  }
	  
	  public void initialize(){
		  VBOLoad(this,address);
		   shaderProgram = GL20.glCreateProgram();
	       vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
	       fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
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
		  
		  
			initialized=true;
		  
	  }
	    
	  public void render(ReadableColor color,int x, int y, int z, int a){
		  if(!initialized)
			  initialize();
		  renderVBO(this,color,x,y,z,a);
	  }

	
	    
	};

	
	public static void load(Objet obj,String fichier){
	
		obj.objectDisplayList=GL11.glGenLists(1);
		GL11.glNewList(obj.objectDisplayList,GL11.GL_COMPILE);
		{
		Model m=null;
		try {
			m=ObjLoader.loadModel(new File(fichier));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		for(Face face:m.faces){
			Vector3f n1=m.normals.get((int) face.normal.x-1);
			GL11.glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1=m.vertices.get((int) face.vertex.x-1);
			GL11.glVertex3f(v1.x, v1.y, v1.z);
			
			Vector3f n2=m.normals.get((int) face.normal.y-1);
			GL11.glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2=m.vertices.get((int) face.vertex.y-1);
			GL11.glVertex3f(v2.x, v2.y, v2.z);
			
			Vector3f n3=m.normals.get((int) face.normal.z-1);
			GL11.glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3=m.vertices.get((int) face.vertex.z-1);
			GL11.glVertex3f(v3.x, v3.y, v3.z);
			 
			
			
		}
			
		GL11.glEnd();
		}
		GL11.glEndList();
	}
	
	public static void render(Objet obj){
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glTranslated(500, 400,0);
		GL11.glScalef((float)100, (float)100, (float)100);
		GL11.glColor3ub((byte)Color.RED.getRed(), (byte)Color.RED.getGreen(),(byte)Color.RED.getBlue());
		
		GL11.glCallList(obj.objectDisplayList);
		
		GL11.glLoadIdentity();
		GL11.glPopMatrix();

		
	}
	
	public static void VBOLoad(Objet obj,String fichier){
		 obj.vboVertexHandle= GL15.glGenBuffers();
		 obj.vboNormalHandle= GL15.glGenBuffers();
		 obj.m=null;
		 try {
			 obj.m=ObjLoader.loadModel(new File(fichier));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		 FloatBuffer vertices= reserveData(obj.m.faces.size()*9);
		 FloatBuffer normals= reserveData(obj.m.faces.size()*9);
		 for (Face face: obj.m.faces){
			 vertices.put( asFloats(obj.m.vertices.get((int) face.vertex.x-1)));
			 vertices.put( asFloats(obj.m.vertices.get((int) face.vertex.y-1)));
			 vertices.put( asFloats(obj.m.vertices.get((int) face.vertex.z-1)));
			 
			 normals.put( asFloats(obj.m.vertices.get((int) face.vertex.x-1)));
			 normals.put( asFloats(obj.m.vertices.get((int) face.vertex.y-1)));
			 normals.put( asFloats(obj.m.vertices.get((int) face.vertex.z-1)));	 
		 }
		 vertices.flip();
		 normals.flip();
		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,obj.vboVertexHandle);
		 GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		 
		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,obj.vboNormalHandle);
		 GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normals, GL15.GL_STATIC_DRAW);
	
		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public static void renderVBO(Objet obj,ReadableColor color,int x, int y, int z, int a){
		GL20.glUseProgram(obj.shaderProgram);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,obj.vboVertexHandle);
		GL11.glVertexPointer(3,GL11.GL_FLOAT,0,0L);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, obj.vboNormalHandle);
		GL11.glNormalPointer(GL11.GL_FLOAT,0,0L);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glColor3ub((byte)color.getRed(), (byte)color.getGreen(),(byte)color.getBlue());
		GL11.glTranslated(x, y,z);
		GL11.glRotatef(a, 0,0, 1);
		GL11.glScalef((float)15, (float)15, (float)40);
		GL11.glDrawArrays(GL11.GL_TRIANGLES,0,obj.m.faces.size()*3);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL20.glUseProgram(0);
	}

	private static float[] asFloats(Vector3f v) {
	
		return new float[]{v.x,v.y,v.z};
	}

	private static FloatBuffer reserveData(int size) {
		FloatBuffer data= BufferUtils.createFloatBuffer(size);
		return data;
	}
	
	public static void cleanUp(){
		GL15.glDeleteBuffers(ROUNDED_CUBE.vboVertexHandle);
		GL15.glDeleteBuffers(ROUNDED_CUBE.vboNormalHandle);
		GL20.glDeleteProgram(ROUNDED_CUBE.shaderProgram);
		GL20.glDeleteShader(ROUNDED_CUBE.vertexShader);
		GL20.glDeleteShader(ROUNDED_CUBE.fragmentShader);
		
		
	}
	

}
