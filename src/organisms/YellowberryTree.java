package organisms;

import game.MainScreen;
import processing.core.PImage;

public class YellowberryTree extends Organism{

	public YellowberryTree(double x, double y, double w, double h, int organismPrice, int count, PImage image,
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
		return 100;
	}

}
