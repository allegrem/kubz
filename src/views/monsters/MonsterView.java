package views.monsters;

import java.util.ArrayList;

import map2.Map;

import org.lwjgl.util.ReadableColor;

import utilities.Maths;
import utilities.Point;
import utilities.Vector;
import views.interfaces.DisplayableChild;
import views.interfaces.DisplayableFather;


/**
 * Une unite d'un joueur ou un monstre
 * 
 * @author paul
 * 
 */
public abstract class MonsterView implements DisplayableFather{
	protected static final double size= 30;
	protected static final double height = 30;
	private Point position;
	private Point positionToGo=new Point(0,0);
	private double angle=0;
	private Map map;
	private ReadableColor color;
	protected ReadableColor actualColor;
	protected ArrayList<DisplayableChild> children= new ArrayList<DisplayableChild>();
	protected int duration=0;
	private double startingTime=0;
	private double pause=0;
	private boolean moveFinished=true;

	/**
	 * Nouveau monstre
	 * @param position Sa position (centre)
	 * @param color Sa couleur
	 */
	public MonsterView(Point position, ReadableColor color) {
		this.map=Map.getMap();
		this.position = position;
		positionToGo.setLocation(position);
		this.color = color;
		actualColor = color;
	}

	@Override
	public abstract void paint();

	/**
	 * deplacement relatif du monstre
	 * @param dx
	 * @param dy
	 */
	public void translate(double dx, double dy) {
		positionToGo.translate(dx, dy);
		moveFinished=false;
	}

	/**
	 * Definition de la position du monstre a partir
	 * de nouvelles coordonnees
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		positionToGo.move(x, y);
		moveFinished=false;

	}

	/**
	 * Definition de la position du monstre a partir de
	 * celle d'un point
	 * @param p
	 */
	public void setLocation(Point p) {
		positionToGo.setLocation(p);
		moveFinished=false;

	}

	@Override
	public double getX() {
		return position.getX();
	}

	@Override
	public double getY() {

		return position.getY();
	}


	@Override
	public boolean isInZone(Point p){
		double x1 = position.getX()-size/2, x2 = position.getX()+size/2;
		double y1 = position.getY()-size/2, y2 = position.getY()+size/2;
		
		double pX = p.getX();
		double pY = p.getY();
		
		
		if (pX>=x1 && pX<=x2 && pY>=y1 && pY<=y2)
			return true;
		return false;
	}
	
	/**
	 * 
	 * @return La couleur du monstre
	 */
	public ReadableColor getColor(){
		return actualColor;
	}
	
	@Override
	public void setColor(ReadableColor color){
		this.actualColor = color;
	}
	
	@Override
	public String getCharac(){
		return " "+position.getX()+" "+position.getY()+
				" "+actualColor.getRed()+" "+actualColor.getGreen()+" "+actualColor.getBlue();
	}

	@Override
	public synchronized ArrayList<DisplayableChild> getChildren() {
		return children;
	}

	@Override
	public synchronized  void addChild(DisplayableChild child) {
		children.add(child);
		child.setFather(this);
		
	}
	
	@Override
	public synchronized void removeChild(DisplayableChild child){
		children.remove(child);
	}

	@Override
	public int getTimeOut() {
		return duration;
	}

	@Override
	public void setTimeOut(int time) {
		this.duration=time;
		
	}
	
	public synchronized void paintChildren(){
		ArrayList<DisplayableChild> childrenDead = new ArrayList<DisplayableChild>();
		for(DisplayableChild child:children){
			if(child.isDead()){
				childrenDead.add(child);
			}else
				child.paint();
		}
		
		for(DisplayableChild child:childrenDead){
			children.remove(child);
		}
	}
	
	@Override
	public  double getSize(){
		return size;
	}
	
	@Override
	public double getHeight(){
		return height;
	}
	
	@Override
	public double getAngle(){
		return angle;
	}
	
	public void actualizePosition(){
		if((positionToGo.getX()-position.getX())!=0 || (positionToGo.getY()-position.getY())!=0){
			if(System.currentTimeMillis()-startingTime>pause){
				startingTime=System.currentTimeMillis();
			Vector dir=Maths.makeVector(position, positionToGo);
			if (dir.norme()>=2){
				Maths.normalize(dir);
				position.translate(2*dir.getX(), 2*dir.getY());
			}else{
				moveFinished=true;
			}
				
			}
		
			
		}
		
	}

	public boolean moveFinished(){
		return moveFinished;
	}
	
}
