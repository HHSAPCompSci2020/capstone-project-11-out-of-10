package organisms;

import game.DrawingSurface;
import game.MainScreen;
import processing.core.PImage;

public class GlowingMoss extends Organism{

	public GlowingMoss(double x, double y, double w, double h, int organismPrice, int count, PImage image,
			MainScreen game) {
		super(x, y, w, h, organismPrice, count, image, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reproduce(MainScreen game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCost() {
		return 30;
	}
	
}
