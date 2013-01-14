package utilities;

import org.lwjgl.BufferUtils;

public class MyFloatBuffer {
	
    public static java.nio.FloatBuffer newFloatBuffer4(float a, float b, float c, float d)
    {
    float[] data ={a,b,c,d};
    java.nio.FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
    fb.put(data);
    fb.flip();
    return fb;
     }
    
    public static java.nio.FloatBuffer newFloatBuffer3(float a, float b, float c)
    {
    float[] data ={a,b,c};
    java.nio.FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
    fb.put(data);
    fb.flip();
    return fb;
     }

}
