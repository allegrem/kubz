package utilities;

import java.util.Random;

public class RandomPerso{

	
	private static Random alea;
	
	public static void initialize(){
		alea=new Random();
	}
	
	
	/**
	   * @return variable aleatoire uniforme sur [0,1]
	   */

	  static double uniform(){
	    return alea.nextDouble();
	  }

	  /**
	   * @param p parametre de la loi de Bernoulli (0<= p <= 1)
	   * @return variable aleatoire de Bernoulli 
	   */

	  static boolean bernoulli(double p){
	    
		  return uniform()<=p;
	    
	    
	  }
}
