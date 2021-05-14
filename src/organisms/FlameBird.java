package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import processing.core.PImage;

public class FlameBird extends Animal{

	private int reproductionIndex;
	
	public FlameBird(double x, double y, PImage image) {
		super(x, y, 10, 20, 50/*reproduction*/, 10/*food*/, image);
		reproductionIndex = 0;
	}

	@Override
	public boolean eat(DrawingSurface game) {
		ArrayList<Organism> o = game.getList();
		if(game.getTotalBerries() >= 2) {
			game.setTotalBerries(game.getTotalBerries()-2);
			return true;
		}
		else {
			for(Organism organism : o) {
				if(organism instanceof GlowingMoss) {
					game.remove(organism);
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
		
		if(!eat(game)) {
			System.out.println("NOFOODFORFLAMEBIRD");
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

	
}
