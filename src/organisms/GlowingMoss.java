package organisms;

import game.DrawingSurface;
import processing.core.PImage;

/**
 * Represents a Glowing Moss organism in the game
 * @author timothyli
 *
 */
public class GlowingMoss extends Organism{
	/**
	 * Constructs a GlowingMoss in the game
	 * @param x x-coordinate of the GlowingMoss
	 * @param y y-coordinate of the GlowingMoss
	 * @param image image of the animal
	 */
	public GlowingMoss(double x, double y, PImage image) {
		super(x, y, 40, 40, image, 2/*reproduction*/, 30/*cost*/, 1/*value*/, 3/*foodValue*/);
		reproductionIndex = 0;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		Organism baby = new GlowingMoss(getX(), getY(), image);
		findReproduceLocation(game, baby);
		game.add(baby);
	}
	
}
