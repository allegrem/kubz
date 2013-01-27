package gameEngine;

import monster.Monster;
import monster.MonsterManager;
import player.*;

/**
 * Pour l'instant c'est un test du moteur de jeu pour un seul joueur
 * @author Felix
 */

public class GameEngine{
	/**
	 * pas de liste de joueurs car un seul joueur pour le moment,
	 * de m�me une seule unit� (Que 3 cubes pour le moment
	 */
	private MonsterManager monsterManager;
	private Player player;
	
	/**
	 * nom � revoir
	 * M�thode qui met 
	 */
	public void monsterTurn(){
		for(Monster m: monsterManager.getMonsterList()){
			m.act();
		}
	}
	
	
}
