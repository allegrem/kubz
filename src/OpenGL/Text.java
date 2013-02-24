package OpenGL;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.font.effects.ShadowEffect;

public class Text {
	Font awFont= new Font("Times New Roman", Font.PLAIN,24);
	UnicodeFont font =new UnicodeFont(awFont);
	
	public Text(){
	font.addAsciiGlyphs();
	font.addGlyphs(400, 600);
	font.getEffects().add(new ColorEffect());
	try {
		font.loadGlyphs();
	} catch (SlickException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	
	public void glPrint(int mx, int my, ReadableColor mColor, String message) {
		Color color=new Color(mColor.getRed(),mColor.getGreen(),mColor.getBlue(),mColor.getAlpha());
		glMatrixMode(GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glTranslated(mx,my,10);
		font.drawString(0, 0, message,color);
		GL11.glLoadIdentity();
		GL11.glPopMatrix();
		
	}

}
