package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class MouseHopper extends Animal {

	public MouseHopper(double x, double y, PImage image) {
		super(x, y, 10, 20, 30/*reproduction*/, 10/*food*/, image);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean eat(DrawingSurface game) {
		if(game.getTotalBerries()>1) {
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

}
