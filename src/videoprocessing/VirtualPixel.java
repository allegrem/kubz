package videoprocessing;

public class VirtualPixel {
	int x;
	boolean grayscale;
	int y;
	int groupeconn;
	public VirtualPixel(int posx, int posy, boolean gray, int gc )
	{
		this.groupeconn = gc;
		this.grayscale = gray;
		this.x = posx;
		this.y = posy;
	}
}