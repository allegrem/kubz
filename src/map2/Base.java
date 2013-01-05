/**
 * Classe correspondant a la Base d'un joueur, repr�sent�e par un cercle.
 * @author valeh
 */
package map2;



import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.Disk;

public class Base {
	

	private static float radius = 80.0f;   //On code le rayon des bases "en dur".

	

	private Point center;
	private ReadableColor color;
	
	public Base(Point center,ReadableColor color){
		this.center = center;
		this.color=color;
	}
	
	public void paint(){	

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Disk disk1 =new Disk();
		Disk disk2=new Disk();
		GL11.glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());		
		GL11.glTranslated(center.getX(), center.getY(), 0);
		disk2.draw(0f, radius, 50, 1);
		GL11.glColor3ub((byte)(color.getRed()*90/100),(byte)(color.getGreen()*90/100),(byte)(color.getBlue()*90/100));
		disk1.draw(radius-5, radius, 50, 1);
		GL11.glTranslated(-center.getX(), -center.getY(), 0);

	}
	
	public boolean isInZone(Point p){
		double x1 = center.getX()-radius/2, x2 = center.getX()+radius/2;
		double y1 = center.getY()-radius/2, y2 = center.getY()+radius/2;
		double pX = p.getX();
		double pY = p.getY();
		
		if (pX>=x1 && pX<=x2 && pY>=y1 && pY<=y2)
			return true;
		return false;
	}
	public ReadableColor getColor(){
		return color;
	}
	public void setColor(ReadableColor color){
		this.color = color;
	}
	public String getCharac(){
		ReadableColor color = this.getColor();
		return center.getX()+" "+center.getY()+
				"  "+color.getRed()+" "+color.getGreen()+" "+color.getBlue();
		
	}
	

}
