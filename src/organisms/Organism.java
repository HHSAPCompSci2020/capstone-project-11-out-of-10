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
	
	public Organism(double x, double y, double w, double h, int count, PImage image) {
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
	
	public static Organism createOrganismFromCode(int c, double x, double y, DrawingSurface d) {
		if (c == 0) {
			d.changeDNA(-100);
			return new YellowberryTree(x, y, DrawingSurface.organismImages.get(0));
		}
		if (c == 1) {
			d.changeDNA(-30);
			return new GlowingMoss(x, y, DrawingSurface.organismImages.get(1));
		}
		if (c == 2) {
			d.changeDNA(-50);
			return new MouseHopper(x, y, DrawingSurface.organismImages.get(2));
		}
		if (c == 3) {
			d.changeDNA(-60);
			return new FlameBird(x, y, DrawingSurface.organismImages.get(3));
		}
		if (c == 4) {
			d.changeDNA(-90);
			return new FluffyRam(x, y, DrawingSurface.organismImages.get(4));
		}
		
		return null;
	}
	
}
