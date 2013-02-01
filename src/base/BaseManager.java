package base;
import java.util.ArrayList;
public class BaseManager {
	private ArrayList<Base> baseList;
	
	public BaseManager(ArrayList<Base> baseList) {
		super();
		this.baseList = baseList;
	}
	public void add(Base base){
		baseList.add(base);
	}
	public void remove(Base base){
		baseList.remove(base);
	}
	public ArrayList<Base> getBaseList(){
		return baseList;
	}
}
