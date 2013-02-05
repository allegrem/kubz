package utilities;

import org.lwjgl.BufferUtils;

/**
 * Creation de buffers necessaires
 * pour l'eclairage
 * @author paul
 *
 */
public class MyBuffer {
	
	/**
	 * Buffer de quatre float
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return le buffer
	 */
    public static java.nio.FloatBuffer newFloatBuffer4(float a, float b, float c, float d)
    {
    float[] data ={a,b,c,d};
    java.nio.FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
    fb.put(data);
    fb.flip();
    return fb;
     }
    
    /**
     * Buffer de trois float
     * (Ne marche pas. Pourquoi ???)
     * @param a
     * @param b
     * @param c
     * @return le buffer
     */
    public static java.nio.FloatBuffer newFloatBuffer3(float a, float b, float c)
    {
    float[] data ={a,b,c};
    java.nio.FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
    fb.put(data);
    fb.flip();
    return fb;
     }

}
