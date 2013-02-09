package OpenGL;

import gameEngine.GameEngine;

import org.lwjgl.input.Keyboard;

import player.Player;

public class KeyboardManager {

	public static boolean zKey = false;
	public static boolean qKey = false;
	public static boolean sKey = false;
	public static boolean dKey = false;
	public static boolean wKey = false;
	public static boolean xKey = false;
	public static boolean tap = false;
	private static boolean tapTyped=false;
	public static boolean quit=false;
	private static GameEngine gameEngine;

	public static void  setGameEngine(GameEngine gameEngine){
		gameEngine=gameEngine;
	}
	
	
	/**
	 * on controle les deplacements via "ZQSD"
	 * on controle la rotation avec "WX"
	 * on passe a l'atape suivante avec "P"
	 * on change le Parameter sur lequel on agis via "TAB"
	 * 
	 */

public static void checkKeyboard(){
	
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)){
			zKey = true;
		}else{
			zKey = false;
		}
			
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
			qKey = true;
		}else
			qKey = false;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S)){
			sKey = true;
		}else
			sKey = false;
			
		if (Keyboard.isKeyDown(Keyboard.KEY_D)){
			dKey =  true;
		}else
			dKey =  false;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)){
			wKey =  true;
		}else
			wKey =  false;
			
		if (Keyboard.isKeyDown(Keyboard.KEY_X)){
			xKey =  true;
		}else
			xKey =  false;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			quit =  true;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_P)){
			tapTyped = true;	
		}else if(tapTyped){
			tap=true;
			tapTyped=false;
		}
			
				
		
		if (Keyboard.isKeyDown(Keyboard.KEY_TAB)){
			for(Player player:gameEngine.getPlayerList()){
				if (player.isTurn()){
					player.setChoice((player.getChoice() + 1)%player.getnParams());
				}
			}	
	}
		
		
}

}
