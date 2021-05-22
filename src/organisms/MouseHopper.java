package organisms;

import java.util.ArrayList;
import java.util.Collection;

import game.DrawingSurface;
import processing.core.PImage;

public class MouseHopper extends Animal {
	
	/**
	 * constructs a MouseHopper in the game
	 * @param x x-coordinate of the MouseHopper
	 * @param y y-coordinate of the MouseHopper
	 * @param image image of the animal
	 */
	public MouseHopper(double x, double y, PImage image) {
		super(x, y, 40, 20, image, 3/*reproduction*/, 50/*cost*/, 2/*value*/, 3/*foodValue*/);
		reproductionIndex = 0;
	}


	@Override
	public boolean tryToEat(DrawingSurface game) {
		Collection<Organism> o = game.getList();
		if (game.getTotalBerries()>1) {
			for (Organism organism : o) {
				if (organism instanceof YellowberryTree) {
					target = organism;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.makeReproduceSound();
		game.add(new MouseHopper(getX()+10,getY(),image));
		
	}

}
