package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class YellowberryTree extends Organism {
	
	protected int berryCount;
	
	/**
	 * Constructs a YellowberryTree in the game
	 * @param x x-coordinate of the tree
	 * @param y y-coordinate of the tree
	 * @param image
	 */
	public YellowberryTree(double x, double y, PImage image) {
		super(x, y, 60, 100, image, 12/*reproduction*/, 100/*cost*/, 3/*value*/, 2/*foodValue*/);
		reproductionIndex = 0;
		berryCount = 0;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		Organism baby = new YellowberryTree(getX(), getY(), image);
		findReproduceLocation(game, baby);
		game.add(baby);
	}

	@Override
	public void act(DrawingSurface game) {
		berryCount += 10;
		System.out.println(berryCount);
		super.act(game);
	}
	
	@Override
	public int getEaten(DrawingSurface game) {
		if (berryCount > 0) {
			berryCount-=2;
			return this.getFoodValue();
		} else return 0;
	}
	
	public int getBerryCount() {
		return berryCount;
	}

}
