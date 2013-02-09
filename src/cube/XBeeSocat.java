package cube;

import java.io.BufferedOutputStream;
import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: nico
 * Date: 09/02/13
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */

/* Adapted from http://edn.embarcadero.com/article/31995 */
public class XBeeSocat{

    /** Define a host server */
    String host = "localhost";
    /** Define a port */
    int port = 4161;

    StringBuffer instr = new StringBuffer();
    String TimeStamp;
    System.out.println("SocketClient initialized");

    try {
        /** Obtain an adress object of the server */
        InetAddress adress = InetAddress.getByName(host);
        /** Establishing a socket connection */
        Socket connection = new Socket(adress, port);
        /** Instantiate a BufferedOutputStream object */
        BufferedOutputStream bos = new BufferedOutputStream(connection.)
    }
       /* private val socket: Socket = new Socket(host,port)

        override protected val inStream = socket.getInputStream
        override protected val outStream = socket.getOutputStream

        init ()*/

}
