package objLoader;

import java.util.ArrayList;

import org.lwjgl.util.vector.*;

/**
 * Le model importe depuis un fichier .obj
 * @author paul
 *
 */
public class Model {
	public ArrayList<Vector3f> vertices =new ArrayList<Vector3f>();
	public ArrayList<Vector3f> normals =new ArrayList<Vector3f>();
	public ArrayList<Vertex> faces =new ArrayList<Vertex>();
	
	public Model(){
		
	}

}
