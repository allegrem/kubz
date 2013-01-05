/**
 * Unit� Monstre de type cercle.
 * @author valeh
 */

package map2;



import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;

public class CircleUnit extends Unit {
	private Cylinder cylinder=new Cylinder();


	public CircleUnit(Point position, ReadableColor color,Map map) {
		super(position, color,map);

	}

	@Override
	public void paint() {

		GL11.glDisable(GL11.GL_TEXTURE_2D);
	
		GL11.glColor3ub((byte)actualColor.getRed(),(byte)actualColor.getGreen(),(byte)actualColor.getBlue());
	
		GL11.glTranslated(getX(), getY(), 0);
		cylinder.draw((float)(size/2),0 ,height, 50, 1);
		GL11.glTranslated(-getX(), -getY(), 0);
	
	}
	
	public boolean isInZone(Point p){
		double pX = p.getX();
		double pY = MapCreator.display_height-p.getY();
		/*
		 * On calcule la distance du centre au point
		 */
		double d=Math.hypot(getX()-pX,getY()-pY);
		
		if(d<=size/2)
			return true;
		return false;
	}

	@Override
	public String getType() {
		return "C";
	}


}
