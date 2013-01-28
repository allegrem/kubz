package gameEngine;

import monster.Monster;
import monster.MonsterManager;
import player.*;

/**
 * Pour l'instant c'est un test du moteur de jeu pour un seul joueur
 * A prendre en compte que pour le reste du code la méthode act() signifie "fais ce que tu as à faire..."
 * @author Felix
 */

public class GameEngine{
	/**
	 * pas de liste de joueurs car un seul joueur pour le moment,
	 * de même une seule unité (Que 3 cubes pour le moment
	 */
	private MonsterManager monsterManager;
	private Player player;
	private boolean quit;
	
	/**
	 * Méthode qui lance les actions des joueurs 
	 */
	public void monsterTurn(){
		for(Monster m: monsterManager.getMonsterList()){
			m.act();
		}
	}
	
	/**
	 * Méthode qui lance les actions du(des) joueur(s)
	 */
	public void playerTurn(){
		player.act();
	}
	
	/**
	 * Méthode qui freeze le jeu, condition de relance sur le mode normal à revoir
	 */
	public void frozen(){
	}
	
	/**
	 * Méthode principale du gameEngine
	 */
	public void act(){
		while(!quit){
			playerTurn();
			monsterTurn();
		}
	}
	
	
}
