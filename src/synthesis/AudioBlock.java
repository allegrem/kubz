package synthesis;

import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.exceptions.TooManyInputsException;

/**
 * Cette interface définit toutes les méthodes qui devront être implémentées 
 * par les AudioBlock. La action qu’effectue un AudioBlock est de jouer un son. 
 * @author allegrem
 */
public interface AudioBlock {
	/**
	 * La lecture d’un son demandera d’abord à tous les AudioBlock branchés 
	 * en entrée de calculer le son, puis l’AudioBlock calculera le son à 
	 * partir des entrées ainsi obtenues. Le son est calculé à l’instant t.
	 * <br />
	 * Attention ! Les boucles dans le graphe des AudioBlock ne sont pas 
	 * autorisées (sinon les appels récursifs seront infinis !).
	 * 
	 * @throws RequireAudioBlocksException si l’AudioBlock n’a pas toutes les 
	 * entrées nécessaires pour calculer le son.
	 * @param t l'instant auquel doit être calculé le son
	 * @return le son calculé à l'instant t
	 */
	public Float play(int t) throws RequireAudioBlocksException;
	
}
