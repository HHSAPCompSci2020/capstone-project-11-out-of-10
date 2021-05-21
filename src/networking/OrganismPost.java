package networking;

/**
 * Represents an Organism in the database
 * @author Nir Reiter
 *
 */
public class OrganismPost {
	
	public int organismType;
	public double x, y;
	public int food;
	public int reproductionTimer;
	
	/**
	 * Creates a new organism representation with default values of zero
	 */
	public OrganismPost() {
		
	}
	
	/**
	 * Creates a new organism representation with given values
	 * @param organismType Type of organism to create, as determined by Organism.getTypeNumber()
	 * @param x X position
	 * @param y Y position
	 * @param food Current amount of food. For organisms that don't need food (trees, moss) this value doesn't matter
	 * @param reproductionTimer Current index in reproduction count.
	 */
	public OrganismPost(int organismType, double x, double y, int food, int reproductionTimer) {
		this.organismType = organismType;
		this.x = x;
		this.y = y;
		this.food = food;
		this.reproductionTimer = reproductionTimer;
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
	
	public int getReproductionTimer() {
		return reproductionTimer;
	}
	
	public String toString() {
		return "Type: " + organismType + ", Location: (" + x + ", " + y + "), Food: " + food;
	}
}
