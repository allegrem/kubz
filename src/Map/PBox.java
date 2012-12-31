package Map;

import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

/**
 * Case de type chemin
 * 
 * @author paul
 * 
 */
public class PBox extends MBox {
	private int i;
	private int j;

	public PBox(int line, int column, Map map) {
		super(line, column, map);
		i = line;
		j = column;

	}

	@Override
	public char type() {
		return 'P';
	}

	/**
	 * Peint un carré de la couleur du chemin
	 * 
	 */
	public void paint() {
		glColor3ub((byte) 102, (byte) 51, (byte) 0); // face marron
		glVertex3d(i, j, 0);
		glVertex3d(i + 1, j, 0);
		glVertex3d(i + 1, j + 1, 0);
		glVertex3d(i, j + 1, 0);

	}
}
