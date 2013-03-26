package kubzDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import cube.Cube;
import cube.XBee;
import cubeManager.CubeManager;

/**
 * La fenetre principale du programme.
 * 
 * @author paul
 *
 */
public class Fenetre extends JFrame implements WindowListener{
	private Container conteneur=this.getContentPane(); //On cree un objet qui pointe vers le conteneur de la fenetre (auquel on peut ajouter des JObjects)
	private int R=127;
	private int G=127;
	private int B=127;
	private int V=127;
	private Cube cube;
	private Get get ;

	private JLabel label1=new JLabel("Angle= 0");
	private JLabel label2=new JLabel("..........");
	
	/**
	 * Construction de la fenetre
	 * 
	 * @param args Les arguments fournis au lancement du programme
	 */
	public Fenetre(){
		setTitle("Kubz manager");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(500,500);
		XBee xbee = new XBee(); // Create a new XBee     
	    CubeManager cubeManager = new CubeManager(xbee); // Create a new cube manager
	    xbee.setCubeManager(cubeManager); // Add the cube manager in the XBee  
	    xbee.start(); // Start the XBee thread
	    cube=cubeManager.getCube(53192);
	   // cube.setIR((byte)5);
	    get =new Get(this);
	    get.start();
		conteneur.setLayout(new GridLayout(1,5));// On cree un layout de type border pour le conteneur
		conteneur.add(new RSlider(0, 255,this));
		conteneur.add(new GSlider(0, 255,this));
		conteneur.add(new BSlider(0, 255,this));
		conteneur.add(new VSlider(0, 255,this));
		conteneur.add(label1);
		conteneur.add(label2);
		send();

	}
	
	public void send(){
		cube.setRGB(R,G,B,(short)1);
		cube.setMotor((byte)V);
	}


	public int getV() {
		return V;
	}


	public void setV(int v) {
		V = v;
		send();
	}


	public int getR() {
		return R;
	}


	public int getG() {
		return G;
	}


	public int getB() {
		return B;
	}


	public void setR(int r) {
		R = r;
		send();
	}


	public void setG(int g) {
		G = g;
		send();
	}


	public void setB(int b) {
		B = b;
		send();
	}



	public void setLabel1(String string) {
		label1.setText(string);
	}
	
	public void setLabel2(String string) {
		label2.setText(string);
	}


	public Cube getCube() {
		return cube;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		get.interrupt();
		this.dispose();
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}