package Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Chargement de la map de jeu
 * 
 * @author paul
 * 
 */
public class Map {

	private int height;
	private int width;
	private MBox[][] tab;

	/**
	 * Chargement de la carte de jeu
	 * 
	 * @param file
	 *            fichier de jeu a charger
	 * @throws Exception
	 *             Erreur lors de la lecture de la carte
	 */
	public Map(String file) throws Exception {
		try {
			findDimensions(file);
			tab = new MBox[width][height];
			initFromFile(file);
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Chargement des dimensions de la map
	 * 
	 * @param file
	 *            fichier de jeu
	 * @throws Exception
	 *             Erreur de lecture du fichier
	 */
	private void findDimensions(String file) throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		String sHeight;
		String sWidth;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			sWidth = br.readLine();
			sHeight = br.readLine();
			width = Integer.valueOf(sWidth);
			height = Integer.valueOf(sHeight);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Fichier introuvable...");
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Format du labyrinthe invalide...");
		} catch (IOException e) {
			throw new IOException(
					"Erreur inconnue lors de la lecture du fichier...");
		} catch (Exception e) {
			throw new Exception(
					"Une erreur inconnue est survenue lors de l'ouverture du fichier...");
		} finally {
			if (br != null)
				try {
					fr.close();
				} catch (Exception e) {
				}
			;
			if (fr != null)
				try {
					br.close();
				} catch (Exception e) {
				}
			;
		}

	}

	/**
	 * 
	 * 
	 * @return hauteur de la map
	 */
	public int getHeight() {

		return height;
	}

	/**
	 * 
	 * 
	 * @return largeur de la map
	 */
	public int getWidth() {

		return width;
	}

	/**
	 * 
	 * 
	 * @param i
	 *            position en hauteur
	 * @param j
	 *            position en largeur
	 * @return La case a la position donnee
	 */
	public final MBox returnCase(int i, int j) {
		return tab[i][j];

	}

	/**
	 * 
	 * 
	 * @param fileName1
	 *            fichier a charger
	 * @throws Exception
	 *             Erreur de lecture
	 */
	public final void initFromFile(String fileName1) throws Exception {
		boolean entryExist = false;
		boolean exitExist = false;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fileName1);
			br = new BufferedReader(fr);

			String line = new String();
			br.readLine();
			br.readLine();
			for (int lineL = 0; lineL < height; lineL++) {
				line = br.readLine();
				if (line.length() < width)
					throw new MapReadingException(fileName1, lineL, "La ligne "
							+ (lineL + 1)
							+ " est trop courte ou n'existe pas...");
				if (line.length() > width)
					throw new MapReadingException(fileName1, lineL, "La ligne "
							+ (lineL + 1) + " est trop longue...");

				for (int columnC = 0; columnC < width; columnC++) {
					char c = line.charAt(columnC);

					switch (c) {
					case 'E':
						tab[columnC][lineL] = new PBox(lineL, columnC, this);
						break;
					case 'W':
						tab[columnC][lineL] = new WBox(lineL, columnC, this);
						break;
					default:
						throw new MapReadingException(fileName1, lineL,
								"Case inconnue ligne " + (lineL + 1) + "...");
					}
				}
			}

		} catch (MapReadingException m) {
			throw m;
		}

		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Fichier introuvable...");
		}

		catch (IOException e) {
			throw new IOException(
					"Erreur inconnue lors de la lecture du fichier...");
		} catch (Exception e) {
			throw new Exception(
					"Une erreur inconnue est survenue lors de l'ouverture du fichier...");
		} finally {
			if (br != null)
				try {
					fr.close();
				} catch (Exception e) {
					throw new Exception(
							"Erreur lors de la fermeture du fichier...");
				}
			;
			if (fr != null)
				try {
					br.close();
				} catch (Exception e) {
					throw new Exception(
							"Erreur lors de la fermeture du buffer...");
				}
			;
		}

	}

	/**
	 * 
	 * 
	 * @param newBox
	 *            Nouvelle case
	 * @param i
	 *            position en hauteur de la case à changer
	 * @param j
	 *            position en largeur de la case a changer
	 */
	public void changeCase(MBox newBox, int i, int j) {
		tab[i][j] = newBox;
	}

}