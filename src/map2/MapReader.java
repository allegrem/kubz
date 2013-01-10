package map2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import utilities.Point;
import views.BaseView;
import views.MonsterView;
import views.WallView;

import Map.MBox;

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
	private String bFileName = MapCreator.bFileName;
	private String mFileName = MapCreator.mFileName;
	private String wFileName = MapCreator.wFileName;
	
	public void readBases(){
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(bFileName);
			br = new BufferedReader(fr);
			//A faire (pas le temps la!!)
		}
	}
	

}
