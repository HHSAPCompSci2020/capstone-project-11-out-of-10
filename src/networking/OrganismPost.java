package networking;

public class OrganismPost {
	
	public int organismType;
	public double x, y;
	public int food;
	
	public OrganismPost() {
		
	}
	
	public OrganismPost(int organismType, double x, double y, int food) {
		this.organismType = organismType;
		this.x = x;
		this.y = y;
		this.food = food;
	}
	
	public int getOrganismType() {
		return organismType;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getFood() {
		return food;
	}
	
	public String toString() {
		return "Type: " + organismType + ", Location: (" + x + ", " + y + "), Food: " + food;
	}
}
