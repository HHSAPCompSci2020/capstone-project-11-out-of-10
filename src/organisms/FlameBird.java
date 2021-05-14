package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import processing.core.PImage;

public class FlameBird extends Animal{

	private int currencyIndex;
	
	public FlameBird(double x, double y, double w, double h, PImage image, DrawingSurface game) {
		super(x, y, w, h, 45/*reproduction*/, 10/*food*/, image, game);
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
		if(game.millis()%(foodCount*1000)==0) {
			if(!eat(game)) {
				System.out.println("NOFOODFORFLAMEBIRD");
			}
		}
		if(game.millis()%(reproductionCount*1000)==0) {
			reproduce(game);
		}
		if(game.millis()%(10000)==0) {
			game.changeDNA(2);
		}
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new FlameBird(getX()+10,getY(),getWidth(),getHeight(),image,game));
	}

	
}
