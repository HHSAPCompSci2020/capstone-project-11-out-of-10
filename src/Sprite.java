import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {

	protected PImage image;
	protected Rectangle2D.Double rect;
	protected double velocityX, velocityY;
	
	public Sprite(double x, double y, double w, double h, PImage image) {
		this.image = image;
		rect = new Rectangle2D.Double(x, y, w, h);
	}
	
	public boolean spriteCollide(Sprite other) {
		return rect.getMaxX() > other.rect.getMinX() &&
				rect.getMinX() < other.rect.getMaxX() &&
				rect.getMaxY() > other.rect.getMinY() &&
				rect.getMinY() < other.rect.getMaxY();
	}
	
	public void setLocation(double x, double y) {
		rect.x = x;
		rect.y = y;
	}
	
	public void translate(double x, double y) {
		rect.x += x;
		rect.y += y;
	}
	
	public void update(DrawingSurface game) {
		rect.x += velocityX;
		rect.y += velocityY;
	}
	
	public void draw(PApplet drawing) {
		drawing.image(image, (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
	}
}
