package cube;

//import Xbee library
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

public class XBeeReceive {
// Attention ceci n'est qu'un exemple qui doit être modifié.
	private XBeeReceive() throws XBeeException {
		
		 XBee xbee = new XBee();
         
         try {
                 // replace with your com port and baud rate. this is the com port of my coordinator            
                 xbee.open("/dev/tty.usbserial-FTC889FB", 115200);
                
                 while (true) {
                         // put some arbitrary data in the payload
                         int[] payload = ByteUtils.stringToIntArray("the\nquick\nbrown\nfox");
                        
                         ZNetTxRequest request = new ZNetTxRequest(XBeeAddress64.BROADCAST, payload);
                         // make it a broadcast packet
                         request.setOption(ZNetTxRequest.Option.BROADCAST);

                        // log.info("request packet bytes (base 16) " + ByteUtils.toBase16(request.getXBeePacket().getPacket()));
                        
                         xbee.sendAsynchronous(request);
                         // we just assume it was sent.  that's just the way it is with broadcast.  
                         // no transmit status response is sent, so don't bother calling getResponse()
                                
                         try {
                                 // wait a bit then send another packet
                                 Thread.sleep(15000);
                         } catch (InterruptedException e) {
                         }
                 }
         } finally {
                 xbee.close();
         }

		
		
	}
	
}
