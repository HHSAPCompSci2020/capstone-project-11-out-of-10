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
	protected int foodCount; // how often the animal has to eat

	public Animal(double x, double y, double w, double h, int reproductionCount, int foodCount, PImage image, DrawingSurface game) {
		super(x, y, w, h, reproductionCount, image, game);
		this.foodCount = foodCount;
	}
	
	public abstract boolean eat(DrawingSurface game);
	
}
