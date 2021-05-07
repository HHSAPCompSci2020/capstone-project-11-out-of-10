import java.util.ArrayList;

import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;

import processing.core.PApplet;

public class DrawingSurface extends PApplet {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public int gameAreaWidth;
	public int gameAreaHeight;
	
	public ArrayList<Sprite> obstacles;
	public Player player1;
	public ArrayList<Integer> keysHeld;
	
	int animalDrawn;


	GButton b1,b2,b3,b4,b5;
	GLabel lblOut;
	//long timer;
	
	
	public DrawingSurface() {
		keysHeld = new ArrayList<Integer>();
		obstacles = new ArrayList<Sprite>();
		
		gameAreaWidth = 5000;
		gameAreaHeight = 3000;
	}
		

	/*public void settings() {
		size(600, 440);
	}*/

	

	public void setup() {
		b1 = new GButton(this, 40,  500, 80, 30, "Button 1");
		b1.fireAllEvents(true);
		b2 = new GButton(this, 190, 500, 80, 30, "Button 2");
		b2.fireAllEvents(true);
		b3 = new GButton(this, 340, 500, 80, 30, "Button 3");
		b3.fireAllEvents(true);
		b4 = new GButton(this, 490, 500, 80, 30, "Button 4");
		b4.fireAllEvents(true);
		b5 = new GButton(this, 640, 500, 80, 30, "Button 5");
		b5.fireAllEvents(true);

		lblOut = new GLabel(this, 50, 30, 560, 20, "");
		animalDrawn = 0;
		
		player1 = new Player(30, 30, loadImage("player.png"));
		obstacles.add(new Sprite(150, 150, 300, 50, loadImage("obstacle.png")));
	}
	
	public void update() {
		player1.update(this);
	}

	public void draw() {
		
		update();
		
		/* DRAWING */
		
		background(150, 150, 150);
		
		pushMatrix();
		
		// Center the game field on the player
		translate(WIDTH/2, HEIGHT/2);
		translate(-(float)player1.rect.x, -(float)player1.rect.y);
		
		fill(150, 100, 0);
		rect(0, 0, gameAreaWidth, gameAreaHeight); // map background
		
		player1.draw(this);
		for (Sprite s : obstacles)
			s.draw(this);
		
		popMatrix();
		
		// ALL UI ELEMENTS DRAW BELOW HERE
		
		String text = "animal1: " + "number    " + "animal2: " + "number    " + "animal3: " + "number    " + "animal4: "+ "number    " + "animal5: " + "number    " + "Animal selected: " + animalDrawn + "     coins: " + "number";
		fill(0);
		text(text, 10, 20);
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
		if (button == b1 && event == GEvent.CLICKED) {
			//lblOut.setText("Hit button 1");
			System.out.println("1");
			animalDrawn = 1;
			
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
