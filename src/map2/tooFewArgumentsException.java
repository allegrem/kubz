/**
 * Exception leve�e quand il n'y a pas les arguments n�cessaires pour la cr�ation de Base et Wall lors 
 * la lecture du fichier dans initFromFile.
 * @author valeh 
 */

package map2;

public class tooFewArgumentsException extends Exception{
	
	public tooFewArgumentsException(String message){
		super(message);
	}

}
