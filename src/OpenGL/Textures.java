package OpenGL;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Textures {
	public static Texture texturePath;
	public static Texture textureWall;
	
	public static void initTexturePath(){
		texturePath=null;
		try {
			texturePath = TextureLoader.getTexture("JPG",ResourceLoader.getResourceAsStream("/home/paul/git/projet-2012-11/herringbone21.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void initTextureWall(){
		textureWall=null;
		try {
			textureWall = TextureLoader.getTexture("JPG",ResourceLoader.getResourceAsStream("/home/paul/git/projet-2012-11/wall049.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
