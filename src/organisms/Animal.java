package organisms;

import game.DrawingSurface;
import processing.core.PImage;

/**
 * 
 * @author Timothy Li
 *
 */
public abstract class Animal extends Organism {
	protected int foodCount; // how often the animal has to eat

	public Animal(double x, double y, double w, double h, int price, int reproductionCount, int foodCount, PImage image, DrawingSurface game) {
		super(x, y, w, h, price, reproductionCount, image, game);
	}
	
	public abstract void eat();
	
	public abstract boolean lookForFood(); //proposal: combine into one, where eat returns bool on whether there is enough food
	
}
