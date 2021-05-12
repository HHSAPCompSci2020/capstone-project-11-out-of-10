package sprite;

import game.DrawingSurface;
import networking.PlayerPost;
import processing.core.PImage;

/**
 * Class that represents a player in EcoWars. 
 * Can move, stores current balance, and deals with collision.
 * @author Nir Reiter
 *
 */
public class Player extends Sprite {
	
	/**
	 * Width of a Player (both sprite and collision)
	 */
	public static final int WIDTH = 32;
	
	/**
	 * Height of a Player (both sprite and collision)
	 */
	public static final int HEIGHT = 64;
	
	/**
	 * Distance a player travels on every frame of movement
	 */
	public static final int MOVE_SPEED = 5;
	
	private double balance;
	private boolean hasChanged;
	
	/**
	 * Creates a new Player with starting position and graphics.
	 * @param x The x-position to start
	 * @param y The y-position to start
	 * @param image The graphic for the Player
	 */
	public Player(int x, int y, PImage image) { 
		super(x, y, WIDTH, HEIGHT, image);
	}
	
	/**
	 * Changes the Player's velocity. 
	 * @param speed The speed to walk at
	 * @param angle The angle to walk at
	 */
	public void walk(double angle, double speed) {
		velocityX = speed*Math.cos(angle);
		velocityY = -speed*Math.sin(angle);
		
		hasChanged = (speed != 0);
	}
	
	@Override
	public void update(DrawingSurface game) {
		
		int x = game.getXAxisInput();
		int y = game.getYAxisInput();
		
		double angle = Math.atan2(y, x);
		walk(angle, (x == 0 && y == 0) ? 0 : MOVE_SPEED);
		
		/* Collision works separately on X and Y, so if a Player collides while moving diagonally,
		 * Player can still move on one axis (as long as there isn't another obstacle blocking the other axis)
		 */
		
		rect.x += velocityX;
		for (Sprite s : game.obstacles) {
			if (spriteCollide(s))
				rect.x -= velocityX;
		}
		
		rect.y += velocityY;
		for (Sprite s : game.obstacles) {
			if (spriteCollide(s))
				rect.y -= velocityY;
		}
		
		rect.x = Math.max(0, Math.min(rect.x, game.gameAreaWidth - rect.width));
		rect.y = Math.max(0, Math.min(rect.y, game.gameAreaHeight - rect.height));
	}
	
	public double getBalance() {
		return balance;
	}
	
	public boolean hasChanged() {
		return hasChanged;
	}
	
	public PlayerPost getDataObject() {
		return new PlayerPost(balance, rect.x, rect.y);
	}
	
}
