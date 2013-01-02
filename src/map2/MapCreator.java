package map2;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;


public class MapCreator extends Observable {
	private int display_width;
	private int display_height;
	private int x;
	private int y;
	private ArrayList<Unit> units=new ArrayList<Unit>();
	private ArrayList<Base> bases=new ArrayList<Base>();
	private ArrayList<Wall> walls=new ArrayList<Wall>();
	private boolean rightClicked =true;
	private boolean leftClicked =true;
	private Map map;
	
	public MapCreator(int display_width,int display_height){
		this.display_height=display_height;
		this.display_width=display_width;
		map=new Map(walls, units, bases, display_width, display_height);
		addObserver(map);
		
		
	}
	
	public void checkInput(){
		
		x=Mouse.getX();
		y=display_height-Mouse.getY();
		
		if(!Mouse.isButtonDown(0))
			leftClicked=true;
		
		if(!Mouse.isButtonDown(1))
			rightClicked=true;
		
		if(Mouse.isButtonDown(1) && rightClicked ){
			
			if (Keyboard.isKeyDown(Keyboard.KEY_U)&& units.size()>0){
				units.remove(units.size()-1);
				setChangedU();
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_W)&& walls.size()>0){
				walls.remove(walls.size()-1);
				setChangedW();
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_B)&& bases.size()>0){
				bases.remove(bases.size()-1);
				setChangedB();
			}
				
			
			rightClicked=false;
			notifyObserversU(units);
			notifyObserversB(bases);
			notifyObserversW(walls);
			
			
		}
		
		if(Mouse.isButtonDown(0) && leftClicked){
			if (Keyboard.isKeyDown(Keyboard.KEY_C)){
				units.add(new SquareUnit(new Point(x,y),Color.BLUE));
				setChangedU();
			}
				
			else if (Keyboard.isKeyDown(Keyboard.KEY_T)){
				units.add(new ShapeUnit(new Point(x,y),Color.GREEN));
				setChangedU();
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_B)){
				bases.add(new Base(new Point(x,y)));
				setChangedB();
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_W)){
				int size=walls.size();
				int ix=x;
				int iy=y;
				walls.add(new Wall(new Point(ix,iy), new Point(x+20,y+30),20,Wall.NORMAL));
				while(Mouse.isButtonDown(0)){
					if(Math.hypot(Mouse.getDX(),Mouse.getDY())>1){
					x=Mouse.getX();
					y=display_height-Mouse.getY();	
					walls.remove(size);
					if(Keyboard.isKeyDown(Keyboard.KEY_H)){
						walls.add(size,new Wall(new Point(ix,iy), new Point(x,y),20,Wall.HORIZONTAL));
					}else if(Keyboard.isKeyDown(Keyboard.KEY_V)){
						walls.add(size,new Wall(new Point(ix,iy), new Point(x,y),20,Wall.VERTICAL));
					}else{
					walls.add(size,new Wall(new Point(ix,iy), new Point(x,y),20,Wall.NORMAL));
					}
					setChangedW();
					notifyObserversW(walls);
					}
					glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
					render();
					Display.update();
					Display.sync(120);
				}
				setChangedW();
			}
				
			
			notifyObserversU(units);
			notifyObserversB(bases);
			notifyObserversW(walls);
			leftClicked=false;
		}
		
		
	}

	public void render(){
		map.paint();
		
	}
}
