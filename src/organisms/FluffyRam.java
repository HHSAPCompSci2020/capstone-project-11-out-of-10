package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import processing.core.PImage;

public class FluffyRam extends Animal {

	public FluffyRam(double x, double y, double w, double h, PImage image, DrawingSurface game) {
		super(x, y, w, h, 60/*reproduction*/, 10/*food*/, image, game);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean eat(DrawingSurface game) {
		ArrayList<Organism> o = game.getList();
		for(Organism organism : o) {
			if(organism instanceof MouseHopper) {
				game.remove(organism);
				return true;
			}
		}
		return false;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new FluffyRam(getX()+10,getY(),getWidth(),getHeight(),image,game));
	}

	@Override
	public int getCost() {
		return 90;
	}

	@Override
	public void act(DrawingSurface game) {
		if(game.millis()%(foodCount*1000)==0) {
			if(!eat(game)) {
				System.out.println("NOFOODFORFLUFFYRAM");
			}
		}
		if(game.millis()%(reproductionCount*1000)==0) {
			reproduce(game);
		}
		if(game.millis()%(10000)==0) {
			game.changeDNA(3);
		}
	}


}
