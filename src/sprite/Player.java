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
		this.balance = 300;
	}
	
	@Override
	// the update method should set hasChanged every time it is called
	public void update(DrawingSurface game) {
		
		int x = game.getXAxisInput();
		int y = game.getYAxisInput();
		
		double angle = Math.atan2(y, x);
		if (x == 0 && y == 0) {
			velocityX = 0;
			velocityY = 0;
			
			hasChanged = false;
			
		} else {
			movePolar(angle, MOVE_SPEED);
			
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
			
			hasChanged = true;
		}
		
		rect.x = Math.max(0, Math.min(rect.x, game.gameAreaWidth - rect.width));
		rect.y = Math.max(0, Math.min(rect.y, game.gameAreaHeight - rect.height));
	}
	
	public void changeBalance(int change) {
		balance += change;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public boolean hasChanged() {
		return hasChanged;
	}
	
	/**
	 * Gets the data object which corresponds to the Player's current state
	 * @return A new PlayerPost with correct fields
	 */
	public PlayerPost getDataObject() {
		return new PlayerPost(balance, rect.x, rect.y);
	}
	
	/**
	 * Sets this Player to match database information
	 * @param post The PlayerPost with the information
	 */
	public void matchPost(PlayerPost post) {
		setLocation(post.getX(), post.getY());
		balance = post.getBalance();
	}
	
}
