package networking;

public class TreePost extends OrganismPost {
	
	private int berries;
	
	public TreePost() {
		
	}
	
	public TreePost(double x, double y, int reproductionTimer, int berries) {
		super(x, y, reproductionTimer);
		this.berries = berries;
	}
	
	public int getBerries() {
		return berries;
	}
	
}
