package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import processing.core.PImage;
import sprite.Sprite;

/**
 * 
 * @author Timothy Li
 *
 */
public abstract class Organism extends Sprite {
	protected int reproductionCount;
	
	public Organism(double x, double y, double w, double h, int count, PImage image, DrawingSurface game) {
		super(x, y, w, h, image);
		reproductionCount = count;
	}
	
	public void move(double dx, double dy) {
		translate(dx,dy);
	}
	
	public void remove(DrawingSurface game) {
		game.remove(this);
	}
	
	public abstract void reproduce(DrawingSurface game);
		//game.add(new Organism(getX()+10,getY(),getWidth(),getHeight(),price,reproductionCount,image,game));
	

	public abstract int getCost();
	
	public abstract void act(DrawingSurface game);
	
	public static int getCodeFromOrganism(Organism o) {
		if (o instanceof YellowberryTree)
			return 0;
		if (o instanceof GlowingMoss)
			return 1;
		if (o instanceof MouseHopper)
			return 2;
		if (o instanceof FlameBird)
			return 3;
		if (o instanceof FluffyRam)
			return 4;
		
		return -1;
	}
	
	public static Organism createOrganismFromCode(int c, double x, double y) {
		if (c == 0)
			return new YellowberryTree(x, y);
		if (c == 1)
			return new GlowingMoss(x, y);
		if (c == 2)
			return new MouseHopper(x, y);
		if (c == 3)
			return new FlameBird(x, y);
		if (c == 4)
			return new FluffyRam(x, y);
		
		return null;
	}
	
}
