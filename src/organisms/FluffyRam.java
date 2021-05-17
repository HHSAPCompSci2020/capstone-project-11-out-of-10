package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import processing.core.PImage;

public class FluffyRam extends Animal {
	private int reproductionIndex;

	/**
	 * Constructs a FluffyRam in the game
	 * @param x x-coordinate of the FluffyRam
	 * @param y y-coordinate of the FluffyRam
	 * @param image image of the animal
	 */
	public FluffyRam(double x, double y, PImage image) {
		super(x, y, 10, 20, 60/*reproduction*/, 10/*food*/, image);
		reproductionIndex=0;
	}


	@Override
	public boolean eat(DrawingSurface game) {
		ArrayList<Organism> o = game.getList();
		for(Organism organism : o) {
			if(organism instanceof MouseHopper) {
				target = organism;
				//game.remove(organism);
				return true;
			}
		}
		return false;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new FluffyRam(getX()+10,getY(),image));
	}

	@Override
	public int getCost() {
		return 90;
	}

	@Override
	public void act(DrawingSurface game) {
		needToEat = true;
		if(!eat(game)) {
			System.out.println("NOFOODFORFLUFFYRAM");
			this.remove(game);
		}
		if(reproductionIndex > reproductionCount/10-1) {
			reproduce(game);
			reproductionIndex = 0;
		}
		game.changeDNA(3);
		reproductionIndex++;
	}

	public void update(DrawingSurface game) {
		if(needToEat == true)
			move(Math.atan2((target.getX()-getX()), (target.getY()-getY()))-Math.PI/2, 10);
		if(target != null && Math.abs(target.getX()-getX())<10) {
			velocityX = 0;
			velocityY = 0;
			needToEat = false;
			target.remove(game);
			target = null;
		}
		rect.x += velocityX;
		rect.y += velocityY;
	}
}
