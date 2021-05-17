package organisms;

import game.DrawingSurface;
import processing.core.PImage;
import sprite.Sprite;

/**
 * 
 * @author Timothy Li
 *
 */
public abstract class Organism extends Sprite {
	
	protected int reproductionTicks;
	protected int reproductionIndex;
	protected int cost;
	protected int value;
	protected int foodValue;
	
	/**
	 * Constructs the organism in the game
	 * @param x x-coordinate of the organism
	 * @param y y-coordinate of the organism
	 * @param w width of the organism
	 * @param h height of the organism
	 * @param count how often the animal reproduces
	 * @param image image of the animal
	 */
	public Organism(double x, double y, double w, double h, PImage image, int reproductionTicks, int cost, int value, int foodValue) {
		super(x, y, w, h, image);
		this.reproductionTicks = reproductionTicks;
		this.cost = cost;
		this.value = value;
		this.foodValue = foodValue;
	}
	
	/**
	 * moves the organism
	 * @param angle angle that the organism moves in
	 * @param speed how fast the organism moves
	 */
	public void move(double angle, double speed) {
		velocityX = speed*Math.cos(angle);
		velocityY = -speed*Math.sin(angle);
	}
	
	/**
	 * removes organism from game
	 * @param game DrawingSurface the organism is in
	 */
	public void remove(DrawingSurface game) {
		game.remove(this);
	}
	
	/**
	 * Allows the animal to reproduce
	 * @param game DrawingSurface the organism is in
	 */
	public abstract void reproduce(DrawingSurface game);
	
	/**
	 * Something that is called every 10 seconds, what the organism does
	 * @param game DrawingSurface that the organism is in
	 */
	public void act(DrawingSurface game) {
		if(reproductionIndex >= reproductionTicks) {
			reproduce(game);
			reproductionIndex = 0;
		}
		game.changeDNA(getDNAValue());
		reproductionIndex++;
	}
	
	/**
	 * gets the index (number) of the organism
	 * @param o organism to be indexed
	 * @return the index corresponding to the organism to be added
	 */
	public static int getCodeFromOrganism(Organism o) {
		if (o instanceof YellowberryTree)
			return 0;
		if (o instanceof GlowingMoss)
			return 1;
		if (o instanceof MouseHopper)
			return 2;
		if (o instanceof FlameBird)
			return 3;
		if (o instanceof FluffyRam)
			return 4;
		
		return -1;
	}
	
	/**
	 * Creates the organism using the index
	 * @param c the index
	 * @param x x-coordinate of the Organism
	 * @param y y-coordinate of the Organism
	 * @param d DrawingSurface the Organism will be in
	 * @return
	 */
	public static boolean createOrganismFromCode(int c, double x, double y, DrawingSurface d) {
		
		Organism o = null;
		PImage imageToUse = DrawingSurface.organismImages.get(c);
		
		if (c == 0)
			o = new YellowberryTree(x, y, imageToUse);
		else if (c == 1)
			o = new GlowingMoss(x, y, imageToUse);
		else if (c == 2)
			o = new MouseHopper(x, y, imageToUse);
		else if (c == 3)
			o = new FlameBird(x, y, imageToUse);
		else if (c == 4) 
			o = new FluffyRam(x, y, imageToUse);
		
		if (o == null || d.thisPlayer.getBalance() < o.getCost())
			return false;

		d.thisPlayer.changeBalance(-o.getCost());
		d.add(o);
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " located at (" + getX() + ", " + getY() + ")";
	}
	
	/**
	 * gets the cost of the organism
	 * @return the cost of the organism
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * @return DNA Value of the organism (per tick)
	 */
	public int getDNAValue() {
		return value;
	}
	
	/**
	 * @return Value of the organism when eaten
	 */
	public int getFoodValue() {
		return foodValue;
	}
}
