package map2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import exceptions.MapReadingException;

import Map.MBox;
import Map.PBox;
import Map.WBox;

/**
 * Chargement de la carte du jeu,avec les murs et les bases.
 * 
 * @author valeh
 * 
 */
public class Map2 {

	private int height;
	private int width;
	private MBox[][] tab;
	private ArrayList<Wall> wallList = new ArrayList<Wall>();
	private Base[] baseList = new Base[4];  //On code le nombre de joueurs "en dur".
 
	/**
	 * Chargement de la carte de jeu
	 * 
	 * @param file
	 *            fichier de jeu a charger
	 * @throws Exception
	 *             Erreur lors de la lecture de la carte
	 * @author paul
	 */
	public Map2(String file) throws Exception {
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
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fileName1);
			br = new BufferedReader(fr);

			String line = new String();
			br.readLine();
			br.readLine();  
			int sWall = Integer.parseInt( br.readLine() );
			wallList = new ArrayList<Wall>(sWall);
			String lineRead = new String();
			for (int lineL=0 ; lineL<sWall ; lineL ++){
				if (br.readLine().length()<5) throw new tooFewArgumentsException("Il" +
						"faut 3 arguments déparés par des espaces soit 5 carctères minimum!");
				int xWall = br.read();
				br.skip(1);   //espace
				int yWall = br.read();
				br.skip(1);   //espace
				int tWall = br.read();
				wallList.add( new Wall(xWall,yWall,tWall) );
			}		
	} catch (tooFewArgumentsException tfe){
		System.out.println(tfe.getMessage());
		tfe.printStackTrace();
	}
	
	}

	/**
	 * 
	 * 
	 * @param newBox
	 *            Nouvelle case
	 * @param i
	 *            position en hauteur de la case Ã  changer
	 * @param j
	 *            position en largeur de la case a changer
	 */
	public void changeCase(MBox newBox, int i, int j) {
		tab[i][j] = newBox;
	}

}