import java.util.ArrayList;
import java.util.Set;

import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;

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
		

	/*public void settings() {
		size(600, 440);
	}*/

	int animalDrawn;


	GButton b1,b2,b3,b4,b5;
	GLabel lblOut;
	long timer;

	public void setup() {
		b1 = new GButton(this, 40, 200, 40, 20, "Button 1");
		b2 = new GButton(this, 100, 200, 40, 20, "Button 2");
		b3 = new GButton(this, 160, 200, 40, 20, "Button 3");
		b4 = new GButton(this, 220, 200, 40, 20, "Button 4");
		b5 = new GButton(this, 280, 200, 40, 20, "Button 5");

		lblOut = new GLabel(this, 50, 30, 560, 20, "");
		animalDrawn = 0;
		
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
	
	public void mousePressed() {
		if(animalDrawn == 1) {
			lblOut = new GLabel(this, mouseX, mouseY, 560, 20, "hi1");
			animalDrawn = 0;
		} else if(animalDrawn == 2) {
			lblOut = new GLabel(this, mouseX, mouseY, 560, 20, "hi2");
			animalDrawn = 0;
		} else if(animalDrawn == 3) {
			lblOut = new GLabel(this, mouseX, mouseY, 560, 20, "hi3");
			animalDrawn = 0;
		} else if(animalDrawn == 4) {
			lblOut = new GLabel(this, mouseX, mouseY, 560, 20, "hi4");
			animalDrawn = 0;
		} else if(animalDrawn == 5) {
			lblOut = new GLabel(this, mouseX, mouseY, 560, 20, "hi5");
			animalDrawn = 0;
		}
	}

	public void handleButtonEvents(GButton button, GEvent event) {
		// Create the control window?
		if (button == b1 && event == GEvent.CLICKED) {
			//lblOut.setText("Hit button 1");
			System.out.println("1");
			animalDrawn = 1;
			System.out.println(animalDrawn);
			
		} else if (button == b2 && event == GEvent.CLICKED) {
			//lblOut.setText("Hit button 2");
			System.out.println("2");
			animalDrawn = 2;
			
		} else if (button == b3 && event == GEvent.CLICKED) {
			//lblOut.setText("Hit button 3");
			System.out.println("3");
			animalDrawn = 3;
			
		} else if (button == b4 && event == GEvent.CLICKED) {
			System.out.println("4");
			animalDrawn = 4;
			
		} else if (button == b5 && event == GEvent.CLICKED) {
			System.out.println("5");
			animalDrawn = 5;
		}
		
	}
}
