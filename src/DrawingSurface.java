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
	
	int animalDrawn;


	GButton b1,b2,b3,b4,b5;
	GLabel lblOut;
	//long timer;
	
	
	public DrawingSurface() {
		keysHeld = new ArrayList<Integer>();
		obstacles = new ArrayList<Sprite>();
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
		
		Player.loadImage(loadImage("player.png"));
		player1 = new Player(30, 30);
		obstacles.add(new Sprite(100, 100, 50, 50, Player.playerImage));
	}

	public void draw() {
		
		pushMatrix();
		
		translate(WIDTH/2, HEIGHT/2);
		translate(-(float)player1.rect.x, -(float)player1.rect.y);
		
		background(0, 255, 255);
		String text = "animal1: " + "number    " + "animal2: " + "number    " + "animal3: " + "number    " + "animal4: "+ "number    " + "animal5: " + "number    " + "Animal selected: " + animalDrawn + "     coins: " + "number";
		fill(0);
		text(text, -365, -250);
		player1.update(this);
		player1.draw(this);
		for (Sprite s : obstacles)
			s.draw(this);
		
		popMatrix();
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
