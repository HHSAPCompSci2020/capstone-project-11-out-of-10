package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class YellowberryTree extends Organism {
	private int berryCount;
	
	public YellowberryTree(double x, double y, PImage image) {
		super(x, y, 10, 20, 120/*reproduction*/, image);
		berryCount = 10;
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
		if(game.millis()%(reproductionCount*1000)==0) {
			reproduce(game);
		}
		if(game.millis()%(berryCount*1000)==0) {
			game.setTotalBerries(game.getTotalBerries()+1);
		}
		if(game.millis()%(10000)==0) {
			game.changeDNA(3);
		}
	}

}
