package cube;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

import cubeManager.CubeManager;

public class XBee extends Thread implements Runnable{

	/* Ref on the cube manager in order to actualize the good cube*/
    private CubeManager manager = new CubeManager();
    
    private String dataReceive = null;
    private Scanner sc = new Scanner(System.in);

    int [] buf =  new int[109];

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
            e.printStackTrace();  
        }
    }
    
     @Override
    public void run () {
       	while(true){
            readFrame();
       		parseRXFrame();

       	}

    }

/* Set the cube manager created at the beginning of the game */
public void setCubeManager(CubeManager cubeManager) {
        this.manager = cubeManager;
}

/* Read the next byte receive */
public int readByte() {
	byte [] b = new byte[1];
	int n=0;
	while(n==0) {
		try {
			n = inStream.read(b, 0, 1);
			} catch (IOException e){
			e.printStackTrace();
		}
	}
	return (int) b[0] & 0xff;
}

/* Read the complete next frame */
public void readFrame (){
	// Read char by char until next frame (0x7E)
	// There is 209 char maximum.
	int n = 0;
	while(n < 109) {
		int b;
		b = readByte();
		if(b==0x7e) 
			// New frame detected, use the previous
			return;
		
		// Until that, we complete the buffer
		buf[n] = b;
		n = n+1;
	}
}

/* Parse the frame in order to decode the data */
public void parseRXFrame (){
	// XXX : TODO : Calculate Checksum + vérif ID + vérif longueur
	int addr = buf[3]*256 + buf[4];
	System.out.println("addr = " + addr);
	
	try {
	//char [] c = {(char)buf[7],(char)buf[8],(char)buf[9],(char)buf[10],(char)buf[11],(char)buf[12],(char)buf[13],(char)buf[14]};
	//String s = new String(c);
	//int angle = (int)Long.parseLong(s, 16);
	
	int angle = buf[7];
		
	System.out.println("angle = " + angle);
	
	// Put the angle in the cube which has the good address.
	manager.getCube(addr).setAngle(angle);	
	} catch (Exception e){
		e.printStackTrace();
	}
}

/* Send the frame which will diffuse the message given */
public void sendTXFrame (byte[] message, int addr){
    byte[] dataSend = new byte[message.length + 9];
	byte sum = 0;
	int frameLength = 5 + message.length;
	
	try{
		
	    // send the begin of the frame (0x7E)
		dataSend[0] = 0x7E; //0x7E
		dataSend[1] = (byte)(frameLength/256);
		dataSend[2] = (byte)(frameLength); // Frame length
		dataSend[3] = 0x01;
		dataSend[4] = 'M'; // Frame ID
		dataSend[5] = (byte)0xFF; // 0xFF
		dataSend[6] = (byte)0xFF; // 0xFF
		dataSend[7] = 0x01;
		
		for (int i=0; i<message.length; i++)
			dataSend[i+8] = message[i]; // Put the data in the packet
		
		for (int i = 3; i < (8 + message.length); i++)
			sum += dataSend[i];
		
		dataSend[9 + message.length]= (byte)(-1 - sum); // checksum				
		
	    outToServer.write(dataSend); // Send the frame
	    outToServer.flush();
	} catch (Exception e){
	   e.printStackTrace();
	} 
	


}

}

