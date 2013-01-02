package map2;

/**
 * Chargement de la carte du jeu,avec les murs et les bases.
 * 
 * @author valeh
 * 
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import map2.Base;
import map2.Wall;
import Map.MBox;

public class MapReader {

	private int height;
	private int width;
	private MBox[][] tab;
	private ArrayList<Wall> wallList = new ArrayList<Wall>();
	private ArrayList<Base> baseList = new ArrayList<Base>();
	private Map mapRead;
	
	
	/**
	 * Chargement de la carte de jeu
	 * 
	 * @param file
	 *            fichier de jeu a charger
	 * @throws Exception
	 *             Erreur lors de la lecture de la carte
	 */
	public MapReader(String file) throws Exception {
		try {
			
			findDimensions(file);
			tab = new MBox[width][height];
			initFromFile(file);
			mapRead = new Map( wallList,new ArrayList<Unit>(), baseList,width,height);
		} catch (Exception e) {
			e.printStackTrace();
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
			width = Integer.parseInt(sWidth);
			height = Integer.parseInt(sHeight);
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
			
			if (fr != null)
				try {
					br.close();
				} catch (Exception e) {
				}
			
		}

	}

	/**
	 * @return hauteur de la map
	 */
	public int getHeight() {

		return height;
	}

	/** 
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
	 */
	public final void initFromFile(String fileName1) throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fileName1);
			br = new BufferedReader(fr);
			br.readLine();
			br.readLine();  
		
			int sBase = Integer.parseInt( br.readLine() );	
			//System.out.println(sBase);
			for (int i=0 ; i<sBase ; i++){
				Scanner sc = new Scanner(br.readLine());  
				int xCenter = sc.nextInt();   //On mettra probabalement des floats pour les coords,pour les tests on essaie avec 
											  //des int pour l'instant
				//System.out.println(xCenter);
				int yCenter = sc.nextInt();
				baseList.add( new Base( new Point(xCenter,yCenter) ) );
				sc.close();
			}
			
			int sWall = Integer.parseInt( br.readLine() );			
			wallList = new ArrayList<Wall> (sWall);
			for (int i=0 ; i<sWall ; i++){
				Scanner sc = new Scanner(br.readLine());  
				float xWall = sc.nextFloat();   
				float yWall = sc.nextFloat();
				Point departure = new Point(xWall,yWall);
				float xWallEnd = sc.nextFloat();
				float yWallEnd = sc.nextFloat();
				Point arrival = new Point(xWallEnd,yWallEnd);
				
				int tWall = sc.nextInt();
				wallList.add( new Wall(departure,arrival,tWall,Wall.NORMAL) );
				sc.close();
			}
					
	} catch (Exception e) { 
		e.printStackTrace(); 
	} finally {
			if (br != null)
				try {
					fr.close();
				} catch (Exception e) {
					throw new Exception("Erreur lors de la fermeture du fichier...");}
			if (fr != null)
				try {
					br.close();
				} catch (Exception e) {
					throw new Exception("Erreur lors de la fermeture du buffer...");}
			
		}
	}
	public ArrayList<Base> getBaseList(){
		return this.baseList;
	}
	public ArrayList<Wall> getWallList(){
		return this.wallList;
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
	public void render(){
		mapRead.paint();
	}

}
