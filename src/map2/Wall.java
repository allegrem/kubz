package map2;

import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import org.lwjgl.util.Color;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadableColor;

public class Wall {
	private ReadableColor color=Color.GREY;
	private Point extremity1;
	private Point extremity2;
	private int thickness;
	private final int height=10;
	private Point[] sommets=new Point[4];
	
	
	public Wall(Point extremity1, Point extremity2, int thickness){
		this.extremity1=extremity1;
		this.extremity2=extremity2;
		this.thickness=thickness;
		initializeSommets();
		computeSommets();
	}
	
	private void initializeSommets() {
		for(int i=0;i<2;i++){
			sommets[i]=new Point(extremity1.getX(),extremity1.getY());
		}
		for(int i=2;i<4;i++){
			sommets[i]=new Point(extremity2.getX(),extremity2.getY());
		}
		
	}

	private void computeSommets() {
		sommets[0].translate((int)(-1/(Math.hypot(1,extremity1.getX()/extremity1.getY()))*thickness/2),
				(int)(extremity1.getX()/extremity1.getY()*1/(Math.hypot(1,extremity1.getX()/extremity1.getY()))*thickness/2));
		sommets[1].translate((int)(1/(Math.hypot(1,extremity1.getX()/extremity1.getY()))*thickness/2),
				(int)(-extremity1.getX()/extremity1.getY()*1/(Math.hypot(1,extremity1.getX()/extremity1.getY()))*thickness/2));
		sommets[2].translate((int)(-1/(Math.hypot(1,extremity2.getX()/extremity2.getY()))*thickness/2),
				(int)(extremity2.getX()/extremity2.getY()*1/(Math.hypot(1,extremity2.getX()/extremity2.getY()))*thickness/2));
		sommets[3].translate((int)(1/(Math.hypot(1,extremity2.getX()/extremity2.getY()))*thickness/2),
				(int)(-extremity2.getX()/extremity2.getY()*1/(Math.hypot(1,extremity2.getX()/extremity2.getY()))*thickness/2));
	
	}

	public void paint() {
		
		
		
		
		// Chargement de la couleur du mur
		glColor3ub((byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue());
		
		glVertex3d(sommets[0].getX(), sommets[0].getY(), 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(), 0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(),height);
		glVertex3d(sommets[0].getX(), sommets[0].getY(),height);

		
		glVertex3d(sommets[0].getX(), sommets[0].getY(),height);
		glVertex3d(sommets[1].getX(), sommets[1].getY(),height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(),height);
		glVertex3d(sommets[3].getX(), sommets[3].getY(),height);

		glVertex3d(sommets[3].getX(), sommets[3].getY(),height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(),height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(),0);
		glVertex3d(sommets[3].getX(), sommets[3].getY(),0);

	
		glVertex3d(sommets[3].getX(), sommets[3].getY(),0);
		glVertex3d(sommets[2].getX(), sommets[2].getY(),0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(),0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(),0);

		glVertex3d(sommets[1].getX(), sommets[1].getY(),0);
		glVertex3d(sommets[1].getX(), sommets[1].getY(),height);
		glVertex3d(sommets[3].getX(), sommets[3].getY(),height);
		glVertex3d(sommets[3].getX(), sommets[3].getY(),0);


		glVertex3d(sommets[0].getX(), sommets[0].getY(),0);
		glVertex3d(sommets[0].getX(), sommets[0].getY(),height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(),height);
		glVertex3d(sommets[2].getX(), sommets[2].getY(),0);
		
	}

}
