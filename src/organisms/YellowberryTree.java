package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class YellowberryTree extends Organism {
	private int reproductionIndex;
	
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
		game.setTotalBerries(game.getTotalBerries()+1);
		game.changeDNA(3);
		reproductionIndex++;
	}

}
