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
	public static Texture textureTry;
	public static Texture K;
	public static Texture U;
	public static Texture B;
	public static Texture Z;
	public static Texture ksol1;
	
	public static void initksol1(){
		ksol1=null;
		try {

			ksol1 = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("Textures/ksol2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	public static void initB(){
		B=null;
		try {

			B = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("Textures/B1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	public static void initZ(){
		Z=null;
		try {

			Z = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("Textures/Z1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}		
	
	public static void initU(){
		U=null;
		try {

			U = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("Textures/U1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}		
	
	
	public static void initK(){
		K=null;
		try {

			K = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("Textures/K1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}		
	
	/**
	 * Initilaisation de la texture du sol
	 */
	public static void initTexturePath(){
		textureSea=null;
		try {

			textureSea = TextureLoader.getTexture("JPG",ResourceLoader.getResourceAsStream("Textures/metal049.jpg"));
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
	
	/**
	 *
	 */
	public static void initTextureTry(){
		textureTry = null;
		try {
			textureTry = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream("Textures/metal091b.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
