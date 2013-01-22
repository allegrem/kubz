package views;

/**
 * Objet affichable de type "Fils":
 * il est attaché a un pere
 * @author paul
 *
 */
public interface DisplayableChild extends Displayable {
	/**
	 * Definir le pere de l'objet "fils"
	 * @param father
	 */
	public void setFather(DisplayableFather father);
}
