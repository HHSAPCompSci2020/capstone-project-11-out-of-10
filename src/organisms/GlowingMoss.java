package organisms;

import game.DrawingSurface;
import processing.core.PImage;

public class GlowingMoss extends Organism{

	private int reproductionIndex;
	public GlowingMoss(double x, double y, PImage image) {
		super(x, y, 10, 20, 20/*reproduction*/, image);
		reproductionIndex = 0;
	}

	@Override
	public void reproduce(DrawingSurface game) {
		game.add(new GlowingMoss(getX()+10,getY(),image));
		
	}

	@Override
	public int getCost() {
		return 30;
	}

	@Override
	public void act(DrawingSurface game) {
		if(reproductionIndex > reproductionCount/10-1) {
			reproduce(game);
			reproductionIndex = 0;
		}
		game.changeDNA(1);
		reproductionIndex++;
	}
	
}
