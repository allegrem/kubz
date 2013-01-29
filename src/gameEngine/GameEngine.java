package gameEngine;

import java.util.ArrayList;

import monster.Monster;
import player.*;
import unit.Unit;

/**
 * Pour l'instant c'est un test du moteur de jeu pour un seul joueur
 * A prendre en compte que pour le reste du code la m�thode act() signifie "fais ce que tu as � faire..."
 * @author Felix
 */

public class GameEngine{
	/**
	 * pas de liste de joueurs car un seul joueur pour le moment,
	 * de m�me une seule unit� (Que 3 cubes pour le moment
	 */
	private ArrayList<Monster> monsterList;
	private ArrayList<Player> playerList;
	private boolean quit;
	
	public ArrayList<Unit> getUnitList(){
		ArrayList<Unit> unitList= new ArrayList<Unit>();
		for (Player player : playerList)
			unitList.add(player.getUnit());
		return unitList;
	}
	
	/**
	 * M�thode qui lance les actions des joueurs 
	 */
	public void monsterTurn(){
		for(Monster m: monsterList){
			m.act();
		}
	}
	
	/**
	 * M�thode qui lance les actions du(des) joueur(s)
	 */
	public void playerTurn(){
		for(Player player: playerList){
			player.act();
		}	
	}
	
	/**
	 * M�thode qui freeze le jeu, condition de relance sur le mode normal � revoir
	 */
	public void frozen(){
	}
	
	/**
	 * M�thode principale du gameEngine
	 */
	public void act(){
		while(!quit){
			playerTurn();
			monsterTurn();
		}
	}
	
	
}
