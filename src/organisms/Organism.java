package organisms;

import java.util.ArrayList;

import game.DrawingSurface;
import game.MainScreen;
import processing.core.PImage;
import sprite.Sprite;

/**
 * 
 * @author Timothy Li
 *
 */
public class Organism extends Sprite {
	protected int reproductionCount;
	protected int price;
	
	public Organism(double x, double y, double w, double h, int organismPrice, int count, PImage image) {
		super(x, y, w, h, image);
		reproductionCount = count;
		price = organismPrice;
		//organisms.add(this);
	}
	
	public void move(double dx, double dy) {
		translate(dx,dy);
	}
	
	public void remove(MainScreen game) {
		game.remove(this);
	}
	
	public void reproduce(MainScreen game) {
		game.add(new Organism(getX()+10,getY(),getWidth(),getHeight(),price,reproductionCount,image));
	}
	
	
}
