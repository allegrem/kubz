package utilities;

import org.lwjgl.BufferUtils;

public class MyFloatBuffer {
	
    public static java.nio.FloatBuffer newFloatBuffer(float a, float b, float c, float d)
    {
    float[] data = new float[]{a,b,c,d};
    java.nio.FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
    fb.put(data);
    fb.flip();
    return fb;
     }

}
