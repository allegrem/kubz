package synthesis;

import synthesis.exceptions.RequireAudioBlocksException;

/**
 * Cette interface définit toutes les méthodes qui devront être implémentées 
 * par les AudioBlock. La action qu’effectue un AudioBlock est de jouer un son. 
 * @author allegrem
 */
public interface AudioBlock {
	
	/**
	 * Defines the sample rate of the synthesis engine.
	 */
	public static final Float SAMPLE_RATE = 44100f;
	
	
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
	public Float play(Float t) throws RequireAudioBlocksException;
	
	
	/**
	 * Compute the phi function which returns the integral of the signal.
	 * @param t The time where to compute the phi function.
	 * @return phi(t) = 2 * Pi * int(f(u), u=0..t) where f is the 
	 * signal produced.
	 * @throws RequireAudioBlsocksException  si l’AudioBlock n’a pas toutes les 
	 * entrées nécessaires pour calculer le son.
	 */
	public Float phi(Float t) throws RequireAudioBlocksException; //Bla
	
}
