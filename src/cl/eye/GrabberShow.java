package cl.eye;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

import com.googlecode.javacv.CanvasFrame;

import processing.core.PImage;
import traitementVideo.Traitement;
import traitementVideo.VirtualPixel;
import utilities.Point;
import videoprocessing.ImagePanel;

/**
 * 
 * @author Charly Cette classe permet de gérer le tracking d' objet. les
 *         methodes qu'elle implement sont run, paint et grabbershow
 */
public class GrabberShow implements Runnable {
	
	private CanvasFrame canvas = new CanvasFrame("Web Cam"); // fenetre contenant la vue
														// de la cam
	private CanvasFrame path = new CanvasFrame("Detection"); // fenetre contenant la
														// position de l'objet
														// tracké
	private JPanel jp = new JPanel();
	private int compconn;
	private int posXold; 
	private int posYold;
	private boolean white;
	private CLCamera myCamera = null;
	private PImage myImage = null;
	public static final int CAMERA_WIDTH = 640;
	public static final int CAMERA_HEIGHT = 480;
	private int cameraRate = 75; // fps maximum pour cette resolution
	private ImagePanel imagePanel;
	private VirtualPixel[][] virtualTab = new VirtualPixel[CAMERA_HEIGHT][CAMERA_WIDTH];
	
	/**
	 * permet d'avoir un interface controlé
	 */
	public GrabberShow() {
		// Verifies the native library loaded
		if (!setupCameras())
			System.exit(0);

		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		imagePanel = new ImagePanel(CAMERA_WIDTH, CAMERA_HEIGHT);
		canvas.setContentPane(imagePanel);

		path.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		path.setContentPane(jp);
	}

	
	/**
	 * methode principale elle va rechercher les maxima de couleurs puis va tracker les maxima rouge 
	 * on track d'abord le premier maxima et on va considerer  tous ceux qui ont un rouge proche de celui du maxima
	 * ensuite on donne leurs positions 
	 */
	public void run() {
		int posnY = 0, posnX = 0;
		while (true) {

			myCamera.getCameraFrame(myImage.pixels, 1000);
			myImage.filter(PImage.THRESHOLD, 0.25f); //passage en mode threshold
			compconn = 0;
			int posX = 0, posY = 0;
			int comptX = 0, comptY = 0;
			double cupos = 0;
			for (int i = 0; i < myImage.pixels.length; i++) {
				if ((myImage.pixels[i] & 0xFF) > 10) {
					white = true;
					posX = comptX;
					posY = comptY;
				}
				else white = false;
				//virtualTab[comptX][comptY] = new VirtualPixel(white, 0, new Point(comptX,comptY));
				comptX++; // on parcours l'image en largeur
				if (comptX == CAMERA_WIDTH) // on descend d'une ligne
				{
					comptX = 0;
					comptY++;
				}
				//virtualTab[comptX][comptY] = new VirtualPixel(comptX,comptY,white,compconn);
			}
			cupos = Math.sqrt((posnX - posX) * (posnX - posX) + (posnY - posY)
					* (posnY - posY));
			//System.out.println("posnY: " + posnY + ";posnX: " + posnX
					//+ ";posX " + posX + ";posY: " + posY + "cupos: " + cupos);
			if (cupos < 100) {
				paint(posX, posY, cupos);
				posnX = posX;
				posnY = posY;
			}
			if (myImage != null) // si l'on ne get pas la cam
			{
				//mise a jour de la fenetre affichant l'image de la camera
				byte[] byteArray = new byte[CAMERA_HEIGHT * CAMERA_WIDTH];
				for (int j = 0; j < CAMERA_HEIGHT * CAMERA_WIDTH; j++)
					byteArray[j] = (byte) myImage.pixels[j];
				Raster raster = Raster.createPackedRaster(DataBuffer.TYPE_INT,
						CAMERA_WIDTH, CAMERA_HEIGHT, 3, 8, null);
				((WritableRaster) raster).setDataElements(0, 0, CAMERA_WIDTH,
						CAMERA_HEIGHT, myImage.pixels);
				imagePanel.updateImage(raster);
			}
			// Je pense que comme ca, ca peut marcher, t'auras juste a decommenter et creer l'objet Traitement ou il faudra
			
			//Traitement traitement = new Traitement();
			//traitement.updateConnexe(virtualTab);
			//traitement.getGroupesPos(); // te renvoie une liste de points (coordonnées en double) qui sont les centres des taches (normalement)
			  
			 
			
			
		}
	}

	/**
	 * permet d'afficher la cam ainsi que le tracking des objets et les valeurs
	 * des positions
	 * 
	 * @param img
	 *            represente ce que l'on get de la cam
	 * @param posX
	 *            est la position horizontal de l'objet
	 * @param posY
	 *            est la position vertical de l'objet
	 */
	private void paint(int posX, int posY, double cupos) {
		Graphics g = jp.getGraphics();
		path.setSize(CAMERA_WIDTH, CAMERA_HEIGHT); // donne en parametre la
													// hauteur et la largeur de
													// l'img
		g.clearRect(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		g.setColor(Color.green);
		g.fillOval(posX, posY, 10, 10);
		g.drawOval(posX, posY, 10, 10);
	}

	public boolean setupCameras() {
		System.out.println("Getting number of cameras");

		// Checks available cameras
		int numCams = CLCamera.cameraCount();
		System.out.println("Found " + numCams + " cameras");
		if (numCams != 1)
			return false;

		// create cameras and start capture
		// Prints Unique Identifier per camera
		System.out.println("UUID " + CLCamera.cameraUUID(0));
		myCamera = new CLCamera();

		// ----------------------(i, CLEYE_GRAYSCALE/COLOR, CLEYE_QVGA/VGA,
		// Framerate)
		myCamera.createCamera(0, CLCamera.CLEYE_COLOR_PROCESSED,
				CLCamera.CLEYE_VGA, cameraRate);

		// Starts camera captures
		myCamera.startCamera();
		myImage = new PImage(CAMERA_WIDTH, CAMERA_HEIGHT, PImage.RGB);

		// resize the output window
		canvas.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
		path.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
		System.out.println("Complete Initializing Cameras");

		return true;
	}

	public VirtualPixel[][] getVirtualTab() {
		return virtualTab;
	}

	public void setVirtualTab(VirtualPixel[][] virtualTab) {
		this.virtualTab = virtualTab;
	}
	
	
	
	
	
}
