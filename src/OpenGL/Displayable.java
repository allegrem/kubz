package OpenGL;

public interface Displayable {
	public void paint();
	public Displayable getChildren();
	public void addChild(Displayable object);
	public int getIndex();
	public void setIndex();
	public int getTimeOut();
	public void setTimeOut(Int time);
}
