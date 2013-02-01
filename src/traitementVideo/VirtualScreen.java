package traitementVideo;

public class VirtualScreen {
	
	private VirtualPixel[][] virtualImage = new VirtualPixel[240][240];

	public VirtualPixel[][] getVirtualImage() {
		return virtualImage;
	}

	public void setVirtualImage(VirtualPixel[][] virtualImage) {
		this.virtualImage = virtualImage;
	}
	
	
}
