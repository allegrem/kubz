package map2;


import gameEngine.GameEngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import monster.CircleMonster;
import monster.Monster;
import monster.ShapeMonster;
import monster.SquareMonster;

import org.lwjgl.util.Color;

import base.Base;

import utilities.Point;
import views.monsters.CircleMonsterView;
import views.monsters.MonsterView;
import views.monsters.ShapeMonsterView;
import views.monsters.SquareMonsterView;
import views.staticViews.BaseView;
import views.staticViews.WallView;
import wall.Wall;



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
	private GameEngine gameEngine;
	
	public MapReader(String bFileName, String mFileName, String wFileName, GameEngine gameEngine){
		this.bFileName = bFileName;
		this.mFileName = mFileName;
		this.wFileName = wFileName;
		this.gameEngine=gameEngine;
	}
	
	/**
	 * Lecture du fichier "Bases"
	 * @throws Exception
	 */
	public ArrayList<Base> readBases() throws Exception {
		ArrayList<Base> bases= new ArrayList<Base>();
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
				bases.add(new Base( new Point(xCenter,yCenter) , new Color(baseRed,baseGreen,baseBlue), sens,gameEngine));
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
		return bases;
	}
	
	/**
	 * Lecture du fichier "Monsters"
	 * @throws Exception
	 */
	public ArrayList<Monster> readMonsters() throws Exception {
		ArrayList<Monster> monsters= new ArrayList<Monster>();
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
					monsters.add(new CircleMonster( xCenter,yCenter , new Color(red,green,blue) ,gameEngine) );
				break;
				case 'T':
					monsters.add(new ShapeMonster( xCenter,yCenter, new Color(red,green,blue),gameEngine) );
				break;
				case 'S':
					monsters.add(new SquareMonster(xCenter,yCenter , new Color(red,green,blue),gameEngine ) );
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
		return monsters;
	}
	
	/**
	 * Lecture du fichier "Walls"
	 * @throws Exception
	 */
	public ArrayList<Wall> readWalls() throws Exception {
		ArrayList<Wall> walls=new ArrayList<Wall>();
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
				walls.add(new Wall( new Point(xExtr1,yExtr1) , new Point(xExtr2,yExtr2), thickness,0,gameEngine));
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
		return walls;
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
