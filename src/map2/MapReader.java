package map2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.lwjgl.util.Color;

import utilities.Point;
import views.monsters.CircleMonsterView;
import views.monsters.MonsterView;
import views.monsters.ShapeMonsterView;
import views.monsters.SquareMonsterView;
import views.staticViews.BaseView;
import views.staticViews.WallView;



/**
 * Chargement de la carte du jeu a partir d'une lecture de fichier contenant
 * les infos sur les murs et les bases et les monstres. A partir de la,la MapCreator 
 * charge son Map avec ces elements et affiche la carte.
 * 
 * @author valeh
 * 
 */
public class MapReader {

	private Map map;
	private String bFileName,mFileName,wFileName;
	
	public MapReader(String bFileName, String mFileName, String wFileName){
		this.bFileName = bFileName;
		this.mFileName = mFileName;
		this.wFileName = wFileName;
	}
	
	/**
	 * Lecture du fichier "Bases"
	 * @throws Exception
	 */
	private void readBases() throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(bFileName);
			br = new BufferedReader(fr);
			int sBase = Integer.parseInt( br.readLine() );
			for (int line=0;line<sBase;line++){
				Scanner sc = new Scanner(br.readLine());
				sc.useLocale(Locale.US);   //sinon il reconnait pas 12.3 mais 12,3!!!
				float xCenter = sc.nextFloat();
				float yCenter = sc.nextFloat();
				int baseRed = sc.nextInt(), baseGreen = sc.nextInt(), baseBlue = sc.nextInt();
				int sens = sc.nextInt();			
				map.add(new BaseView( new Point(xCenter,yCenter) , new Color(baseRed,baseGreen,baseBlue), sens));
				sc.close();
			}
		
		}catch(Exception e){e.printStackTrace();}
		finally {
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
	
	/**
	 * Lecture du fichier "Monsters"
	 * @throws Exception
	 */
	private void readMonsters() throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(mFileName);
			br = new BufferedReader(fr);
			int sMonster = Integer.parseInt( br.readLine() );
			for (int line=0;line<sMonster;line++){
				Scanner sc = new Scanner(br.readLine());
				sc.useLocale(Locale.US);
				char type = sc.next().charAt(0); //"convert" to char 
				float xCenter = sc.nextFloat(), yCenter = sc.nextFloat();
				System.out.println(xCenter);
				int red = sc.nextInt(), green = sc.nextInt(), blue = sc.nextInt();
				switch(type){
				case 'C':
					map.add(new CircleMonsterView( new Point(xCenter,yCenter) , new Color(red,green,blue) ) );
				break;
				case 'T':
					map.add(new ShapeMonsterView( new Point(xCenter,yCenter) , new Color(red,green,blue)) );
				break;
				case 'S':
					map.add(new SquareMonsterView( new Point(xCenter,yCenter) , new Color(red,green,blue) ) );
				break;
				default:
				break;
				}
				sc.close();
			}
		
		}catch(Exception e){e.printStackTrace();}
		finally {
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
	
	/**
	 * Lecture du fichier "Walls"
	 * @throws Exception
	 */
	private void readWalls() throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(wFileName);
			br = new BufferedReader(fr);
			int sWall = Integer.parseInt( br.readLine() );
			for (int line=0;line<sWall;line++){
				Scanner sc = new Scanner(br.readLine());
				sc.useLocale(Locale.US);				
				float xExtr1 = sc.nextFloat(), yExtr1 = sc.nextFloat();
				float xExtr2 = sc.nextFloat(), yExtr2 = sc.nextFloat();				
				int thickness = sc.nextInt();
				map.add(new WallView( new Point(xExtr1,yExtr1) , new Point(xExtr2,yExtr2), thickness,0));
				sc.close();
			}
		}catch(Exception e){e.printStackTrace();}
		finally {
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
	
	/**
	 * Lecture de tous les fichiers
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map read(Map map) throws Exception{
		this.map = map;
		readBases();
		readMonsters();
		readWalls();
		return map;
		
	}
	

}
