package cube;

import cubeManager.CubeManager;


public class XBee extends Thread{

    private CubeManager manager = new CubeManager();

    public void run () {






    }



/* Permet de récupérer le cube manager pour donner les informations au bon cube */
public void setCubeManager(CubeManager cubeManager) {
        this.manager = cubeManager;
}

public void parse (byte[] msg){

     byte checksum;
     char length;
     char sourceAdress;
     short angle;

    /* Longueur des données */
    length = (char) ((msg[1] << 8) + msg[2]);
    /* Octet de vérification, = 0xFF si ok */
    checksum = msg[(int) length + 3];

    if ((msg[0] == 0x7E) & (checksum == 0xFF)){
        /* Récupération de l'adresse du cube qui parle */
        sourceAdress = (char) ((msg[4] << 8) + msg[5]);
        /* Récupération de l'angle */
        angle = (short) ((msg[9] << 8) + msg[8]);
}
}
}
