package cube;

//import Xbee library

public class XBeeReceiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        XBeeReceive xbee = new XBeeReceive();
        xbee.setCubeManager();    //À COMPLÉTER
        xbee.start();

    }

}
