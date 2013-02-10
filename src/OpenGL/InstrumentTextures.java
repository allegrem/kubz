package OpenGL;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class InstrumentTextures {
	
	public static Texture[] textureList = new Texture[6];
	private static String fileName;
	
	public static void initTexture(int i){
		textureList[i] = null;
		switch (i){
		case 0:			
			fileName = "Textures/herringbone21.jpg";
		case 1:
			fileName =  "Textures/metal021.jpg";
		
		}
		textureList[i] = loadTexture(fileName);
	}
	
	private static Texture loadTexture(String fileName){
		Texture textureToLoad = null;
		try {
			textureToLoad = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return textureToLoad;
	}

}
