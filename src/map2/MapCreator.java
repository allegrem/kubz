package map2;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;
import org.lwjgl.util.Point;

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
		
		x=Mouse.getX()*100/display_width;
		y=100-(Mouse.getY()*100/display_height);
		
		if(!Mouse.isButtonDown(0))
			leftClicked=true;
		
		if(!Mouse.isButtonDown(1))
			rightClicked=true;
		
		if(Mouse.isButtonDown(1) && rightClicked && units.size()>0){
			
			if (Keyboard.isKeyDown(Keyboard.KEY_U)){
				units.remove(units.size()-1);
				setChangedU();
			}
				
			
			rightClicked=false;
			notifyObserversU(units);
			notifyObserversB(bases);
			notifyObserversW(walls);
			
			
		}
		
		if(Mouse.isButtonDown(0) && leftClicked){
			if (Keyboard.isKeyDown(Keyboard.KEY_C)){
				units.add(new SquareUnit(new Point(x,y),Color.RED));
				setChangedU();
			}
				
			else if (Keyboard.isKeyDown(Keyboard.KEY_T)){
				units.add(new ShapeUnit(new Point(x,y),Color.GREEN));
				setChangedU();
			}
				
			
			leftClicked=false;
			notifyObserversU(units);
			notifyObserversB(bases);
			notifyObserversW(walls);
			
		}
		
		
	}

	public void render(){
		map.paint();
		
	}
}
