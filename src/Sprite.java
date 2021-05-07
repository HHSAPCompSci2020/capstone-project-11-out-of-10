import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents an object in the game with a position, image. Contains collision checks.
 * @author Nir Reiter
 *
 */
public class Sprite {

	public static boolean debug = true;
	
	protected PImage image;
	protected Rectangle2D.Double rect;
	protected double velocityX, velocityY;
	
	/**
	 * Creates a new sprite, with dimensions, starting location, and graphics.
	 * @param x The x-coordinate to start
	 * @param y The y-coordinate to start
	 * @param w The width of the sprite (collision and graphics)
	 * @param h The height of the sprite (collision and graphics)
	 * @param image The graphic for the sprite
	 */
	public Sprite(double x, double y, double w, double h, PImage image) {
		this.image = image;
		rect = new Rectangle2D.Double(x, y, w, h);
	}
	
	/**
	 * Tests whether the sprite collides with another sprite.
	 * Sprites do not collide on the outermost pixel.
	 * @param other The Sprite to check collision against
	 * @return True if the Sprites collide, False if not.
	 */
	public boolean spriteCollide(Sprite other) {
		return rect.getMaxX() > other.rect.getMinX() &&
				rect.getMinX() < other.rect.getMaxX() &&
				rect.getMaxY() > other.rect.getMinY() &&
				rect.getMinY() < other.rect.getMaxY();
	}
	
	/**
	 * @param x The x-coordinate of the new location
	 * @param y The y-coordinate of the new location
	 */
	public void setLocation(double x, double y) {
		rect.x = x;
		rect.y = y;
	}
	
	/**
	 * @param x The distance to move along the x-axis
	 * @param y The distance to move along the y-axis
	 */
	public void translate(double x, double y) {
		rect.x += x;
		rect.y += y;
	}
	
	/**
	 * Should be called every frame by the game.
	 * @param game The DrawingSurface that the player is located on.
	 */
	public void update(DrawingSurface game) {
		rect.x += velocityX;
		rect.y += velocityY;
	}
	
	/**
	 * Draws the Sprite to the window. Should be called after Sprite.update()
	 * @param drawing the PApplet to draw this Sprite on.
	 */
	public void draw(PApplet drawing) {
		drawing.pushStyle();
		drawing.image(image, (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
		
		if (debug) {
			drawing.noFill();
			drawing.stroke(255, 0, 0);
			drawing.rect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
		}
		drawing.popStyle();
	}
}
