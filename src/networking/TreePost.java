package networking;

/**
 * Represents a tree in the database
 * @author Nir Reiter
 *
 */
public class TreePost extends OrganismPost {
	
	private int berries;
	
	/**
	 * Constructs a tree in database with default values
	 */
	public TreePost() {
		
	}
	
	/**
	 * Constructs a tree in database with specified values
	 * @param x x-coordinate of tree
	 * @param y y-coordinate of tree
	 * @param reproductionTimer how often tree reproduces
	 * @param berries how many berries tree has
	 */
	public TreePost(double x, double y, int reproductionTimer, int berries) {
		super(x, y, reproductionTimer);
		this.berries = berries;
	}
	
	/**
	 * gets how many berries tree has
	 * @return number of berries tree currently has
	 */
	public int getBerries() {
		return berries;
	}
	
}
