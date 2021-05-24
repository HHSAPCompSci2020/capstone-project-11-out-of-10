package organisms;

import java.util.ArrayList;
import java.util.Collection;

import game.DrawingSurface;
import processing.core.PImage;

/**
 * Represents a Mouse Hopper in the game
 * @author Timothy Li
 *
 */
public class MouseHopper extends Animal {
	
	/**
	 * constructs a MouseHopper in the game
	 * @param x x-coordinate of the MouseHopper
	 * @param y y-coordinate of the MouseHopper
	 * @param image image of the animal
	 */
	public MouseHopper(double x, double y, PImage image) {
		super(x, y, 40, 20, image, 3/*reproduction*/, 50/*cost*/, 2/*value*/, 4/*foodValue*/);
		reproductionIndex = 0;
	}


	@Override
	public boolean tryToEat(DrawingSurface game) {
		Collection<Organism> o = game.getOrganismList();
		ArrayList<Organism> trees = new ArrayList<>();
		for (Organism organism : o) {
			if (organism instanceof YellowberryTree || organism instanceof GlowingMoss)
				trees.add(organism);
		}
		target = findClosest(trees);
		return target != null;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		Organism baby = new MouseHopper(getX(), getY(), image);
		findReproduceLocation(game, baby);
		game.add(baby);
	}

}
