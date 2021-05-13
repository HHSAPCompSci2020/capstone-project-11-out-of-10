package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class YellowberryTree extends Organism {
	private int berryCount;
	
	public YellowberryTree(double x, double y, double w, double h, PImage image,
			DrawingSurface game) {
		super(x, y, w, h, 120/*reproduction*/, image, game);
		berryCount = 10;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		YellowberryTree x = new YellowberryTree(getX()+10,getY(),getWidth(),getHeight(),image,game);
		
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
	}

}
