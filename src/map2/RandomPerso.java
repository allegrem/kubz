package map2;

import java.util.Random;

public class RandomPerso{

	
	private static Random alea;
	
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
}
