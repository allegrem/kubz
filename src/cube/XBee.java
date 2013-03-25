package cube;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

import cubeManager.CubeManager;

public class XBee extends Thread implements Runnable{

	/* Ref on the cube manager in order to actualize the good cube*/
    private CubeManager manager = new CubeManager(this);
    
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

    int i = 0;
    
	@Override
    public void run () {
       	while(true){
            readFrame();
       		parseRXFrame();
       		manager.getCube(45679).setIR((byte)5);
       		}

    }

/* Set the cube manager created at the beginning of the game */
public void setCubeManager(CubeManager cubeManager) {
        this.manager = cubeManager;
}

/* Read the next byte receive */
private int readByte() {
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

/* Read the next unescaped byte received */
private int readByteUnescaped() {
	int b;
	b = readByte();
	if (b==0x7d)
		b=readByte() ^ 0x20;	
	return b;
}

/* Read the complete next frame */
private void readFrame (){
	while(true){
	// Read char by char until first 0x7E
	while(readByte()!=(byte)0x7E)
		
	buf[0]=(byte)0x7E;	
	
	// Get frame size
	buf[1] = readByteUnescaped();
	buf[2] = readByteUnescaped();
	int size =  (int)(buf[1])*256+(int)(buf[2]);
	
	// Verify size
	if(size>105) {
	  System.out.println("Impossible size " + size);
	  continue;
	}

	// Get payload
	int n = 0;
	while(n<size) {
	  // Store incoming chard
	    buf[3+n] = readByteUnescaped();
	    n = n+1;
	}

	// Get checksum
	buf[3+size] = readByteUnescaped();
	
	 // Verify checksum
	 byte sum=0;
	 for(int i=0; i<(size+1); i++)
	    sum += buf[3+i];
	 if(sum!=(byte)0xff) {
	    System.out.println("Bad checksum");
	    continue;
	  }
	 else return;
	}
}

/* Choose the kind of Frame it is and execute the parsing which is adapted */
private void parseRXFrame(){
	switch(buf[3]) {
    case 0x89:
      //System.out.println("ACK");
      break;
    case 0x80 : 
      System.out.println("64bits RX frame");
      break;
    case 0x81 : 
      System.out.println("16bits RX frame");
      parse16BitFrame();
      break;
    default:
      System.out.println("Unknown frame ID" + buf[3]);
      break;
	}
}

/* Parse the frame in order to decode the data */
private void parse16BitFrame (){
	int addr = buf[4]*256 + buf[5];
	if (addr == 15075)
		System.out.println(String.format("address = %d", addr));
	
	try {
	int angle =buf[9]*256+buf[8];
	if (angle > 32767)
		angle = angle - 65536;
	if (addr==15075)
		System.out.println(String.format("angle = %d", angle));
	
	// Put the angle in the cube which has the good address.
	manager.getCube(addr).setAngle(angle);	
	
	int tap = buf[10];
	//System.out.println(String.format("tap = %d", tap));
	
	manager.getCube(addr).setTap(tap);
	//System.out.print("valeur de tap du cube = ");
	//System.out.println(manager.getCube(addr).getTap());
	
	} catch (Exception e){
		e.printStackTrace();
	}
}

/* Send the frame which will diffuse the message given */
public synchronized void sendTXFrame (byte[] message, int addr){
    byte[] dataSend = new byte[message.length + 9];
	int frameLength = 5 + message.length;
	
	try{
	    // send the begin of the frame (0x7E)
		dataSend[0] = 0x7E; //0x7E
		dataSend[1] = (byte)(frameLength/256);
		dataSend[2] = (byte)(frameLength); // Frame length
		dataSend[3] = 0x01;
		dataSend[4] = (byte)(65+Math.random()*26); // Frame ID
		dataSend[5] = (byte)(addr/256); // 0xFF
		dataSend[6] = (byte)(addr & 0xFF); // 0xFF
		dataSend[7] = 0x00;
		
		for (int i=0; i<message.length; i++)
			dataSend[i+8] = message[i]; // Put the data in the packet
		
		byte sum = 0;
		for (int i = 3; i < (8 + message.length); i++)
			sum += dataSend[i];
		
		dataSend[8 + message.length]= (byte)(-1 - sum); // checksum				
		
		outToServer.write(0x7e);
		for (int i=1; i<dataSend.length; i++) {
			if((dataSend[i]==0x7e) || (dataSend[i]==0x7d) || (dataSend[i]==0x11) || (dataSend[i]==0x13)) {
			      outToServer.write(0x7d);
			      outToServer.write(dataSend[i] ^ 0x20);
			}	
			else	
				outToServer.write(dataSend[i]);
		}
	    outToServer.flush();
	} catch (Exception e){
	   e.printStackTrace();
	} 
}


}

