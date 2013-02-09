package cube;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

public class XBeeSend {

    public XBeeSend() throws XBeeException {

        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REP);

        System.out.println("Binding hello world server");
        socket.bind ("tcp://*:5555");

        while (!Thread.currentThread ().isInterrupted ()) {

            // Wait for next request from client
            byte[] reply = socket.recv(0);
            System.out.println("Received " + reply.length );
            System.out.println("Received " + ": [" + new String(reply) + "]");

            Thread.sleep(1000);
            //  Create a "Hello" message.
            //  Ensure that the last byte of our "Hello" message is 0 because
            //  our "Hello World" server is expecting a 0-terminated string:
            String requestString = "Hello" ;
            byte[] request = requestString.getBytes();
            //request[request.length-1]=0; //Sets the last byte to 0
            // Send the message
            System.out.println("Sending response " + requestString );
            socket.send(request, 0);

            //  Get the reply.
        }

        socket.close();
        context.term();

    }

}
