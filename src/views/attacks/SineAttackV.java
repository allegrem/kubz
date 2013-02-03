package views.attacks;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.glu.GLU;

import utilities.Point;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;


public class SineAttackV implements DisplayableChild{
	
	private int direction;
	private DisplayableFather father; 
	private long time = 0;
	private int max = 0;
	private boolean dead=false;
	
	
	
	public SineAttackV(int direction, DisplayableFather father){
		this.direction = direction; 
		this.father = father;
	}
	
	@Override
	public void paint(){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glLineWidth(4);
		
		for (int i=0;i<max;i++){
			
			GL11.glPushMatrix();
			GL11.glRotated(-19, father.getX(), father.getY() ,1);

			GL11.glBegin(GL11.GL_LINES);			
			GL11.glColor4ub((byte)205,(byte) 0, (byte)253, (byte) 255);
			GL11.glVertex3d(father.getX()+3*i, father.getY()+15*Math.sin(i), 0);
			GL11.glVertex3d(father.getX()+3*(i+1), father.getY()+15*Math.sin((i+1)), 0);
			GL11.glRotated(3, father.getX(), father.getY() , 1);
			/*GL11.glColor4ub((byte)205,(byte) 0, (byte)253, (byte) 255);
			GL11.glVertex3d(father.getX()+3*i, father.getY()+15*Math.sin(i), 0);
			GL11.glVertex3d(father.getX()+3*(i+1), father.getY()+15*Math.sin((i+1)), 0);*/
			GL11.glEnd();
			
			GL11.glRotated(19, father.getX(), father.getY() , 1);
			GL11.glPopMatrix();
			
		}
	
		if(System.currentTimeMillis()-time>10 && max < 50){
			max++;		
			time=System.currentTimeMillis();
		}
		
		
		
		
		
		
	}
	
	/*private void erase(){
		GL11.glClearStencil(0);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		 GL11.glStencilFunc (GL11.GL_ALWAYS, 1, 1);
		   GL11.glStencilOp (GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		   
		   GL11.glBegin(GL11.GL_QUADS);
		   GL11.glVertex3d(MapCreator.display_height/2, 0, 0);
		   GL11.glVertex3d(MapCreator.display_height, MapCreator.display_width, 0);
		   GL11.glEnd();
		   
		   GL11.glDisable(GL11.GL_STENCIL_TEST);  ??? :(
		   
	}*/
	
	@Override
	public int getTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTimeOut(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInZone(Point mousePoint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColor(ReadableColor color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCharac() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFather(DisplayableFather father) {
		this.father = father;
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return dead;
	}

}
