package organisms;

import java.util.Collection;

import game.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents an animal in the game
 * @author Timothy Li
 *
 */
public abstract class Animal extends Organism {
	/**
	 * how often the animal has to eat
	 */
	protected int foodCount;
	protected Organism target;

	
	/**
	 * Constructs an animal with coordinates and dimensions
	 * @param x x-coordinate of upper left corner
	 * @param y y-coordinate of upper left corner
	 * @param w width of animal
	 * @param h height of animal
	 * @param reproductionCount how often the animal reproduces in seconds
	 * @param foodCount how often the animal eats in seconds
	 * @param image image of the animal
	 */
	public Animal(double x, double y, double w, double h, PImage image, int reproductionTicks, int cost, int value, int foodValue) {
		super(x, y, w, h, image, reproductionTicks, cost, value, foodValue);
		this.foodCount = 2;
		this.target = null;
		wander();
	}
	
	/**
	 * the animal eats, unless there is not enough food, in that case ends game
	 * @param game The DrawingSurface the animal is in
	 * @return whether the animal has enough to eat or not
	 */
	public abstract boolean tryToEat(DrawingSurface game);
	
	@Override
	public void update(DrawingSurface game) {
		if (target != null) {
			if (!game.organisms.containsKey(target.getRef().getKey())) {
				target = null;
				return;
			}
			if (DrawingSurface.distance(this, target) < 10) {
				velocityX = 0;
				velocityY = 0;
				foodCount += target.getEaten(game);
				game.makeEatSound();
				target = null;
			} else
				move(Math.atan2(target.getX() - getX(), target.getY() - getY()) - Math.PI/2, 10);
		}
		super.update(game);
	}
	
	@Override
	public void act(DrawingSurface game) {
		foodCount--;
		if (foodCount < 3) {
			if (!tryToEat(game))
				wander(); // when it fails eating wander randomly
			
			if (foodCount < 0) {
				//System.out.println("No food for " + this);
				this.remove(game);
			}
		} else {
			wander(); // when it has enough food wander randomly
		}

		super.act(game);
	}
	
	/**
	 * Causes the animal to move in a random direction at a speed of 0.5
	 */
	public void wander() {
		movePolar(Math.random()*2*Math.PI, 0.5);
	}
	
	/**
	 * @param organisms List of all organisms to be checked
	 * @return Whichever organism is closest to this one
	 */
	public Organism findClosest(Collection<Organism> organisms) {
		double minDistance = Double.MAX_VALUE;
		Organism closest = null;
		for (Organism o : organisms) {
			double dist = DrawingSurface.distance(o, this);
			if (dist < minDistance) {
				minDistance = dist;
				closest = o;
			}
		}
		return closest;
	}
	
}
