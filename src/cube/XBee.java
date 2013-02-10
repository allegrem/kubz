package cube;

import java.io.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

public class XBee extends Thread implements Runnable{

    private CubeManager manager = new CubeManager();
    private String dataReceive = null;
    private String dataSend = null;
    private Scanner sc = new Scanner(System.in);

    byte [] byteReceive =  new byte[109];
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
    BufferedReader inFromServer;
    {
        try {
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
        	for (byte b : byteReceive)
        		System.out.format("%02X ", b);
        	System.out.println();
        	
            /*System.out.println("Your message :");

            mutex.lock();

            try{
                dataSend = sc.nextLine();
                outToServer.writeBytes(dataSend);
                outToServer.flush();
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                mutex.unlock();
            } */

        }

        /* clientSocket.close() manquant ??? */

    }

    //clientSocket.close();

/* Set the cube manager created at the beginning of the game */
public void setCubeManager(CubeManager cubeManager) {
        this.manager = cubeManager;
}

public void parse (byte[] msg){

     /*byte checksum;
     char length;
     char sourceAddress;
     short angle;

    /* Data length *
    length = (char) ((msg[1] << 8) + msg[2]);
    /* Verification byte, equal 0xFF if OK *
    checksum = msg[10];//msg[(byte) length + 3];

    //if ((msg[0] == 0x7E) & (checksum == 0xFF)){
        /* Get back the address of the sender *
        sourceAddress = (char) ((msg[4] << 8) + msg[5]);
        System.out.println(sourceAddress);
        /* Get back the actual value of the angle of the cube which is speaking *
        angle = (short) ((msg[9] << 8) + msg[8]);
        System.out.println(angle);
    //}*/
	
	byte checksum;
	short length;
	short sourceAddress;
	short angle;
	
	length = (short) (((short)msg[1]+128)*256 + ((short)msg[2]+128));
	checksum = msg[10];
	System.out.format("msg = ");
	for (byte b : msg)
		System.out.format("%02X ", b);
	System.out.println();
	
	if ((msg[0]== 0x7E)){
		System.out.println("salut");
		sourceAddress = (short) (((short)msg[4]+128)*256 + ((short)msg[5]+128));
		System.out.println(sourceAddress);
	}
	
}

public void setDataSend (String s){
    this.dataSend = s;
}


public byte readByte() {
	byte [] b = new byte[1];
	int n=0;
	while(n==0) {
		try {
			n = clientSocket.getInputStream().read(b, 0, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return b[0];
}


public void readFrame (){
	// Initialise le tableau dans lequel on va stocker la trame
	for(int i=0; i<109; i++)
		byteReceive[i] = 0;
	
	// Lit caractère par caractère, jusqu'au début de trame suivant (0x7E)
	// et au plus 109 caractères.
	int n = 0;
	while(n < 109) {
		byte b;
		b = readByte();
		if(b==0x7e) 
			// On a un marqueur de début de trame, on renvoie la trame actuelle
			return;
		
		// Sinon, on accumule les caractères dans byteReceive
		byteReceive[n] = b;
		n = n+1;
	}
}
}
