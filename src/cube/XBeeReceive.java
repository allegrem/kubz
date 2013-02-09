package cube;

import cubeManager.CubeManager;
import java.lang.Byte;


public class XBeeReceive extends Thread{

    private byte[] msg = new byte[109];
    private byte checksum;
    private char length;
    private char sourceAdress;
    private short angle;

    private CubeManager manager = new CubeManager();

    /* Permet de récupérer le cube manager pour donner les informations au bon cube */
    public void setCubeManager(CubeManager cubeManager) {
         this.manager = cubeManager;
    }

    public void run () {

        /* Longueur des données */
        length = ((msg[1] << 8) + msg[2]);
        /* Octet de vérification, = 0xFF si ok */
        checksum = msg[(int) length + 3];

         if ((msg[0] == 0x7E) & (checksum == 0xFF)){
             /* Récupération de l'adresse du cube qui parle */
             sourceAdress = ((msg[4] << 8) + msg[5]);
             /* Récupération de l'angle */
             angle = ((msg[9] << 8) + msg[8]);



         }

    }
	
}
