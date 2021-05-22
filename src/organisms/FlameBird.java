package organisms;

import java.util.Collection;

import game.DrawingSurface;
import processing.core.PImage;

public class FlameBird extends Animal{
	
	/**
	 * Constructs a FlameBird
	 * @param x x-coordinate of the FlameBird
	 * @param y y-coordinate of the FlameBird
	 * @param image image of the animal
	 */
	public FlameBird(double x, double y, PImage image) {
		super(x, y, 30, 50, image, 5/*reproduction*/, 60/*cost*/, 2/*value*/, 3/*foodValue*/);
		reproductionIndex = 0;
	}

	@Override
	public boolean tryToEat(DrawingSurface game) {
		Collection<Organism> o = game.getOrganismList();
			for(Organism organism : o) {
				if(organism instanceof YellowberryTree && ((YellowberryTree)organism).getBerryCount()>0) {
					target = organism;
					return true;
				}
			}
			for(Organism organism : o) {
				if(organism instanceof GlowingMoss) {
					target = organism;
					return true;
				}
			}
		return false;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.makeReproduceSound();
		Organism baby = new FlameBird(getX(), getY(), image);
		findReproduceLocation(game, baby);
		game.add(baby);
	}
}
