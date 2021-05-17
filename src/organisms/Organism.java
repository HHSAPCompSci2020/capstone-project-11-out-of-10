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
	
	/**
	 * Constructs the organism in the game
	 * @param x x-coordinate of the organism
	 * @param y y-coordinate of the organism
	 * @param w width of the organism
	 * @param h height of the organism
	 * @param count how often the animal reproduces
	 * @param image image of the animal
	 */
	public Organism(double x, double y, double w, double h, int count, PImage image) {
		super(x, y, w, h, image);
		reproductionCount = count;
	}
	
	/**
	 * moves the organism
	 * @param angle angle that the organism moves in
	 * @param speed how fast the organism moves
	 */
	public void move(double angle, double speed) {
		velocityX = speed*Math.cos(angle);
		velocityY = -speed*Math.sin(angle);
	}
	
	/**
	 * removes organism from game
	 * @param game DrawingSurface the organism is in
	 */
	public void remove(DrawingSurface game) {
		game.remove(this);
	}
	
	/**
	 * Allows the animal to reproduce
	 * @param game DrawingSurface the organism is in
	 */
	public abstract void reproduce(DrawingSurface game);

	/**
	 * gets the cost of the organism
	 * @return the cost of the organism
	 */
	public abstract int getCost();
	
	/**
	 * Something that is called every 10 seconds, what the organism does
	 * @param game DrawingSurface that the organism is in
	 */
	public abstract void act(DrawingSurface game);
	
	/**
	 * gets the index (number) of the organism
	 * @param o organism to be indexed
	 * @return the index corresponding to the organism to be added
	 */
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
	
	/**
	 * Creates the organism using the index
	 * @param c the index
	 * @param x x-coordinate of the Organism
	 * @param y y-coordinate of the Organism
	 * @param d DrawingSurface the Organism will be in
	 * @returnx
	 */
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
