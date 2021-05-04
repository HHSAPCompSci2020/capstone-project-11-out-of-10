import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {

	private PImage image;
	private Rectangle2D.Double rect;
	
	public Sprite(int x, int y, int w, int h, PImage image) {
		this.image = image;
		rect = new Rectangle2D.Double(x, y, w, h);
	}
	
	public void setLocation(int x, int y) {
		rect.x = x;
		rect.y = y;
	}
	
	public void translate(int x, int y) {
		rect.x += x;
		rect.y += y;
	}
	
	public void update(double deltaTime) {
		// to be overridden
	}
	
	public void draw(PApplet drawing) {
		drawing.image(image, (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
	}
}
