package exceptions;
/**
 * Exception levee quand il n'y a pas les arguments necessaires pour la creation de Base et Wall lors 
 * la lecture du fichier dans initFromFile.
 * @author valeh 
 */
@SuppressWarnings("serial")
public class tooFewArgumentsException extends Exception{
	
	public tooFewArgumentsException(String message){
		super(message);
	}

}
