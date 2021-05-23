package networking;

/**
 * Represents an animal in the database
 * @author Nir Reiter
 *
 */
public class AnimalPost extends OrganismPost {
	
	private int food;
	
	/**
	 * Constructs a new animal in database with default values of 0
	 */
	public AnimalPost() {
		
	}
	
	/**
	 * Constructs an animal with specified values
	 * @param x x-coordinate of animal
	 * @param y y-coordinate of animal
	 * @param reproductionTimer how fast animal reproduces
	 * @param food how much food animal has
	 */
	public AnimalPost(double x, double y, int reproductionTimer, int food) {
		super(x, y, reproductionTimer);
		this.food = food;
	}
	
	/**
	 * returns food animal has
	 * @return food animal has
	 */
	public int getFood() {
		return food;
	}

}
