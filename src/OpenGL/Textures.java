package OpenGL;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Chargement des differentes textures utilisees
 * @author paul
 *
 */
public class Textures {
	public static Texture textureSea;
	public static Texture textureWall;
	
	/**
	 * Initilaisation de la texture du sol
	 */
	public static void initTexturePath(){
		textureSea=null;
		try {

			textureSea = TextureLoader.getTexture("JPG",ResourceLoader.getResourceAsStream("Textures/sea.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Initialisation de la texture des murs
	 */
	public static void initTextureWall(){
		textureWall=null;
		try {
			textureWall = TextureLoader.getTexture("JPG",ResourceLoader.getResourceAsStream("Textures/metal049.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
