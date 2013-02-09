package cube;

/**
 * Created with IntelliJ IDEA.
 * User: nico
 * Date: 09/02/13
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */

import java.net.Socket;

public class XBeeSocat(host: String, port Int) extends XBee{

        private val socket: Socket = new Socket(host,port)

        override protected val inStream = socket.getInputStream
        override protected val outStream = socket.getOutputStream

        init ()

}
