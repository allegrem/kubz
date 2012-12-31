package Map;

import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

public class WBox extends MBox {
	private int i;
	private int j;

	public WBox(int line, int column, Map map) {
		super(line, column, map);
		i = line;
		j = column;
	}

	@Override
	public char type() {
		return 'W';
	}

	/**
	 * Peint un cube de la couleur du mur
	 * 
	 */
	public void paint() {

		glColor3ub((byte) 255, (byte) 0, (byte) 0); // face rouge
		glVertex3d(i, j, 0);
		glVertex3d(i + 1, j, 0);
		glVertex3d(i + 1, j + 1, 0);
		glVertex3d(i, j + 1, 0);

		glColor3ub((byte) 0, (byte) 255, (byte) 0); // face verte
		glVertex3d(i, j, 0);
		glVertex3d(i, j, 1);
		glVertex3d(i, j + 1, 1);
		glVertex3d(i, j + 1, 0);

		glColor3ub((byte) 0, (byte) 0, (byte) 255); // face bleue
		glVertex3d(i, j, 0);
		glVertex3d(i, j, 1);
		glVertex3d(i + 1, j, 1);
		glVertex3d(i + 1, j, 0);

		glColor3ub((byte) 255, (byte) 255, (byte) 0); // face jaune
		glVertex3d(i + 1, j, 0);
		glVertex3d(i + 1, j, 1);
		glVertex3d(i + 1, j + 1, 1);
		glVertex3d(i + 1, j + 1, 0);

		glColor3ub((byte) 0, (byte) 255, (byte) 255); // face cyan
		glVertex3d(i + 1, j + 1, 0);
		glVertex3d(i + 1, j + 1, 1);
		glVertex3d(i, j + 1, 1);
		glVertex3d(i, j + 1, 0);

		glColor3ub((byte) 255, (byte) 0, (byte) 255); // face magenta
		glVertex3d(i, j, 1);
		glVertex3d(i + 1, j, 1);
		glVertex3d(i + 1, j + 1, 1);
		glVertex3d(i, j + 1, 1);

	}
}
