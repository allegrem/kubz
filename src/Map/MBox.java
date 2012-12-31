package Map;

import java.util.ArrayList;

import javax.swing.JButton;

/**
 * Classe abstraite representant une casse de la map
 * 
 * @author paul
 * 
 */
public abstract class MBox {
	private final int column;
	private final int line;
	private final Map map;

	/**
	 * 
	 * 
	 * @param line
	 *            ligne de la case
	 * @param column
	 *            colonne de la case
	 * @param map
	 *            map a laquelle appartient la case
	 */
	public MBox(int line, int column, Map map) {
		this.line = line;
		this.column = column;
		this.map = map;
	}

	/**
	 * 
	 * 
	 * @return ligne de la case
	 */
	public int getLine() {
		return line;
	}

	/**
	 * 
	 * 
	 * @return colonne de la case
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * 
	 * 
	 * @return type de la case
	 */
	public abstract char type();

	/**
	 * Peidre ce que représente la case à sa positon
	 * 
	 */
	public abstract void paint();

}