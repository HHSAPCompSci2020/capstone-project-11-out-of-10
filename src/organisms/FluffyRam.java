package organisms;

import game.DrawingSurface;
import game.MainScreen;
import processing.core.PImage;

public class FluffyRam extends Animal {

	public FluffyRam(double x, double y, double w, double h, int price, int reproductionCount, int foodCount,
			PImage image, MainScreen game) {
		super(x, y, w, h, price, reproductionCount, foodCount, image, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean lookForFood() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reproduce(MainScreen game) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getCost() {
		return 90;
	}

}
