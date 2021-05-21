package networking;

public class AnimalPost extends OrganismPost {
	
	private int food;
	
	public AnimalPost(double x, double y, int reproductionTimer, int food) {
		super(x, y, reproductionTimer);
		this.food = food;
	}
	
	public int getFood() {
		return food;
	}

}
