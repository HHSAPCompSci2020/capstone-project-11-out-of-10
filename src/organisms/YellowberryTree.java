package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class YellowberryTree extends Organism {
	
	/**
	 * Constructs a YellowberryTree in the game
	 * @param x x-coordinate of the tree
	 * @param y y-coordinate of the tree
	 * @param image
	 */
	public YellowberryTree(double x, double y, PImage image) {
		super(x, y, 60, 100, image, 12/*reproduction*/, 100/*cost*/, 3/*value*/, 2/*foodValue*/);
		reproductionIndex = 0;;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new YellowberryTree(getX()+10,getY(),image));
	}

	@Override
	public void act(DrawingSurface game) { // remember to add berries as well
		game.setTotalBerries(game.getTotalBerries()+10);
		System.out.println(game.getTotalBerries());
		
		super.act(game);
	}
	
	@Override
	public int getEaten(DrawingSurface game) {
		if (game.totalBerries > 0) {
			game.totalBerries--;
			return this.getFoodValue();
		} else return 0;
	}

}
