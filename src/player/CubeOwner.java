package player;

/**
 * @author Felix
 */

import cube.Cube;
import traitementVideo.VirtualCube;

public class CubeOwner {
	protected Cube cube;
	protected VirtualCube vCube;

	public CubeOwner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retourne le cube physique auquel est associe le CubeOwner
	 * @return
	 */
	public Cube getCube() {
		return cube;
	}
	
	/**
	 * Retourne le cube du traitement de l'image auquel est associe le CubeOwner
	 * @return
	 */
	public VirtualCube getVCube(){
		return vCube;
	}

}
