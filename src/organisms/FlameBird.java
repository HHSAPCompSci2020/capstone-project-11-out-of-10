package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import processing.core.PImage;

public class FlameBird extends Animal{
	
	/**
	 * Constructs a FlameBird
	 * @param x x-coordinate of the FlameBird
	 * @param y y-coordinate of the FlameBird
	 * @param image image of the animal
	 */
	public FlameBird(double x, double y, PImage image) {
		super(x, y, 30, 50, image, 5/*reproduction*/, 60/*cost*/, 2/*value*/, 3/*foodValue*/);
		reproductionIndex = 0;
		needToEat = false;
	}

	@Override
	public boolean tryToEat(DrawingSurface game) {
		ArrayList<Organism> o = game.getList();
		if(game.getTotalBerries() >= 2) {
			for(Organism organism : o) {
				if(organism instanceof YellowberryTree) {
					target = organism;
					break;
				}
			}
			game.setTotalBerries(game.getTotalBerries()-2);
			return true;
		}
		else {
			for(Organism organism : o) {
				if(organism instanceof GlowingMoss) {
					target = organism;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new FlameBird(getX()+10,getY(),image));
	}
}
