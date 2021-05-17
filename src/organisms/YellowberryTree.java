package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class YellowberryTree extends Organism {
	private int reproductionIndex;
	
	/**
	 * Constructs a YellowberryTree in the game
	 * @param x x-coordinate of the tree
	 * @param y y-coordinate of the tree
	 * @param image
	 */
	public YellowberryTree(double x, double y, PImage image) {
		super(x, y, 10, 20, 120/*reproduction*/, image);
		reproductionIndex = 0;;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new YellowberryTree(getX()+10,getY(),image));
	}

	@Override
	public int getCost() {
		return 100;
	}

	@Override
	public void act(DrawingSurface game) { // remember to add berries as well
		if(reproductionIndex > reproductionCount/10-1) {
			reproduce(game);
			reproductionIndex = 0;
		}
		game.setTotalBerries(game.getTotalBerries()+10);
		game.changeDNA(3);
		reproductionIndex++;
		System.out.println(game.getTotalBerries());
	}

}
