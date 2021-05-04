import java.util.ArrayList;
import java.util.Set;

import processing.core.PApplet;

public class DrawingSurface extends PApplet {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public ArrayList<Sprite> obstacles;
	public Player player1;
	public ArrayList<Integer> keysHeld;
	
	public DrawingSurface() {
		keysHeld = new ArrayList<Integer>();
		obstacles = new ArrayList<Sprite>();
	}
	
	public void setup() {
		Player.loadImage(loadImage("player.png"));
		player1 = new Player(30, 30);
		obstacles.add(new Sprite(100, 100, 50, 50, Player.playerImage));
	}
	
	public void draw() {
		
		translate(WIDTH/2, HEIGHT/2);
		translate(-(float)player1.rect.x, -(float)player1.rect.y);
		
		background(0, 255, 255);
		
		player1.update(this);
		player1.draw(this);
		for (Sprite s : obstacles)
			s.draw(this);
	}
	
	public void keyPressed() {
		if (!keysHeld.contains(keyCode))
			keysHeld.add(keyCode);
	}
	
	public void keyReleased() {
		keysHeld.remove(new Integer(keyCode));
	}
	
	public boolean isKeyHeld(Integer code) {
		return keysHeld.contains(code);
	}
	
}
