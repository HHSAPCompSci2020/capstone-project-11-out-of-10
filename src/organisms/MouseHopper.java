package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import processing.core.PImage;

public class MouseHopper extends Animal {

	private int reproductionIndex;
	
	/**
	 * constructs a MouseHopper in the game
	 * @param x x-coordinate of the Mousehopper
	 * @param y y-coordinate of the MouseHopper
	 * @param image image of the animal
	 */
	public MouseHopper(double x, double y, PImage image) {
		super(x, y, 10, 20, 30/*reproduction*/, 10/*food*/, image);
		reproductionIndex = 0;
	}


	@Override
	public boolean eat(DrawingSurface game) {
		ArrayList<Organism> o = game.getList();
		if(game.getTotalBerries()>1) {
			for(Organism organism : o) {
				if(organism instanceof YellowberryTree) {
					target = organism;
				}
			}
			game.setTotalBerries(game.getTotalBerries()-1);
			return true;
		}
		return false;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new MouseHopper(getX()+10,getY(),image));
		
	}

	@Override
	public int getCost() {
		return 50;
	}

	@Override
	public void act(DrawingSurface game) {
		needToEat = true;
		if(!eat(game)) {
			System.out.println("NOFOODFORMOUSEHOPPER");
			this.remove(game);
		}
		if(reproductionIndex > reproductionCount/10-1) {
			reproduce(game);
			reproductionIndex = 0;
		}
		game.changeDNA(2);
		reproductionIndex++;
	}
	
	public void update(DrawingSurface game) {
		if(needToEat == true)
			move(Math.atan2((target.getX()-getX()), (target.getY()-getY()))-Math.PI/2, 10);
		if(target != null && Math.abs(target.getX()-getX())<10) {
			velocityX = 0;
			velocityY = 0;
			needToEat = false;
			target = null;
		}
		rect.x += velocityX;
		rect.y += velocityY;
	}

}
