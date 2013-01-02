package exceptions;
/**
 * Exception levée quand il n'y a pas les arguments nécessaires pour la création de Base et Wall lors 
 * la lecture du fichier dans initFromFile.
 * @author valeh 
 */
public class tooFewArgumentsException extends Exception{
	
	public tooFewArgumentsException(String message){
		super(message);
	}

}
