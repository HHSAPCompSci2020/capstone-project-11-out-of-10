package organisms;

import game.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Timothy Li
 *
 */
public abstract class Animal extends Organism {
	/**
	 * how often the animal has to eat
	 */
	protected int foodCount;

	
	/**
	 * Constructs an animal with coordinates and dimensions
	 * @param x x-coordinate of upper left corner
	 * @param y y-coordinate of upper left corner
	 * @param w width of animal
	 * @param h height of animal
	 * @param reproductionCount how often the animal reproduces in seconds
	 * @param foodCount how often the animal eats in seconds
	 * @param image image of the animal
	 */
	public Animal(double x, double y, double w, double h, int reproductionCount, int foodCount, PImage image) {
		super(x, y, w, h, reproductionCount, image);
		this.foodCount = foodCount;
	}
	
	/**
	 * the animal eats, unless there is not enough food, in that case ends game
	 * @param game The DrawingSurface the animal is in
	 * @return whether the animal has enough to eat or not
	 */
	public abstract boolean eat(DrawingSurface game);
	
}
