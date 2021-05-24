package organisms;

import java.util.ArrayList;
import java.util.Collection;

import game.DrawingSurface;
import processing.core.PImage;


/**
 * Represents a Fluffy Ram in the game
 * @author Timothy Li
 *
 */
public class FluffyRam extends Animal {

	/**
	 * Constructs a FluffyRam in the game
	 * @param x x-coordinate of the FluffyRam
	 * @param y y-coordinate of the FluffyRam
	 * @param image image of the animal
	 */
	public FluffyRam(double x, double y, PImage image) {
		super(x, y, 60, 40, image, 6/*reproduction*/, 90/*cost*/, 3/*value*/, 7/*foodValue*/);
		reproductionIndex=0;
	}


	@Override
	public boolean tryToEat(DrawingSurface game) {
		Collection<Organism> o = game.getOrganismList();
		ArrayList<Organism> mice = new ArrayList<>();
		for (Organism organism : o) {
			if (organism instanceof MouseHopper)
				mice.add(organism);
		}
		target = findClosest(mice);
		return target != null;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		Organism baby = new FluffyRam (getX(), getY(), image);
		findReproduceLocation(game, baby);
		game.add(baby);
	}
}
