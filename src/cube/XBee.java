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

    int [] buf =  new int[209];

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
	// Initialize the Array where the Frame will be
	for(int i=0; i<209; i++)
		buf[i] = 0;
	
	// Read char by char until next frame (0x7E)
	// There is 209 char maximum.
	int n = 0;
	while(n < 209) {
		int b;
		b = readByte();
		if(b==0x7e) 
			// New frame detected, use the previous
			return;
		
		// Unescape escaped chars
		if(b==0x7d) {
			b = readByte();
			b = b ^ 0x20;
		}
		
		// Until that, we complete the buffer
		buf[n] = b;
		n = n+1;
	}
}

/* Parse the frame in order to decode the data */
public void parseRXFrame (){
	// XXX : TODO : Calculate Checksum
	int addr = buf[3]*256 + buf[4];

	try {
	char [] c = {(char)buf[7],(char)buf[8],(char)buf[9],(char)buf[10],(char)buf[11],(char)buf[12],(char)buf[13],(char)buf[14]};
	String s = new String(c);
	int angle = (int)Long.parseLong(s, 16);
	
	// Put the angle in the cube which has the good address.
	manager.getCube(addr).setAngle(angle);	
	} catch (Exception e){
		e.printStackTrace();
	}
}

/* Send the frame which will diffuse the message given */
public void sendRXFrame (String message){
	byte[] msg =  new byte[200];
    byte[] dataSend = new byte[208];
	byte sum = 0x00;
	String st = "";
	
	try{
		msg = message.getBytes();

	    // send the begin of the frame (0x7E)
		dataSend[0] = 0x7E;
		dataSend[1] = 0x00;
		dataSend[2] = (byte)(11 + message.length()); // Frame length
		dataSend[3] = 0x00;
		dataSend[4] = 0x01; // API identifier
		dataSend[5] = 0x52; // Frame ID
		dataSend[6] = 0x00; // 5-12 -> Broadcast mode
		dataSend[7] = 0x00;
		dataSend[8] = 0x00;
		dataSend[9] = 0x00;
		dataSend[10] = 0x00;
		dataSend[11] = 0x00;
		dataSend[12] = (byte)(-1);
		dataSend[13] = (byte)(-1);
		dataSend[14] = 0x04; // Send packet with Broadcast Pan ID
		
		for (int i=0; i<msg.length; i++){
			dataSend[i+15] = msg[i]; // Put the data in the packet
			sum = (byte) ((sum + msg[i]) & 0xFF);
		}
		
		sum = (byte) (((sum + dataSend[3] + dataSend[4] + dataSend[5] + dataSend[6] + dataSend[7] + dataSend[8] + dataSend[9] + dataSend[10] + dataSend[11] + dataSend[12] + dataSend[13] + dataSend[14])+0) & 0xFF);
		
		dataSend[15 + msg.length]= (byte) (0xFF - (sum)); // checksum				
		
	    outToServer.writeBytes(st); // Send the frame
	    outToServer.flush();
	} catch (Exception e){
	   e.printStackTrace();
	} 
	


}

}

