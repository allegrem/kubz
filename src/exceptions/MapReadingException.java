package exceptions;

/**
 * Erreur de lecture de la map depuis le fichier
 * 
 * @author paul
 * 
 */
public class MapReadingException extends Exception {

	private static final long serialVersionUID = 1L;
	public MapReadingException(String nameOfFile, int lineNumber,
			String errorMessage) {
		super(errorMessage);

	}

}
