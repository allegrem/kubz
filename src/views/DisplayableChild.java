package views;

public interface DisplayableChild extends Displayable {
	/**
	 * Definir le pere de l'objet "fils"
	 * @param father
	 */
	public void setFather(DisplayableFather father);
}
