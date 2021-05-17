package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import processing.core.PImage;

public class FlameBird extends Animal{

	private int reproductionIndex;
	
	
	/**
	 * Constructs a FlameBird
	 * @param x x-coordinate of the FlameBird
	 * @param y y-coordinate of the FlameBird
	 * @param image image of the animal
	 */
	public FlameBird(double x, double y, PImage image) {
		super(x, y, 10, 20, 50/*reproduction*/, 10/*food*/, image);
		reproductionIndex = 0;
		needToEat = false;
	}

	@Override
	public boolean eat(DrawingSurface game) {
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
					/*while(Math.abs(organism.getX()-getX())>10) {
						double angle = Math.atan2(organism.getX()-getX(), organism.getY()-getY());
						move(angle,10);
					}
					velocityX=0;
					velocityY=0;*/
					target = organism;
					//game.remove(organism);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getCost() {
		return 60;
	}

	@Override
	public void act(DrawingSurface game) {
		needToEat = true;
		if(!eat(game)) {
			System.out.println("NOFOODFORFLAMEBIRD");
			this.remove(game);
		}
		if(reproductionIndex > reproductionCount/10-1) {
			reproduce(game);
			reproductionIndex = 0;
		}
		game.changeDNA(2);
		reproductionIndex++;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new FlameBird(getX()+10,getY(),image));
	}

	public void update(DrawingSurface game) {
		if(needToEat == true)
			move(Math.atan2((target.getX()-getX()), (target.getY()-getY()))-Math.PI/2, 10);
		if(target != null && Math.abs(target.getX()-getX())<10) {
			velocityX = 0;
			velocityY = 0;
			needToEat = false;
			if(target instanceof GlowingMoss)
				target.remove(game);
			target = null;
		}
		rect.x += velocityX;
		rect.y += velocityY;
	}
}
