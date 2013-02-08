package utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

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

    /**
     * Convert the buffered image to a texture
     */
    public static ByteBuffer convertImageData(BufferedImage bufferedImage) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace
                .getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 },
                true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

        raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                bufferedImage.getWidth(), bufferedImage.getHeight(), 4, null);
        texImage = new BufferedImage(glAlphaColorModel, raster, true,
                new Hashtable<Object, Object>());

        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, 256, 256);
        g.drawImage(bufferedImage, 0, 0, null);

        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer())
                .getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

}
