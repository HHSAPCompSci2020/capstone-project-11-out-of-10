package networking;

/**
 * Represents an Organism in the database
 * @author Nir Reiter
 *
 */
public class OrganismPost {
	
	public double x, y;
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
	public OrganismPost(double x, double y, int reproductionTimer) {
		this.x = x;
		this.y = y;
		this.reproductionTimer = reproductionTimer;
	}
	
	/**
	 * gets x-coordinate of organism
	 * @return x-coordinate of organism
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * gets y-coordinate of organism
	 * @return y-coordinate of organism
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * gets how often organism has to reproduce in 3s (so if returns 1, it reproduces every 3 seconds)
	 * @return how fast organism reproduces
	 */
	public int getReproductionTimer() {
		return reproductionTimer;
	}
	
	@Override
	public String toString() {
		return "Location: (" + x + ", " + y + ")";
	}
}
