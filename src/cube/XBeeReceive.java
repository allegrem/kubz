package cube;

//import Xbee library

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;

public class XBeeReceive {
// Attention ceci n'est qu'un exemple qui doit être modifié.
    XBee xbee = new XBee();
    xbee.open("/dev/ttyXBee", 115200);

    while (true) {
        RxResponseIoSample ioSample = null;
        try {
            ioSample = (RxResponseIoSample) xbee.getResponse();
        } catch (XBeeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println("We received a sample from " + ioSample.getSourceAddress());

        if (ioSample.containsAnalog()) {
            System.out.println("10-bit temp reading (pin 19) is " + ioSample.getSamples()[0].getAnalog1();
        }
    }
	
}
