
public class Obj {
	
	// Child-classes will override these.
	public boolean isR() {
		return false;
	}
	public boolean isG() {
		return false;
	}
	public boolean isB() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Obj";
	}

}
