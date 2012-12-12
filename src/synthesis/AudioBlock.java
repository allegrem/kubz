package synthesis;

/**
 * Cette interface définit toutes les méthodes qui devront être implémentées 
 * par les AudioBlock. Les trois principales actions qu’effectue un AudioBlock 
 * sont : jouer un son, brancher un AudioBlock, débrancher un AudioBlock. 
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
	public Float play(int t);

	
	/**
	 * Cette méthode permet de brancher l’AudioBlock a sur l’entrée i de 
	 * l’AudioBlock considéré. Certains blocs n’autorisent qu’un nombre fixé 
	 * d’entrées, d’autres n’autorisent aucune entrée, etc... Pour être certain 
	 * de l’utilisation de la méthode plugin, il convient de se référer à la 
	 * documentation de l’AudioBlock considéré.
	 * <br />
	 * Le numéro de l’entrée est parfois essentiel (pour certains AudioBlock, 
	 * toutes les entrées ne jouent pas le même rôle). Là encore on se 
	 * référera à la documentation de l’AudioBlock considéré. 
	 * Généralement, des constantes représentant les différentes entrées sont 
	 * définies dans la classe implémentant l’AudioBlock. Si l’ordre des 
	 * entrées ne compte pas, n’importe quelle entrée pourra être passée en 
	 * argument.
	 * 
	 * @throws TooManyInputsException si l’AudioBlock ne peut plus accepter 
	 * d’entrées
	 * @throws InvalidInputNumberException si l’argument i 
	 * n’est pas une entrée valide.
	 * @param a AudioBlock à brancher
	 * @param i numéro de l'entrée sur laquelle l'AudioBlock doit être branché
	 */
	public void plugin(AudioBlock a, int i);

	
	/**
	 * Cette méthode débranche l’AudioBlock a.
	 * @param a AudioBlock à débrancher
	 */
	void plugout(AudioBlock a);

	
	/**
	 * Cette méthode débranche l’AudioBlock branché sur l’entrée i. Cette 
	 * méthode ne devrait être utilisée que sur les AudioBlock pour lesquels 
	 * l’ordre des entrées compte.
	 * <br />
	 * Attention ! A priori, rien ne garantit que les numéros d’entrée 
	 * correspondent à l’ordre dans lequel la méthode plugin a été appelée. Il 
	 * est toujours préférable de garder une référence sur les AudioBlock 
	 * branchés et d’utiliser la méthode plugout(AudioBlock a).
	 * 
	 * @throws InvalidInputNumberException si l’argument i n’est pas une 
	 * entrée valide.
	 * @param i numéro de l'entrée à débrancher
	 */
	void plugout(int i);

	
	/**
	 * Cette méthode débranche tous les AudioBlock actuellement branchés.
	 */
	void plugoutAll();
}
