package sprite;

import game.DrawingSurface;
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
	 * Always travels at the same speed (Player.MOVE_SPEED), no matter the angle
	 * @param angle The angle to walk at
	 */
	public void walk(double angle) {
		velocityX = MOVE_SPEED*Math.cos(angle);
		velocityY = -MOVE_SPEED*Math.sin(angle);
	}
	
	@Override
	public void update(DrawingSurface game) {
		velocityX = 0;
		velocityY = 0;
		
		if (game.isKeyHeld(DrawingSurface.RIGHT))
			walk(0);
		
		else if (game.isKeyHeld(DrawingSurface.UP))
			walk(0.5 * Math.PI);
		
		else if (game.isKeyHeld(DrawingSurface.LEFT))
			walk(Math.PI);
		
		else if (game.isKeyHeld(DrawingSurface.DOWN))
			walk(1.5 * Math.PI);
		
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
	
}
