package objLoader;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
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

	private int objectDisplayList;
	private int shaderProgram;
	private  int vboVertexHandle;
	private  int vboNormalHandle;
	private Model  m;
	
	public void load(String fichier){
	
		objectDisplayList=GL11.glGenLists(1);
		GL11.glNewList(objectDisplayList,GL11.GL_COMPILE);
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
	
	public void render(){
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glTranslated(500, 400,0);
		GL11.glScalef((float)100, (float)100, (float)100);
		GL11.glColor3ub((byte)Color.RED.getRed(), (byte)Color.RED.getGreen(),(byte)Color.RED.getBlue());
		
		GL11.glCallList(objectDisplayList);
		
		GL11.glLoadIdentity();
		GL11.glPopMatrix();

		
	}
	
	public void VBOLoad(String fichier){
		 vboVertexHandle= GL15.glGenBuffers();
		 vboNormalHandle= GL15.glGenBuffers();
		m=null;
		 try {
				m=ObjLoader.loadModel(new File(fichier));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		 FloatBuffer vertices= reserveData(m.faces.size()*9);
		 FloatBuffer normals= reserveData(m.faces.size()*9);
		 for (Face face: m.faces){
			 vertices.put( asFloats(m.vertices.get((int) face.vertex.x-1)));
			 vertices.put( asFloats(m.vertices.get((int) face.vertex.y-1)));
			 vertices.put( asFloats(m.vertices.get((int) face.vertex.z-1)));
			 
			 normals.put( asFloats(m.vertices.get((int) face.vertex.x-1)));
			 normals.put( asFloats(m.vertices.get((int) face.vertex.y-1)));
			 normals.put( asFloats(m.vertices.get((int) face.vertex.z-1)));	 
		 }
		 vertices.flip();
		 normals.flip();
		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboVertexHandle);
		 GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		 
		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboNormalHandle);
		 GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normals, GL15.GL_STATIC_DRAW);
	
		 GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void renderVBO(ReadableColor color,int x, int y, int z, int a){
		GL20.glUseProgram(shaderProgram);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboVertexHandle);
		GL11.glVertexPointer(3,GL11.GL_FLOAT,0,0L);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboNormalHandle);
		GL11.glNormalPointer(GL11.GL_FLOAT,0,0L);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glColor3ub((byte)color.getRed(), (byte)color.getGreen(),(byte)color.getBlue());
		GL11.glTranslated(x, y,z);
		GL11.glRotatef(a, 0,0, 1);
		GL11.glScalef((float)15, (float)15, (float)40);
		GL11.glDrawArrays(GL11.GL_TRIANGLES,0,m.faces.size()*3);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL20.glUseProgram(0);
	}

	private float[] asFloats(Vector3f v) {
	
		return new float[]{v.x,v.y,v.z};
	}

	private FloatBuffer reserveData(int size) {
		FloatBuffer data= BufferUtils.createFloatBuffer(size);
		return data;
	}
	
	private void cleanUp(){
		
	}
	

}
