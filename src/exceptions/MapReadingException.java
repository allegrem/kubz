package exceptions;

/**
 * Erreur de lecture du labyrinthe depuis le fichier
 * 
 * @author paul
 * 
 */
public class MapReadingException extends Exception {

	private static final long serialVersionUID = 1L;
	private int line;

	public MapReadingException(String nameOfFile, int lineNumber,
			String errorMessage) {
		super(errorMessage);

	}

}
