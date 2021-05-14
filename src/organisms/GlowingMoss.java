package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class GlowingMoss extends Organism{

	public GlowingMoss(double x, double y, PImage image) {
		super(x, y, 10, 20, 20/*reproduction*/, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new GlowingMoss(getX()+10,getY(),image));
		
	}

	@Override
	public int getCost() {
		return 30;
	}

	@Override
	public void act(DrawingSurface game) {
		if(game.millis()%(reproductionCount*1000)==0) {
			reproduce(game);
		}
		if(game.millis()%(10000)==0) {
			game.changeDNA(1);
		}
	}
	
}
