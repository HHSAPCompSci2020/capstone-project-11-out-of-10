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
	protected int price;
	
	public Organism(double x, double y, double w, double h, int organismPrice, int count, PImage image, DrawingSurface game) {
		super(x, y, w, h, image);
		reproductionCount = count;
		price = organismPrice;
		game.add(this);
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
	
	
}
