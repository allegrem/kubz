package cube;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

import cubeManager.CubeManager;

public class XBee extends Thread implements Runnable{

    private CubeManager manager = new CubeManager();
    private String dataReceive = null;
    private String dataSend = null;
    private Scanner sc = new Scanner(System.in);

    int [] buf =  new int[109];
    ReentrantLock mutex = new ReentrantLock(true);

    /* Socket part, adapted from http://systembash.com/content/a-simple-java-tcp-server-and-tcp-client/ */
    /* Open a new socket on localhost:4161 */
    Socket clientSocket;
    {
         try {
            clientSocket = new Socket("localhost", 4161);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Creating a buffer for the data receive */
    private InputStream inStream;
    {
        try {
            inStream = clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Creating a data output object to cast what we want to send to the server */
    DataOutputStream outToServer;
    {
        try {
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void run () {

        while (true){
            readFrame();
            System.out.format("msg = ");
        	for (int b : buf)
        		System.out.format("%02X ", b);
        	System.out.println();
        	parseRXFrame();
        }
    }

/* Set the cube manager created at the beginning of the game */
public void setCubeManager(CubeManager cubeManager) {
        this.manager = cubeManager;
}

public void setDataSend (String s){
    this.dataSend = s;
}

public int readByte() {
	byte [] b = new byte[1];
	int n=0;
	while(n==0) {
		try {
			n = inStream.read(b, 0, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return (int) b[0] & 0xff;
}


public void readFrame (){
	// Initialise le tableau dans lequel on va stocker la trame
	for(int i=0; i<109; i++)
		buf[i] = 0;
	
	// Lit caractère par caractère, jusqu'au début de trame suivant (0x7E)
	// et au plus 109 caractères.
	int n = 0;
	while(n < 109) {
		int b;
		b = readByte();
		if(b==0x7e) 
			// On a un marqueur de début de trame, on renvoie la trame actuelle
			return;
		
		// Unescape escaped chars
		if(b==0x7d) {
			b = readByte();
			b = b ^ 0x20;
		}
		
		// Sinon, on accumule les caractères dans byteReceive
		buf[n] = b;
		n = n+1;
	}
}

public void parseRXFrame (){
	// XXX : TODO : Calculate Checksum
	int addr = buf[3]*256 + buf[4];

	char [] c = {(char)buf[7],(char)buf[8],(char)buf[9],(char)buf[10],(char)buf[11],(char)buf[12],(char)buf[13],(char)buf[14]};
	int angle = (int)Long.parseLong(new String(c), 16);
	//manager.getCube(addr).setAngle(angle);	
}

}

