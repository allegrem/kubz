package cube;

import cubeManager.CubeManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import java.util.Scanner;

public class XBee extends Thread{

    private CubeManager manager = new CubeManager();
    private String dataReceive = null;
    private String dataSend = null;
    private Scanner sc = new Scanner(System.in);

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
            try {
                /* Putting the data receive in dataReceive and sending it to the terminal */
                dataReceive = inFromServer.readLine();
                System.out.println(dataReceive);
            } catch (IOException e){
                e.printStackTrace();
            }

            System.out.println("Your message :");
            dataSend = sc.nextLine();
            try{
                outToServer.writeBytes(dataSend);
                outToServer.flush();
            } catch (IOException e){
                e.printStackTrace();
            }



        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

/* Set the cube manager created at the beginning of the game */
public void setCubeManager(CubeManager cubeManager) {
        this.manager = cubeManager;
}

public void parse (byte[] msg){

     byte checksum;
     char length;
     char sourceAddress;
     short angle;

    /* Data length */
    length = (char) ((msg[1] << 8) + msg[2]);
    /* Verification byte, equal 0xFF if OK */
    checksum = msg[(int) length + 3];

    if ((msg[0] == 0x7E) & (checksum == 0xFF)){
        /* Get back the address of the sender */
        sourceAddress = (char) ((msg[4] << 8) + msg[5]);
        /* Get back the actual value of th angle of the cube which is speaking */
        angle = (short) ((msg[9] << 8) + msg[8]);
}
}
}
