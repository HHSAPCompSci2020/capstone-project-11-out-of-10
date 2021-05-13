package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class GlowingMoss extends Organism{

	public GlowingMoss(double x, double y, double w, double h, PImage image,
			DrawingSurface game) {
		super(x, y, w, h, 20/*reproduction*/, image, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reproduce(DrawingSurface game) {
		GlowingMoss x = new GlowingMoss(getX()+10,getY(),getWidth(),getHeight(),image,game);
		
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
	}
	
}
