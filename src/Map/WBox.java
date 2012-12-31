package Map;

import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glVertex3d;

public class WBox extends MBox {
	private int i;
	private int j;

	public WBox(int line, int column, Map map) {
		super(line, column, map);
		i = column;
		j =line;
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

		glColor3ub((byte) 100, (byte)100, (byte) 100); // face rouge
		glVertex3d(i, j, 0);
		glVertex3d(i + 1, j, 0);
		glVertex3d(i + 1, j + 1, 0);
		glVertex3d(i, j + 1, 0);

		
		glVertex3d(i, j, 0);
		glVertex3d(i, j, 1);
		glVertex3d(i, j + 1, 1);
		glVertex3d(i, j + 1, 0);

		
		glVertex3d(i, j, 0);
		glVertex3d(i, j, 1);
		glVertex3d(i + 1, j, 1);
		glVertex3d(i + 1, j, 0);

	
		glVertex3d(i + 1, j, 0);
		glVertex3d(i + 1, j, 1);
		glVertex3d(i + 1, j + 1, 1);
		glVertex3d(i + 1, j + 1, 0);

		glVertex3d(i + 1, j + 1, 0);
		glVertex3d(i + 1, j + 1, 1);
		glVertex3d(i, j + 1, 1);
		glVertex3d(i, j + 1, 0);


		glVertex3d(i, j, 1);
		glVertex3d(i + 1, j, 1);
		glVertex3d(i + 1, j + 1, 1);
		glVertex3d(i, j + 1, 1);

	}
}
