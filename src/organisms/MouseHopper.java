package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class MouseHopper extends Animal {

	public MouseHopper(double x, double y, double w, double h, int price, int reproductionCount, int foodCount,
			PImage image, DrawingSurface game) {
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
	public void reproduce(DrawingSurface game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCost() {
		return 50;
	}

}
