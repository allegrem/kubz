package utilities;

import java.util.Random;

/**
 * Classe servant a generer des nombres aleatoires
 * @author paul
 *
 */
public class RandomPerso{

	
	private static Random alea;
	
	/**
	 * Initilalise la graine du generateur
	 */
	public static void initialize(){
		alea=new Random();
	}
	
	
	/**
	   * @return variable aleatoire uniforme sur [0,1]
	   */

	  public static double uniform(){
	    return alea.nextDouble();
	  }

	  /**
	   * @param p parametre de la loi de Bernoulli (0<= p <= 1)
	   * @return variable aleatoire de Bernoulli 
	   */

	  public static boolean bernoulli(double p){
	    
		  return uniform()<=p;
	    
	    
	  }
	  
	  /**
	   * 
	   * @param max
	   * @return Un entier entre 0 et max-1
	   */
	  public static int entier(int max){
		  return (int)(uniform()*max);
	  }
}
