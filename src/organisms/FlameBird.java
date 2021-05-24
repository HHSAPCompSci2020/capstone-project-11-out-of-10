package organisms;

import java.util.ArrayList;
import java.util.Collection;

import game.DrawingSurface;
import processing.core.PImage;

/**
 * Represents a Flame Bird animal in the game.
 * @author Timothy Li
 *
 */
public class FlameBird extends Animal{
	
	/**
	 * Constructs a FlameBird
	 * @param x x-coordinate of the FlameBird
	 * @param y y-coordinate of the FlameBird
	 * @param image image of the animal
	 */
	public FlameBird(double x, double y, PImage image) {
		super(x, y, 30, 50, image, 5/*reproduction*/, 60/*cost*/, 2/*value*/, 5/*foodValue*/);
		reproductionIndex = 0;
	}

	@Override
	public boolean tryToEat(DrawingSurface game) {
		Collection<Organism> o = game.getOrganismList();
		ArrayList<Organism> food = new ArrayList<>();
		for (Organism organism : o) {
			if (organism instanceof YellowberryTree || organism instanceof GlowingMoss)
				food.add(organism);
		}
		target = findClosest(food);
		return target != null;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		Organism baby = new FlameBird(getX(), getY(), image);
		findReproduceLocation(game, baby);
		game.add(baby);
	}
}
