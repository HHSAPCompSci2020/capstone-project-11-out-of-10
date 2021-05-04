import processing.core.PImage;

public class Player extends Sprite {
	
	public static final int WIDTH = 32;
	public static final int HEIGHT = 64;
	public static PImage playerImage;
	
	public static final int MOVE_SPEED = 5;
	
	private double balance;
	
	public Player(int x, int y) { 
		super(x, y, WIDTH, HEIGHT, playerImage);
	}
	
	public static void loadImage(PImage image) {
		playerImage = image;
	}
	
	public void walk(double angle) {
		velocityX = MOVE_SPEED*Math.cos(angle);
		velocityY = -MOVE_SPEED*Math.sin(angle);
	}
	
	@Override
	public void update(DrawingSurface game) {
		if (game.isKeyHeld(DrawingSurface.RIGHT))
			walk(0);
		
		else if (game.isKeyHeld(DrawingSurface.UP))
			walk(0.5 * Math.PI);
		
		else if (game.isKeyHeld(DrawingSurface.LEFT))
			walk(Math.PI);
		
		else if (game.isKeyHeld(DrawingSurface.DOWN))
			walk(1.5 * Math.PI);
		
		else {
			velocityX = 0;
			velocityY = 0;
		}
		
		super.update(game);
		
		for (Sprite s : game.obstacles) {
			if (spriteCollide(s)) {
				rect.x -= velocityX;
				rect.y -= velocityY;
			}
		}
	}
	
}
