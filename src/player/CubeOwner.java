package player;

/**
 * @author Felix
 */

import cube.Cube;
import traitementVideo.VideoCube;

public class CubeOwner {
	protected Cube cube;
	protected VideoCube vCube;
	protected final int id;

	public CubeOwner(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
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
	public VideoCube getVCube(){
		return vCube;
	}

	/**
	 * Permet de récupérer l'id du cube (c'est clairement pas optimisé, mais c'est un rush)
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	
	
	

}
