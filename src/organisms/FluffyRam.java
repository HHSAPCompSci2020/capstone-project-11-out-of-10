package organisms;

import java.util.ArrayList;
import java.util.Collection;

import game.DrawingSurface;
import processing.core.PImage;

public class FluffyRam extends Animal {

	/**
	 * Constructs a FluffyRam in the game
	 * @param x x-coordinate of the FluffyRam
	 * @param y y-coordinate of the FluffyRam
	 * @param image image of the animal
	 */
	public FluffyRam(double x, double y, PImage image) {
		super(x, y, 60, 40, image, 6/*reproduction*/, 90/*cost*/, 3/*value*/, 5/*foodValue*/);
		reproductionIndex=0;
	}


	@Override
	public boolean tryToEat(DrawingSurface game) {
		Collection<Organism> o = game.getList();
		for(Organism organism : o) {
			if(organism instanceof MouseHopper) {
				target = organism;
				//game.remove(organism);
				return true;
			}
		}
		return false;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.makeReproduceSound();
		Organism baby = new FluffyRam (getX(), getY(), image);
		findReproduceLocation(game, baby);
		game.add(baby);
	}
}
