package map2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.util.Color;

import utilities.Point;
import views.BaseView;
import views.CircleMonsterView;
import views.MonsterView;
import views.ShapeMonsterView;
import views.SquareMonsterView;
import views.WallView;



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
	
	private void readBases() throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(bFileName);
			br = new BufferedReader(fr);
			int sBase = Integer.parseInt( br.readLine() );
			for (int line=0;line<sBase;line++){
				Scanner sc = new Scanner(br.readLine());
				float xCenter = sc.nextFloat();
				System.out.println(xCenter);
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
	
	private void readMonsters() throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(mFileName);
			br = new BufferedReader(fr);
			int sMonster = Integer.parseInt( br.readLine() );
			for (int line=0;line<sMonster;line++){
				Scanner sc = new Scanner(br.readLine());
				char type = sc.next().charAt(0); //"convert" to char 
				float xCenter = sc.nextFloat(), yCenter = sc.nextFloat();
				System.out.println(xCenter);
				int red = sc.nextInt(), green = sc.nextInt(), blue = sc.nextInt();
				switch(type){
				case 'C':
					map.add(new CircleMonsterView( new Point(xCenter,yCenter) , new Color(red,green,blue) , map ) );
				break;
				case 'T':
					map.add(new ShapeMonsterView( new Point(xCenter,yCenter) , new Color(red,green,blue) , map ) );
				break;
				case 'S':
					map.add(new SquareMonsterView( new Point(xCenter,yCenter) , new Color(red,green,blue) , map ) );
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
	
	
	public Map read(Map map) throws Exception{
		this.map = map;
		readBases();
		readMonsters();
		return map;
		
	}
	

}
