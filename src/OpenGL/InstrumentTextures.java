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
			fileName = "Textures/1360563702_tmp_violin.jpg";
			break;
		case 1:
			fileName =  "Textures/bell.jpg";
			break;
		case 2:
			fileName = "Textures/flute.jpg";
			break;
		case 3:
			fileName = "Textures/FM2.jpg";
			break;
		case 4:
			fileName = "Textures/xylophone.jpg";
			break;
		case 5:
			fileName = "Textures/piano.jpg";
			break;			
		default:
			//throw exception... will be written later
			break;
		
		}
		try {
			textureList[i] = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*private static void loadTexture(Texture texture,String fileName){
		
		try {
			texture = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}
