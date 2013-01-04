package map2;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.util.ArrayList;
import java.util.Random;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.glu.GLU;

import OpenGL.GLBaseModule;
import OpenGL.Textures;


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
	private GLBaseModule affichage;
	private boolean do_run=true;
	public static final boolean MODE3D=true;
	
	
	public MapCreator(int display_width,int display_height){
		this.display_height=display_height;
		this.display_width=display_width;
		affichage=new GLBaseModule(display_width,display_height);
		map=new Map(walls, units, bases, display_width, display_height);
		addObserver(map);
		RandomPerso.initialize();
		
		if (MODE3D){
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		GLU.gluLookAt((float)display_width/2, (float)display_height,(float)500,(float)display_width/2,(float)display_height/2,(float)0, 0, 0, 1); // Positionnement de la caméra
		walls.add(new Wall(new Point(display_width/2-100,display_height/2),new Point(display_width/2+100,display_height/2), 15, 1));
		setChangedW();
		notifyObserversW(walls);
		}
		
		while(do_run){
			if (Display.isCloseRequested())
				do_run = false;
			//checkInput();
			
			for (Wall wall:walls){
				wall.aleaMove();
				GL11.glTranslatef((float)display_width/2,(float) display_height/2, 0);
				GL11.glRotated(-0.1, 0, 0, 1);
				GL11.glTranslatef(-(float)display_width/2, (float)-display_height/2, 0);
				setChangedW();
				notifyObserversW(walls);
			}
			
			affichage.clear();
			render();
			affichage.update();			
			Display.sync(120);
		}
		affichage.close();
		
		
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
			if (Keyboard.isKeyDown(Keyboard.KEY_S)){
				units.add(new SquareUnit(new Point(x,y),Color.BLUE,map));
				setChangedU();
			}
				
			else if (Keyboard.isKeyDown(Keyboard.KEY_T)){
				units.add(new ShapeUnit(new Point(x,y),Color.GREEN,map));
				setChangedU();
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_C)){
				units.add(new CircleUnit(new Point(x,y),Color.YELLOW,map));
				setChangedU();
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_B)){
				int size=bases.size();
				bases.add(new Base(new Point(x,y),Color.PURPLE));
				while(Mouse.isButtonDown(0)){
					x=Mouse.getX();
					y=display_height-Mouse.getY();	
					bases.remove(size);
					if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
						bases.add(new Base(new Point(x,0),Color.PURPLE));
					}else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
						bases.add(new Base(new Point(x,display_height),Color.PURPLE));
					}
					else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
						bases.add(new Base(new Point(display_width,y),Color.PURPLE));
					}
					else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
						bases.add(new Base(new Point(0,y),Color.PURPLE));
					}else{
						bases.add(new Base(new Point(x,y),Color.PURPLE));
					}
					setChangedB();
					notifyObserversB(bases);
					affichage.clear();
					render();
					affichage.update();
					Display.sync(120);
					Mouse.poll();
				}
				setChangedB();
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_W)){
				int size=walls.size();
				int ix=x;
				int iy=y;
				walls.add(new Wall(new Point(ix,iy), new Point(x+20,y+30),15,Wall.NORMAL));
				while(Mouse.isButtonDown(0)){
					x=Mouse.getX();
					y=display_height-Mouse.getY();	
					walls.remove(size);
					if(Keyboard.isKeyDown(Keyboard.KEY_H)){
						walls.add(size,new Wall(new Point(ix,iy), new Point(x,y),15,Wall.HORIZONTAL));
					}else if(Keyboard.isKeyDown(Keyboard.KEY_V)){
						walls.add(size,new Wall(new Point(ix,iy), new Point(x,y),15,Wall.VERTICAL));
					}else{
					walls.add(size,new Wall(new Point(ix,iy), new Point(x,y),15,Wall.NORMAL));
					}
					setChangedW();
					notifyObserversW(walls);
					affichage.clear();
					render();
					affichage.update();
					Display.sync(120);
					Mouse.poll();
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
