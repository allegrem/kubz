package objLoader;

import org.lwjgl.util.vector.Vector3f;

/**
 * Un sommet
 * @author paul
 *
 */
public class Vertex {
	public Vector3f vertex= new Vector3f();
	public Vector3f normal= new Vector3f();
	
	public Vertex(Vector3f vertex, Vector3f normal){
		this.vertex=vertex;
		this.normal=normal;
		
	}
	
}
